package com.dynatrace.diagnostics.plugins.jmx.datacollection;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.management.MBeanAttributeInfo;
import javax.management.MBeanFeatureInfo;
import javax.management.MBeanOperationInfo;
import javax.management.MBeanServerConnection;
import javax.management.ObjectInstance;
import javax.management.openmbean.CompositeData;
import javax.management.openmbean.TabularData;

import java.util.logging.Logger;
import java.util.logging.Level;

import com.dynatrace.diagnostics.plugins.jmx.variableholder.JVMStats;
import com.dynatrace.diagnostics.plugins.jmx.variableholder.DateTime;
import com.dynatrace.diagnostics.plugins.jmx.variableholder.JVMTimeHolder;
import com.dynatrace.diagnostics.plugins.jmx.variableholder.TibcoStats;
import com.dynatrace.diagnostics.plugins.jmx.variableholder.TibcoTimeHolder;


public class SubCommand {

    private final ArrayList<JVMStats> statsList = new ArrayList<JVMStats>();
    private final ArrayList<TibcoStats> statsList2 = new ArrayList<TibcoStats>();
    
    JVMTimeHolder ad = new JVMTimeHolder();
    TibcoTimeHolder add = new TibcoTimeHolder();
    
	private ArrayList<JVMStats> test;
	private ArrayList<TibcoStats> test2;
    
    private static final Logger log = Logger.getLogger(SubCommand.class.getName());

    public boolean isFeatureInfo(MBeanFeatureInfo infos[], String cmd) {
        return getFeatureInfo(infos, cmd) != null;
    }

    protected String addNameToBuffer(StringBuffer buffer, String indent, String name) {
        if (name == null || name.length() == 0) {
            return indent;
        } else {
            buffer.append(indent);
            buffer.append(name);
            buffer.append(":\n");
            return (new StringBuilder()).append(indent).append(" ").toString();
        }
    }

    protected StringBuffer recurseCompositeData(StringBuffer buffer, String indent, String name, CompositeData data) {
        indent = addNameToBuffer(buffer, indent, name);
        for (Iterator<?> i = data.getCompositeType().keySet().iterator(); i.hasNext();) {
            String key = (String) i.next();
            Object o = data.get(key);
            if (o instanceof CompositeData) {
                recurseCompositeData(buffer, (new StringBuilder()).append(indent).append(" ").toString(), key, (CompositeData) o);
            } else if (o instanceof TabularData) {
                recurseTabularData(buffer, indent, key, (TabularData) o);
            } else {
                buffer.append(indent);
                buffer.append(key);
                buffer.append(": ");
                buffer.append(o);
                buffer.append("\n");
            }
        }
        return buffer;
    }

    protected StringBuffer recurseTabularData(StringBuffer buffer, String indent, String name, TabularData data) {
        addNameToBuffer(buffer, indent, name);
        Collection<?> c = data.values();
        for (Iterator<?> i = c.iterator(); i.hasNext();) {
            Object obj = i.next();
            if (obj instanceof CompositeData) {
                recurseCompositeData(buffer, (new StringBuilder()).append(indent).append(" ").toString(), "", (CompositeData) obj);
            } else if (obj instanceof TabularData) {
                recurseTabularData(buffer, indent, "", (TabularData) obj);
            } else {
                buffer.append(obj);
            }
        }
        return buffer;
    }

    public MBeanFeatureInfo getFeatureInfo(MBeanFeatureInfo infos[], String cmd) {
        int index = cmd.indexOf('=');
        String name = index <= 0 ? cmd : cmd.substring(0, index);
        for (int i = 0; i < infos.length; i++) {
            if (infos[i].getName().equals(name)) {
                return infos[i];
            }
        }
        return null;
    }

    public ArrayList<JVMStats> doSubCommand(MBeanServerConnection mbsc, ObjectInstance instance, String subCommand, String BeanName)
            throws Exception {
        MBeanAttributeInfo attributeInfo[] = mbsc.getMBeanInfo(instance.getObjectName()).getAttributes();
        MBeanOperationInfo operationInfo[] = mbsc.getMBeanInfo(instance.getObjectName()).getOperations();
        Object result = null;
        if (subCommand != null) {
            AttributeOperation ao = new AttributeOperation();
            if (Character.isUpperCase(subCommand.charAt(0))  && !isFeatureInfo(operationInfo, subCommand) && isFeatureInfo(attributeInfo, subCommand)) {
                    result = ao.doAttributeOperation(mbsc, instance, subCommand);
            } else {
            	result = ao.doAttributeOperation(mbsc, instance, subCommand);
            }
            if (result instanceof CompositeData) {
                result = recurseCompositeData(new StringBuffer("\n"), "", "", (CompositeData) result);
                
            } else if (result instanceof TabularData) {
                result = recurseTabularData(new StringBuffer("\n"), "", "", (TabularData) result);
                
            } else if (result instanceof String[]) {
                String strs[] = (String[]) (String[]) result;
                StringBuffer buffer = new StringBuffer("\n");
                for (int i = 0; i < strs.length; i++) {
                    buffer.append(strs[i]);
                    buffer.append("\n");
                }
                result = buffer;
            }
            if (result != null) {
                String str = result.toString().replace("\n", " ");
                String rt = str.replace(": ", " ");
                String[] tokens = rt.split(" ");
                JVMStats stats = new JVMStats();
                stats.setbeanName(BeanName);
                stats.setMBeanName(instance.toString().replaceAll(" ", ""));
                stats.setAttName(subCommand);
                long time = DateTime.getDateTime();
                stats.settimeDateConverted(time);
                int p = 0;
                int n = 1;
                for (String t : tokens) {

                     if (n == 0) {
                         stats = new JVMStats();
                         stats.setMBeanName(instance.toString());
                         stats.setAttName(subCommand);
                         time = DateTime.getDateTime();
                         stats.settimeDateConverted(time);
                         n++;
                     }
                     if (t.equals("")) {
                         //Do nothing. We don't want empty space being saved off.
                     } else {
                    	 if (log.isLoggable(Level.INFO)){
                    		 log.info(t);
                    	 }
                         if (p == 0) {

                             try {
                                 try {
                                     Long.parseLong(t);
                                 } catch (Exception pp) {
                                     Float.parseFloat(t);
                                 }
                                 stats.setSubAttName("Count");
                                 if (subCommand.equalsIgnoreCase("Uptime")) {
                                     DecimalFormat df = new DecimalFormat("#.##");
                                     Float val = Float.parseFloat(t);
                                     val = (((val / 1000) / 60));
                                     String r = df.format(val);
                                     stats.setValue(r);
                                 } else {
                                     if (Float.parseFloat(t) < 0) {
                                         t = "0";
                                     }
                                     DecimalFormat df = new DecimalFormat("#.##");
                                     float f = Float.parseFloat(t);
                                     String r = df.format(f);
                                     stats.setValue(r);
                                 }
                             } catch (Exception e) {
                                 stats.setSubAttName(t);
                             }
                             if (p == 0) {
                                 statsList.add(stats);
                                 ad.addServerStatsList(statsList);
                                 statsList.clear();
                             }
                             p++;
                         } else if (p == 1) {
                             if (subCommand.equalsIgnoreCase("Uptime")) {
                                 DecimalFormat df = new DecimalFormat("#.##");
                                 Float val = Float.parseFloat(t);
                                 val = (((val / 1000) / 60) / 60);
                                 String r = df.format(val);
                                 stats.setValue(r);
                             } else {
                                 if (Float.parseFloat(t) < 0) {
                                     t = "0";
                                 }
                                 DecimalFormat df = new DecimalFormat("#.##");

                                 float f = Float.parseFloat(t);
                                 String r = df.format(f);
                                 stats.setValue(r);
                                 p = 0;
                                 n = 0;
                             }
                         }
                     }
                 }

             }
        }
        test = ad.getArrayList();
        return test;
    }
    
    //Tibco Monitoring
    public ArrayList<TibcoStats> doSubCommand2(MBeanServerConnection mbsc, ObjectInstance instance, String subCommand, boolean command, String BeanName, String[] ignoreProcess)
            throws Exception {
        MBeanAttributeInfo attributeInfo[] = mbsc.getMBeanInfo(instance.getObjectName()).getAttributes();
        MBeanOperationInfo operationInfo[] = mbsc.getMBeanInfo(instance.getObjectName()).getOperations();
        Object result=null;
        
        if (subCommand != null) {
            BeanOperation bo = new BeanOperation();
            //AttributeOperation ao = new AttributeOperation();
            if (Character.isUpperCase(subCommand.charAt(0))) {
                if (!isFeatureInfo(attributeInfo, subCommand) && isFeatureInfo(operationInfo, subCommand)) {
                    result = bo.doBeanOperation(mbsc, instance, subCommand, operationInfo);
                } else {
                    //result = ao.doAttributeOperation(mbsc, instance, subCommand, attributeInfo);
                }
            } else if (!isFeatureInfo(operationInfo, subCommand) && isFeatureInfo(attributeInfo, subCommand)) {
                //result = ao.doAttributeOperation(mbsc, instance, subCommand, attributeInfo);
            } else {
                result = bo.doBeanOperation(mbsc, instance, subCommand, operationInfo);  // using...
            }
            if (result instanceof CompositeData) {
                result = recurseCompositeData(new StringBuffer("\n"), "", "", (CompositeData) result);
            } else if (result instanceof TabularData) {
                result = recurseTabularData(new StringBuffer("\n"), "", "", (TabularData) result);
            } else if (result instanceof String[]) {
                String strs[] = (String[]) (String[]) result;
                StringBuffer buffer = new StringBuffer("\n");
                for (int i = 0; i < strs.length; i++) {
                    buffer.append(strs[i]);
                    buffer.append("\n");
                }
                result = buffer;
            }
            if (command) {
                if (result != null) {
                	TibcoStats stats2 = new TibcoStats();
                	if(subCommand.equalsIgnoreCase("GetProcessCount")){
                		stats2 = new TibcoStats();
                		String value = result.toString();
                		String bean = BeanName.substring(BeanName.lastIndexOf("=")+1);
                		
                		stats2.setbeanName(bean);
                        stats2.setCommand(subCommand);
                        stats2.setsubName("none");	
                        stats2.setmeasure("ProcessCount");
                        stats2.setValue(value);
                		
                		statsList2.add(stats2);
                        add.addServerStatsList(statsList2);
                        statsList2.clear();
                	} else if(subCommand.equalsIgnoreCase("ListDbConnections")){
                        	String test = result.toString();
                        	String assetClasses;
                            String[] splits;
                            String s = test; 
                            String regexp = "[\\n]+"; 
                            String [] tokens; 
                            tokens = s.split(regexp);
                            String bean = BeanName.substring(BeanName.lastIndexOf("=")+1);
                            String subName1 = "";
                            if (log.isLoggable(Level.INFO)){
                            	log.info("ListDbConnections Token size: " + tokens.length);
                            }
                            for (String token : tokens) {
                                assetClasses = token;
                                splits = assetClasses.split(":");
                                if(splits.length == 2){ //get the blank line from entering here.
                                	String measureName = splits[0];
                                	String measureValue = splits[1];
                                	String name = measureName.trim();
                                	String value = measureValue.trim();
                                	if(name.equalsIgnoreCase("Connection-Name")) {
                                		subName1=value;
                                		log.info("Setting Connection Name : " + value);
                                		
                                	} else if(!name.equalsIgnoreCase("Connection-Owner") && !name.equalsIgnoreCase("User-Name")){
                                    	if(value.equalsIgnoreCase("Idle")){
                                    		value="0";
                                    	} 
                                    	if (value.equalsIgnoreCase("Active")){
                                    		value="1";
                                    	} 
                                    	stats2 = new TibcoStats();	
                                	
                                    	stats2.setbeanName(bean);
                                    	stats2.setCommand(subCommand);
                                    	stats2.setsubName(subName1);	
                                    	stats2.setmeasure(name);
                                    	stats2.setValue(value);
                                    
                                    	statsList2.add(stats2);
                                    	add.addServerStatsList(statsList2);
                                    	statsList2.clear();
                                    	
                                    	if (log.isLoggable(Level.INFO)){
                                    		log.info("Message : " + bean + " " + subCommand + " " + name +" " +  subName1 +" " +  value);
                                    	}
                                    	
                                    }
                                } 
                             }                	
                	} else if(subCommand.equalsIgnoreCase("GetActiveProcessCount")){
                		stats2 = new TibcoStats();
                		
                		String value = result.toString();
                		String bean = BeanName.substring(BeanName.lastIndexOf("=")+1);
                		
                		stats2.setbeanName(bean);
                        stats2.setCommand(subCommand);
                        stats2.setsubName("none");	
                        stats2.setmeasure("ActiveProcessCount");
                        stats2.setValue(value);
               		
                		statsList2.add(stats2);
                        add.addServerStatsList(statsList2);
                        statsList2.clear();
                    } else if(subCommand.equalsIgnoreCase("GetMemoryUsage")){
                    	String test = result.toString();
                    	String assetClasses;
                        String[] splits;
                        String s = test; 
                        String regexp = "[\\n]+"; 
                        String [] tokens; 
                        tokens = s.split(regexp);
                        String bean = BeanName.substring(BeanName.lastIndexOf("=")+1);
                        String subName1 = "";
                        if (log.isLoggable(Level.INFO)){
                        	log.info("GetMemoryUsage Token size: " + tokens.length);
                        }
                        for (String token : tokens) {
                            assetClasses = token;
                            splits = assetClasses.split(":");
                            if(splits.length == 2){ //get the blank line from entering here.
                                String measureName = splits[0];
                                String measureValue = splits[1];
                                
                                String name = measureName.trim();
                                String value = measureValue.trim();
                                
                                stats2 = new TibcoStats();	
                            	
                            	stats2.setbeanName(bean);
                                stats2.setCommand(subCommand);
                                stats2.setsubName(subName1);	
                                stats2.setmeasure(name);
                                stats2.setValue(value);
                                
                                statsList2.add(stats2);
                                add.addServerStatsList(statsList2);
                                statsList2.clear();
                            } 
                         }
                    } else if(subCommand.equalsIgnoreCase("GetExecInfo")){
                    	String test = result.toString();
                    	String assetClasses;
                        String[] splits;
                        String s = test; 
                        String regexp = "[\\n]+"; 
                        String [] tokens; 
                        tokens = s.split(regexp);
                        String bean = BeanName.substring(BeanName.lastIndexOf("=")+1);
                        String subName1 = "";
                        for (String token : tokens) {
                            assetClasses = token;
                            splits = assetClasses.split(":");
                            if(splits.length == 2){ //get the blank line from entering here.
                                String measureName = splits[0];
                                String measureValue = splits[1];
                                
                                String name = measureName.trim();
                                String value = measureValue.trim();
                                stats2 = new TibcoStats();	
                            	
                                if(name.equalsIgnoreCase("Status")){
                                	if(value.equalsIgnoreCase("ACTIVE")){
                                		value="0"; //set to one for active connections
                                	} else{
                                		value="1"; //one for other conditions
                                	}
                                	stats2.setbeanName(bean);
                                    stats2.setCommand(subCommand);
                                    stats2.setsubName(subName1);	
                                    stats2.setmeasure(name);
                                    stats2.setValue(value);
                                    
                                    statsList2.add(stats2);
                                    add.addServerStatsList(statsList2);
                                    statsList2.clear();	
                                }else {
                            	
                            	stats2.setbeanName(bean);
                                stats2.setCommand(subCommand);
                                stats2.setsubName(subName1);	
                                stats2.setmeasure(name);
                                stats2.setValue(value);
                                
                                statsList2.add(stats2);
                                add.addServerStatsList(statsList2);
                                statsList2.clear();
                                }
                            }   
                         }
                    } else if(subCommand.equalsIgnoreCase("getStatus")){
                    	String test = result.toString();
                    	String assetClasses;
                        String[] splits;
                        String s = test; 
                        String regexp = "[\\n]+"; 
                        String [] tokens; 
                        tokens = s.split(regexp);
                        String bean = BeanName.substring(BeanName.lastIndexOf("=")+1);
                        String subName1 = "";
                        for (String token : tokens) {
                            assetClasses = token;
                            splits = assetClasses.split(":");
                            if(splits.length == 2){ //get the blank line from entering here.
                                String measureName = splits[0];
                                String measureValue = splits[1];
                                
                                String name = measureName.trim();
                                String value = measureValue.trim();
                                
                                stats2 = new TibcoStats();	
                                if(!name.equalsIgnoreCase("Adapter Name") && !name.equalsIgnoreCase("Host") && !name.equalsIgnoreCase("Instance ID")){
                                	
                                	stats2.setbeanName(bean);
                                    stats2.setCommand(subCommand);
                                    stats2.setsubName(subName1);	
                                    stats2.setmeasure(name);
                                    stats2.setValue(value);
                                    
                                    statsList2.add(stats2);
                                    add.addServerStatsList(statsList2);
                                    statsList2.clear();
                                }
                            }   
                         }
                    } else if(subCommand.equalsIgnoreCase("GetProcessDefinitions")){
                        String test = result.toString();
                        String assetClasses;
                        String[] splits;
                        String s = test; 
                        String regexp = "[\\n]+"; 
                        String [] tokens; 
                        tokens = s.split(regexp);
                        int counter = 0;
                        String bean = BeanName.substring(BeanName.lastIndexOf("=")+1);
                        String subName1 = "";
                        int match = 0;
                        int z = ignoreProcess.length;
						String[] record = new String[38];
						int c = 0;
						for (String token : tokens) {
                            assetClasses = token;
                            splits = assetClasses.split(":");
                            if(splits.length == 2){ //get the blank line from entering here.
                                String measureName = splits[0];
                                String measureValue = splits[1];
                                String name = measureName.trim();
                                String value = measureValue.trim();
                                //The subname will be number 14... There are 19 values per subname. 21 data points.
								if (!name.equalsIgnoreCase("Starter") && (counter < 22)) {
									if(name.equalsIgnoreCase("Name")){
										subName1 = value;
									}
									if(!name.equalsIgnoreCase("Name")){ //put the values in a string array so we can line up the subname
										record[c] = name;
										c++;
										record[c] = value;
										c++;
                                    }
									if (counter == 21) { //Time to store off the data.
										//Need to ensure we don't save off the data for the ignore list.
										for (int e=0; e<z; e++){
											String nameSub = ignoreProcess[e];
											String subName2 = subName1.replace("/", "-");
											subName2 = subName2.replace(".", "-");
											if(subName1.equalsIgnoreCase(nameSub) || subName1.contains(nameSub) || 
													subName2.equalsIgnoreCase(nameSub) || subName2.contains(nameSub)){
												match = 1;
												break;
											}
										}
										if (match == 0){
											int size = record.length;
											for (int i=0; i<size; i=i+2) {
												stats2 = new TibcoStats();	
												stats2.setbeanName(bean);
												stats2.setCommand(subCommand);
												stats2.setsubName(subName1); 	
												stats2.setmeasure(record[i]);
												stats2.setValue(record[i+1]);
												if (log.isLoggable(Level.INFO)){
													log.info("Message : " + bean + " " + subCommand + " " + record[i] +" " +  subName1 +" " +  record[i+1]);
												}
												statsList2.add(stats2);
												add.addServerStatsList(statsList2);
												statsList2.clear();
											}
											counter = 0;
											c = 0;
											record = new String[38];
											subName1 = "";
										} else if (match == 1){
											counter = 0;
											c = 0;
											record = new String[38];
											subName1 = "";
											match = 0;
										}
									} 
								} else if (name.equalsIgnoreCase("Starter")){
                                	if (log.isLoggable(Level.FINE)){
                                		log.fine("Not recording a value for Starter: " + name + " " + value);
                                	}
								} else {
									if (log.isLoggable(Level.WARNING)){
                                		log.warning("GetProcessDefinitions counter seems to be off.");
                                	}
								}
							}
						counter++;
						}
                    }
                 }
              }
           }
        test2 = add.getArrayList();
        return test2;
    }
}
