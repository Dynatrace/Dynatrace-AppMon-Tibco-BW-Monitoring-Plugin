package com.dynatrace.diagnostics.plugins.jmx.datacollection;

import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.logging.Level;

import com.dynatrace.diagnostics.pdk.MonitorEnvironment;
import com.dynatrace.diagnostics.plugins.jmx.DynamicMeasure;	
import com.dynatrace.diagnostics.plugins.jmx.JvmMonitoringConstants;	
import com.dynatrace.diagnostics.plugins.jmx.variableholder.TibcoStats;


public class TibcoRecordData implements JvmMonitoringConstants{

	private static final Logger log = Logger.getLogger(TibcoRecordData.class.getName());
	    
	public void getWriteToDB(MonitorEnvironment envs, String Environment, ArrayList<TibcoStats> ad, ArrayList<TibcoStats> oldCopy) throws Exception {
		String measureName;
	    DynamicMeasure testing = new DynamicMeasure();
	        
	    try {
	    	ArrayList<TibcoStats> list = ad;
	        log.info("ad size : " + ad.size());
	            
	        for (TibcoStats fromStatic : list) {
	        	//Main Need try catch
	            	
	            String beanName = fromStatic.getbeanName();	
	            String measure =fromStatic.getmeasure();
	            Double Value = Double.parseDouble(fromStatic.getValue());
	            String Command = fromStatic.getCommand();
	            String subName = fromStatic.getsubName();
	             
	            // use for subname...
	            subName = subName.replace("/", "-");
	            subName = subName.replace(".", "-");
	            if (log.isLoggable(Level.FINE)){	
	            	log.fine("TibMonitor|" + beanName + "|" + subName + "|" + Command + "|" + measure+ "|" + Value);
	            }    
                if(measure.equalsIgnoreCase("Aborted")){
                	  measureName = "TibMonitor|" + beanName + "|" + subName + "|" + Command + "|" + measure;
  	            	testing.populateDynamicMeasure(envs, TIBCO_WEBSERVICE_GROUP, TIBCO_ABORTED_METRIC, measureName, (double)Value);
                    
                }else if(measure.equalsIgnoreCase("AverageElapsed")){
                	measureName = "TibMonitor|" + beanName + "|" + subName + "|" + Command + "|" + measure;
	            	testing.populateDynamicMeasure(envs, TIBCO_WEBSERVICE_GROUP, TIBCO_AVERAGEELAPSED_METRIC, measureName, (double)Value);
                    
                }else if(measure.equalsIgnoreCase("AverageExecution")){
                	measureName = "TibMonitor|" + beanName + "|" + subName + "|" + Command + "|" + measure;
	            	testing.populateDynamicMeasure(envs, TIBCO_WEBSERVICE_GROUP, TIBCO_AVERAGEEXECUTION_METRIC, measureName, (double)Value);
                    
                }else if(measure.equalsIgnoreCase("Checkpointed")){
                	measureName = "TibMonitor|" + beanName + "|" + subName + "|" + Command + "|" + measure;
	            	testing.populateDynamicMeasure(envs, TIBCO_WEBSERVICE_GROUP, TIBCO_CHECKPOINTED_METRIC, measureName, (double)Value);
                    
                }else if(measure.equalsIgnoreCase("Completed")){
                	measureName = "TibMonitor|" + beanName + "|" + subName + "|" + Command + "|" + measure;
	            	testing.populateDynamicMeasure(envs, TIBCO_WEBSERVICE_GROUP, TIBCO_COMPLETED_METRIC, measureName, (double)Value);
                    
                }else if(measure.equalsIgnoreCase("CountSinceReset")){
                	measureName = "TibMonitor|" + beanName + "|" + subName + "|" + Command + "|" + measure;
	            	testing.populateDynamicMeasure(envs, TIBCO_WEBSERVICE_GROUP, TIBCO_COUNTSINCERESET_METRIC, measureName, (double)Value);
                    
                }else if(measure.equalsIgnoreCase("Created")){
                	measureName = "TibMonitor|" + beanName + "|" + subName + "|" + Command + "|" + measure;
	            	testing.populateDynamicMeasure(envs, TIBCO_WEBSERVICE_GROUP, TIBCO_CREATED_METRIC, measureName, (double)Value);
                    
                }else if(measure.equalsIgnoreCase("MaxElapsed")){
                	measureName = "TibMonitor|" + beanName + "|" + subName + "|" + Command + "|" + measure;
	            	testing.populateDynamicMeasure(envs, TIBCO_WEBSERVICE_GROUP, TIBCO_MAXELAPSED_METRIC, measureName, (double)Value);
                    
                }else if(measure.equalsIgnoreCase("MaxExecution")){
                	measureName = "TibMonitor|" + beanName + "|" + subName + "|" + Command + "|" + measure;
	            	testing.populateDynamicMeasure(envs, TIBCO_WEBSERVICE_GROUP, TIBCO_MAXEXECUTION_METRIC, measureName, (double)Value);
                    
                }else if(measure.equalsIgnoreCase("MinElapsed")){
                	measureName = "TibMonitor|" + beanName + "|" + subName + "|" + Command + "|" + measure;
	            	testing.populateDynamicMeasure(envs, TIBCO_WEBSERVICE_GROUP, TIBCO_MINELAPSED_METRIC, measureName, (double)Value);
                    
                }else if(measure.equalsIgnoreCase("MinExecution")){
                	measureName = "TibMonitor|" + beanName + "|" + subName + "|" + Command + "|" + measure;
	            	testing.populateDynamicMeasure(envs, TIBCO_WEBSERVICE_GROUP, TIBCO_MINEXECUTION_METRIC, measureName, (double)Value);
                    
                }else if(measure.equalsIgnoreCase("MostRecentElapsedTime")){
                	measureName = "TibMonitor|" + beanName + "|" + subName + "|" + Command + "|" + measure;
	            	testing.populateDynamicMeasure(envs, TIBCO_WEBSERVICE_GROUP, TIBCO_MOSTRECENTELAPSEDTIME_METRIC, measureName, (double)Value);
                    
                }else if(measure.equalsIgnoreCase("MostRecentExecutionTime")){
                	measureName = "TibMonitor|" + beanName + "|" + subName + "|" + Command + "|" + measure;
	            	testing.populateDynamicMeasure(envs, TIBCO_WEBSERVICE_GROUP, TIBCO_MOSTRECENTEXECUTIONTIME_METRIC, measureName, (double)Value);
                    
                }else if(measure.equalsIgnoreCase("Queued")){
                	measureName = "TibMonitor|" + beanName + "|" + subName + "|" + Command + "|" + measure;
	            	testing.populateDynamicMeasure(envs, TIBCO_WEBSERVICE_GROUP, TIBCO_QUEUED_METRIC, measureName, (double)Value);
                    
                }else if(measure.equalsIgnoreCase("Suspended")){
                	measureName = "TibMonitor|" + beanName + "|" + subName + "|" + Command + "|" + measure;
	            	testing.populateDynamicMeasure(envs, TIBCO_WEBSERVICE_GROUP, TIBCO_SUSPENDED_METRIC, measureName, (double)Value);
                    
                }else if(measure.equalsIgnoreCase("Swapped")){
                	measureName = "TibMonitor|" + beanName + "|" + subName + "|" + Command + "|" + measure;
	            	testing.populateDynamicMeasure(envs, TIBCO_WEBSERVICE_GROUP, TIBCO_SWAPPED_METRIC, measureName, (double)Value);
                    
                }else if(measure.equalsIgnoreCase("TimeSinceLastUpdate")){
                	measureName = "TibMonitor|" + beanName + "|" + subName + "|" + Command + "|" + measure;
	            	testing.populateDynamicMeasure(envs, TIBCO_WEBSERVICE_GROUP, TIBCO_TIMESINCELASTUPDATE_METRIC, measureName, (double)Value);
                    
                }else if(measure.equalsIgnoreCase("TotalElapsed")){
                	measureName = "TibMonitor|" + beanName + "|" + subName + "|" + Command + "|" + measure;
	            	testing.populateDynamicMeasure(envs, TIBCO_WEBSERVICE_GROUP, TIBCO_TOTALELAPSED_METRIC, measureName, (double)Value);
                    
                }else if(measure.equalsIgnoreCase("TotalExecution")){
                	measureName = "TibMonitor|" + beanName + "|" + subName + "|" + Command + "|" + measure;
	            	testing.populateDynamicMeasure(envs, TIBCO_WEBSERVICE_GROUP, TIBCO_TOTALEXECUTION_METRIC, measureName, (double)Value);
	            	// Lets match the old copy and get how many executions we did between monitoring cycles.
	            	if(oldCopy != null){
	            		for (TibcoStats fromOldStatic : oldCopy) {
	            			String beanOldName = fromOldStatic.getbeanName();	
	        	            String measureOld =fromOldStatic.getmeasure();
	        	            Double ValueOld = Double.parseDouble(fromOldStatic.getValue());
	        	            String CommandOld = fromOldStatic.getCommand();
	        	            String subNameOld = fromOldStatic.getsubName();
	        	            // use for subname...
	        	            subNameOld = subNameOld.replace("/", "-");
	        	            subNameOld = subNameOld.replace(".", "-");
	        	            if(beanName.equalsIgnoreCase(beanOldName) && subName.equalsIgnoreCase(subNameOld) && 
	        	            		Command.equalsIgnoreCase(CommandOld) && measure.equalsIgnoreCase(measureOld)){
	        	            	Double diff = Value - ValueOld;
	        	            	
	        	            	measureName = "TibMonitor|" + beanName + "|" + subName + "|" + Command + "|Executions";
	        	            	testing.populateDynamicMeasure(envs, TIBCO_WEBSERVICE_GROUP, TIBCO_CYCLEEXECUTION_METRIC, measureName, (double)diff);
	        	            	break;
	        	            }
	            		}
	            	}
	            	
                }else if (measure.equalsIgnoreCase("ProcessCount")){
	            	measureName = "TibMonitor|" + beanName + "|" + Command + "|" + measure;
	            	testing.populateDynamicMeasure(envs, TIBCO_PROCESS_GROUP, TIBCO_PROCESSCOUNT_METRIC, measureName, (double)Value);
	            	 	
                }else if (measure.equalsIgnoreCase("ActiveProcessCount")){
	            	measureName = "TibMonitor|" + beanName + "|" + Command + "|" + measure;
	            	testing.populateDynamicMeasure(envs, TIBCO_PROCESS_GROUP, TIBCO_ACTIVEPROCESSCOUNT_METRIC, measureName, (double)Value);	
                }else if (measure.equalsIgnoreCase("New Errors") && Command.equalsIgnoreCase("getStatus")){
	            	measureName = "TibMonitor|" + beanName + "|" + Command + "|" + measure;
	            	testing.populateDynamicMeasure(envs, TIBCO_STATUS_GROUP, TIBCO_NEWERRORS_METRIC, measureName, (double)Value);		
	            	
                }else if (measure.equalsIgnoreCase("Process ID") && Command.equalsIgnoreCase("getStatus")){
	            	measureName = "TibMonitor|" + beanName + "|" + Command + "|" + measure;
	            	testing.populateDynamicMeasure(envs, TIBCO_STATUS_GROUP, TIBCO_PROCESSID_METRIC, measureName, (double)Value);	
	            	
                }else if (measure.equalsIgnoreCase("Total Errors") && Command.equalsIgnoreCase("getStatus")){
	            	measureName = "TibMonitor|" + beanName + "|" + Command + "|" + measure;
	            	testing.populateDynamicMeasure(envs, TIBCO_STATUS_GROUP, TIBCO_TOTALERRORS_METRIC, measureName, (double)Value);	
	            	
                }else if (measure.equalsIgnoreCase("Uptime") && Command.equalsIgnoreCase("getStatus")){
	            	measureName = "TibMonitor|" + beanName + "|" + Command + "|" + measure;
	            	testing.populateDynamicMeasure(envs, TIBCO_STATUS_GROUP, TIBCO_STATUSUPTIME_METRIC, measureName, (double)Value);

	            //DB	
                }else if (measure.equalsIgnoreCase("Connection-State") && Command.equalsIgnoreCase("ListDbConnections")){
	            	measureName = "TibMonitor|" + beanName + "|" + Command + "|" + measure;
	            	testing.populateDynamicMeasure(envs, TIBCO_DB_GROUP, TIBCO_CONNECTIONSTATE_METRIC, measureName, (double)Value);	
                }else if (measure.equalsIgnoreCase("Idle-Time") && Command.equalsIgnoreCase("ListDbConnections")){
	            	measureName = "TibMonitor|" + beanName + "|" + Command + "|" + measure;
	            	testing.populateDynamicMeasure(envs, TIBCO_DB_GROUP, TIBCO_IDLETIME_METRIC, measureName, (double)Value);

                }else if (measure.equalsIgnoreCase("Status") && Command.equalsIgnoreCase("GetExecInfo")){
	            	measureName = "TibMonitor|" + beanName + "|" + Command + "|" + measure;
	            	testing.populateDynamicMeasure(envs, TIBCO_EXECINFO_GROUP, TIBCO_STATUS_METRIC, measureName, (double)Value);
                }else if (measure.equalsIgnoreCase("Threads") && Command.equalsIgnoreCase("GetExecInfo")){
	            	measureName = "TibMonitor|" + beanName + "|" + Command + "|" + measure;
	            	testing.populateDynamicMeasure(envs, TIBCO_EXECINFO_GROUP, TIBCO_THREADS_METRIC, measureName, (double)Value);
                }else if (measure.equalsIgnoreCase("Uptime") && Command.equalsIgnoreCase("GetExecInfo")){
	            	measureName = "TibMonitor|" + beanName + "|" + Command + "|" + measure;
	            	testing.populateDynamicMeasure(envs, TIBCO_EXECINFO_GROUP, TIBCO_EXECINFOUPTIME_METRIC, measureName, (double)Value);
                }else if (measure.equalsIgnoreCase("Version") && Command.equalsIgnoreCase("GetExecInfo")){
	            	measureName = "TibMonitor|" + beanName + "|" + Command + "|" + measure;
	            	testing.populateDynamicMeasure(envs, TIBCO_EXECINFO_GROUP, TIBCO_VERSION_METRIC, measureName, (double)Value);
	            	
                }else if (measure.equalsIgnoreCase("FreeBytes") && Command.equalsIgnoreCase("GetMemoryUsage")){
	            	measureName = "TibMonitor|" + beanName + "|" + Command + "|" + measure;
	            	testing.populateDynamicMeasure(envs, TIBCO_MEMORYINFO_GROUP, TIBCO_FREEBYTES_METRIC, measureName, (double)Value);
                }else if (measure.equalsIgnoreCase("PercentUsed") && Command.equalsIgnoreCase("GetMemoryUsage")){
	            	measureName = "TibMonitor|" + beanName + "|" + Command + "|" + measure;
	            	testing.populateDynamicMeasure(envs, TIBCO_MEMORYINFO_GROUP, TIBCO_PERCENTUSED_METRIC, measureName, (double)Value);
                }else if (measure.equalsIgnoreCase("TotalBytes") && Command.equalsIgnoreCase("GetMemoryUsage")){
	            	measureName = "TibMonitor|" + beanName + "|" + Command + "|" + measure;
	            	testing.populateDynamicMeasure(envs, TIBCO_MEMORYINFO_GROUP, TIBCO_TOTALBYTES_METRIC, measureName, (double)Value);
                }else if (measure.equalsIgnoreCase("UsedBytes") && Command.equalsIgnoreCase("GetMemoryUsage")){
	            	measureName = "TibMonitor|" + beanName + "|" + Command + "|" + measure;
	            	testing.populateDynamicMeasure(envs, TIBCO_MEMORYINFO_GROUP, TIBCO_USEDBYTES_METRIC, measureName, (double)Value);
	            	
                }else {
                	if (log.isLoggable(Level.WARNING)){
                		log.warning("Tibco recording data does not match a monitored type.");
                	}
                }	                
	                
	        }
	    } catch (Exception e) {
	    	if (log.isLoggable(Level.WARNING)){
	            log.warning("" + e);
	    	}
	    }
	}
}