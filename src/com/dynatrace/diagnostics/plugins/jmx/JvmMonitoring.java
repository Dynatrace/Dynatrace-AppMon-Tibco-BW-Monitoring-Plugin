
package com.dynatrace.diagnostics.plugins.jmx;

import com.dynatrace.diagnostics.pdk.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.management.MBeanServerConnection;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXServiceURL;
import com.dynatrace.diagnostics.plugins.jmx.variableholder.JVMStats;
import com.dynatrace.diagnostics.plugins.jmx.variableholder.DateTime;
import com.dynatrace.diagnostics.plugins.jmx.variableholder.TibcoStats;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import javax.management.ObjectName;


public class JvmMonitoring implements Monitor, JvmMonitoringConstants {

	private static final Logger log = Logger.getLogger(JvmMonitoring.class.getName());
	
	private Connection wc = null;
	private JvmMonitoringProperties properties;
	private MBeanServerConnection mbsc = null;
	private JMXServiceURL rmiurl = null;
	private JMXConnector jmxc = null;
	private ArrayList<JVMStats> oldCopy = null;
	private ArrayList<TibcoStats> tibOldCopy = null;
	private String MBeans;
	private String attributes;
	private String[] bean;
	private String[] attribute;
	private String processIgnore;
	private String[] ignoreProcess;
	private String environment;
	private String serverName;
	private String userpass;
	private String hostname;
	private String acommand[] = null;
	private boolean c = false;
	private boolean p = false;
	private boolean monJVM = false;
	private boolean monTIB = false;
	
	public static boolean isEmptyOrBlank(String str) {
		return str == null || str.trim().isEmpty();
		}
	
	@Override
	public Status setup(MonitorEnvironment env) throws Exception {
		if (log.isLoggable(Level.INFO)) {
			log.info("Entering setup method");
		}
		properties = new JvmMonitoringProperties();
		
		// set plugin's configuration parameters
		properties.setJmxEnvironment(env.getConfigString(CONFIG_JMX_ENVIRONMENT).trim());
		properties.setDtServer(env.getConfigString(CONFIG_DT_SERVER).trim());
		properties.setJmxPassword(env.getConfigString(CONFIG_JMX_PASSWORD));
		properties.setserverName(env.getConfigString(CONFIG_SERVER_NAME).trim());
		properties.setMbeans(env.getConfigString(CONFIG_MBEAN_GROUP).trim());
		properties.settibMonitor(env.getConfigBoolean(CONFIG_TIBMONITOR_NAME));
		properties.setjvmMonitor(env.getConfigBoolean(CONFIG_JVMMONITOR_NAME));
		
		MBeans=properties.getMbeans();
		bean = MBeans.split("\\s+");
		properties.setAttributes(env.getConfigString(CONFIG_MBEAN_ATTRIBUTES).trim());
		attributes=properties.getAttributes();
    	attribute = attributes.split("\\s+");
    	
    	properties.setProcessIgnore(env.getConfigString(CONFIG_TIBCO_PROCESSIGNORE).trim());
    	processIgnore=properties.getProcessIgnore();
    	ignoreProcess=processIgnore.split("\\s+");
    	
    	
    	
    	
		environment=properties.getJmxEnvironment();
		serverName=properties.getserverName();
		
		monJVM = properties.getjvmMonitor();
		monTIB = properties.gettibMonitor();
		
		
		if (isEmptyOrBlank(MBeans)){
			log.warning("No MBean data was entered.");
			return new Status(Status.StatusCode.ErrorInternal, "No MBean data was entered. Validate configuration.");
		}
		if (isEmptyOrBlank(attributes)){
			log.warning("No Attributes data was entered.");
			return new Status(Status.StatusCode.ErrorInternal, "No Attribute data was entered. Validate configuration.");
		}		
		
		hostname = properties.getDtServer();
		userpass = properties.getJmxPassword();
       try {
    	   wc = new Connection();
        	mbsc = wc.Connect(hostname,userpass); 
        } catch (Exception e) {
        	mbsc = null; 
        }
       try{
    	   if(mbsc != null){
    		   return new Status(Status.StatusCode.Success);
    	   }else {
    		   mbsc = null;
    		   return new Status(Status.StatusCode.PartialSuccess, "There was an error connecting to JMX server. Could be formatting of the user password and/or configuration setup. Please validate the configurations being used"
       			+ " and JMX server is running.");
    	   }
       } catch (Exception e) {
    	   mbsc = null;
    	   return new Status(Status.StatusCode.PartialSuccess, "Unable to connect to the MBean server at: " + hostname + ". Please validate it is running and connection"
    	   		+ " details are correct.");
       }
	}
	
	@Override
	public Status execute(MonitorEnvironment env) throws Exception {
		DynamicMeasure testing = new DynamicMeasure();
		
		if (mbsc == null){
			try{
				try {
					mbsc = wc.Connect(hostname,userpass);
		        } catch (Exception e) {
		        	mbsc = null;  
		        	String measureName = serverName+"|JVMConnection|"+environment;
					Float f = (float) 0;
					testing.populateDynamicMeasure(env, JVM_CONNECTION_GROUP, JVM_CONNECTION_METRIC, measureName, (double)f);
		        	return new Status(Status.StatusCode.PartialSuccess, "There was an error connecting to JMX server. Could be formatting of the user password and/or configuration setup. Please validate the configurations being used"
		        			+ " and target JMX server is running.");

		        }
		       } catch (Exception e) {
		    	    String measureName = serverName+"|JVMConnection|"+environment;
					Float f = (float) 0;
					testing.populateDynamicMeasure(env, JVM_CONNECTION_GROUP, JVM_CONNECTION_METRIC, measureName, (double)f);
		    	    mbsc = null;
		    	    return new Status(Status.StatusCode.PartialSuccess, "Unable to connect to: " + hostname + ". RMI URL string is: " + rmiurl
		    			   + ". ");
		       }
		}
		
		if(mbsc != null){
			long webMonitorStartTime = DateTime.getDateTime();
			ArrayList<JVMStats> thwd = null;
			ArrayList<TibcoStats> srv = null;
			com.dynatrace.diagnostics.plugins.jmx.datacollection.JVMDataCollection client = new com.dynatrace.diagnostics.plugins.jmx.datacollection.JVMDataCollection(thwd);
			com.dynatrace.diagnostics.plugins.jmx.datacollection.TibcoDataCollection client2 = new com.dynatrace.diagnostics.plugins.jmx.datacollection.TibcoDataCollection(srv);
			
			//Monitor JVM section.
			if(monJVM){
				try{
				
					thwd = client.execute(mbsc, bean, attribute);
				}catch (Exception e){
					mbsc = null;
					String measureName = serverName+"|JVMConnection|"+environment;
					Float f = (float) 0;
					testing.populateDynamicMeasure(env, JVM_CONNECTION_GROUP, JVM_CONNECTION_METRIC, measureName, (double)f);
					log.info("Exception: " + e);
					return new Status(Status.StatusCode.PartialSuccess, "Lost connection to: " + hostname + ". We will try to establish this connection on next run.");
				}
			}
			//Tibco monitoring section.
			if (monTIB){
				List<String> tib = new ArrayList<String>();
				int x = 0;
				Set<ObjectName> names = new TreeSet<ObjectName>(mbsc.queryNames(null, null));
				for (ObjectName name : names) {
					if(name.toString().contains("com.tibco.bw:")){
						tib.add(name.toString());
					}
				}
				int objName = tib.size();
				String stringArray[] = tib.toArray(new String[tib.size()]);
				for (x=0; x < objName; x++){
					System.out.println("ObjectName = " + stringArray[x]);
					c = true;
					String Command[] = new String[7];
					Command[0] = "GetProcessDefinitions"; //Commands you want to perform
					Command[1] = "GetProcessCount";
					Command[2] = "GetMemoryUsage";
					Command[3] = "GetExecInfo";
					Command[4] = "GetActiveProcessCount";
					Command[5] = "getStatus";
					Command[6] = "ListDbConnections";
					
                    try {
                        acommand = new String[8];
                        acommand[0] = stringArray[x];
                        int i = 0;
                        while (i < Command.length) {
                            acommand[i + 1] = Command[i];
                            i++;
                        }
                    } catch (Exception e) {
                        System.out.println(e.toString());
                        
                    }
                    srv = client2.executeTib(mbsc, true, acommand, c, p, 0, ignoreProcess);
				}            
			}
			//How long it took to monitor.
			long webPerfEndTime = DateTime.getDateTime();
			long WebtotalPerfTime = (webPerfEndTime - webMonitorStartTime); 
			String measureName = serverName+"|MonitorJVMTime|"+environment;
			testing.populateDynamicMeasure(env, JVM_CONNECTION_GROUP, JVM_MONITORTIME_METRIC, measureName, (double)WebtotalPerfTime);

			//Record JVM
			if(monJVM){
				com.dynatrace.diagnostics.plugins.jmx.datacollection.JVMRecordData record = new com.dynatrace.diagnostics.plugins.jmx.datacollection.JVMRecordData();
			
				try{
					record.getWriteToDB(env, serverName, environment, thwd, oldCopy);
				} catch (Exception e){
					log.info("Error writting JVM data to DT: " + e.toString());
				}
				oldCopy=thwd;
			}

			//Record Tibco
			if (monTIB){
				com.dynatrace.diagnostics.plugins.jmx.datacollection.TibcoRecordData record2 = new com.dynatrace.diagnostics.plugins.jmx.datacollection.TibcoRecordData();
				try{
					record2.getWriteToDB(env, environment, srv, tibOldCopy);
				} catch (Exception e){
					log.info("Error writting TIBCO data to DT: " + e.toString());
				}
				tibOldCopy=srv;
			}
	}
	    String measureName = serverName+"|JVMConnection|"+environment;
		Float f = (float) 1;
		testing.populateDynamicMeasure(env, JVM_CONNECTION_GROUP, JVM_CONNECTION_METRIC, measureName, (double)f);
		return new Status(Status.StatusCode.Success, "Monitoring completed without know issue.");
	}
	
	@Override
	public void teardown(MonitorEnvironment env) throws Exception {
		if (log.isLoggable(Level.INFO)) {
			log.info("Entering teardown method");
		}
		
		if (jmxc != null) {
			jmxc.close();
		}
	}
}
