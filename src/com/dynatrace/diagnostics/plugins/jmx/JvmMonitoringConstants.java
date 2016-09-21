package com.dynatrace.diagnostics.plugins.jmx;

public interface JvmMonitoringConstants {
	// Plugin's configuration parameter's constants
	public static final String CONFIG_JMX_ENVIRONMENT = "environment";
	public static final String CONFIG_DT_SERVER = "dtServer";
	public static final String CONFIG_JMX_PASSWORD = "jmxPassword";
	public static final String CONFIG_MBEAN_GROUP = "mBeans";
	public static final String CONFIG_MBEAN_ATTRIBUTES = "attributes";
	public static final String CONFIG_SERVER_NAME = "serverName";
	public static final String CONFIG_TIBMONITOR_NAME = "tibMonitor";
	public static final String CONFIG_JVMMONITOR_NAME = "jvmMonitor";
	public static final String CONFIG_TIBCO_PROCESSIGNORE = "ignoreProcess";
	
	// JVM Connection Group
	public static final String JVM_CONNECTION_GROUP = "JVM Connection Group";
	public static final String JVM_CONNECTION_METRIC = "JVM Connection Status";
	public static final String JVM_MONITORTIME_METRIC = "JVM Monitor Time";
	
	// JVM Monitor Group
	public static final String JVM_MONITOR_GROUP = "JVM Monitor Group";	
	public static final String JVM_DATA_METRIC = "JVM DATA";
	public static final String JVM_MARKSWEEPTIME_METRIC = "Old Collection Time";
	public static final String JVM_MARKSWEEPCOUNT_METRIC = "Old Collection Count";
	public static final String JVM_MARKSWEEPCOMPACTS_METRIC = "Old Total Compacts";
	public static final String JVM_MARKSWEEPGCTIME_METRIC = "Old Time In GC";
	public static final String JVM_COPYTIME_METRIC = "Young Collection Time";
	public static final String JVM_COPYCOUNT_METRIC = "Young Collection Count";
	public static final String JVM_COPYCOMPACTS_METRIC = "Young Total Compacts";	
	public static final String JVM_COPYGCTIME_METRIC = "Young Time In GC";
	public static final String JVM_MEMORYUSED_METRIC = "Heap Memory Used";
	public static final String JVM_MEMORYMAX_METRIC = "Heap Memory Max";
	public static final String JVM_MEMORYCOMMITTED_METRIC = "Heap Memory Committed";
	public static final String JVM_MEMORYINIT_METRIC = "Heap Memory Init";
	public static final String JVM_NONMEMORYUSED_METRIC = "NonHeap Memory Used";
	public static final String JVM_NONMEMORYMAX_METRIC = "NonHeap Memory Max";
	public static final String JVM_NONMEMORYCOMMITTED_METRIC = "NonHeap Memory Committed";
	public static final String JVM_NONMEMORYINIT_METRIC = "NonHeap Memory Init";
	public static final String JVM_MAXHEAPSIZE_METRIC = "Max Heap Size";
	public static final String JVM_THREADPEAK_METRIC = "Peak Thread Count";
	public static final String JVM_THREADSTARTED_METRIC = "Total Started ThreadCount";
	public static final String JVM_THREADTOTAL_METRIC = "Thread Count";
	public static final String JVM_THREADDAEMON_METRIC = "Daemon Thread Count";
	public static final String JVM_OPCPUTIME_METRIC = "Process Cpu Time";
	public static final String JVM_OPSYSLOADAVG_METRIC = "System Load Average";
	public static final String JVM_OPPROCCAP_METRIC = "Processing Capacity";
	public static final String JVM_OPPROCCPUTIMENS_METRIC = "Process Cpu Time By NS";
	public static final String JVM_OPAVAILPROC_METRIC = "Available Processors";
	public static final String JVM_FREESWAPSIZE_METRIC = "Free Swap Space Size";
	public static final String JVM_TOTALSWAPSIZE_METRIC = "Total Swap Space Size";
	public static final String JVM_JVMUPTIME_METRIC = "JVM Uptime";
	
	//TIBCO BW Webservice Group
	public static final String TIBCO_WEBSERVICE_GROUP = "TIBCO Webservice Group";
	public static final String TIBCO_ABORTED_METRIC = "Aborted";
	public static final String TIBCO_AVERAGEELAPSED_METRIC = "Average Elapsed";
	public static final String TIBCO_AVERAGEEXECUTION_METRIC = "Average Execution";
	public static final String TIBCO_CHECKPOINTED_METRIC = "Checkpointed";
	public static final String TIBCO_COMPLETED_METRIC = "Completed";
	public static final String TIBCO_COUNTSINCERESET_METRIC = "Count Since Reset";
	public static final String TIBCO_CREATED_METRIC = "Created";
	public static final String TIBCO_MAXELAPSED_METRIC = "Max Elapsed";
	public static final String TIBCO_MAXEXECUTION_METRIC = "Max Execution";
	public static final String TIBCO_MINELAPSED_METRIC = "Min Elapsed";
	public static final String TIBCO_MINEXECUTION_METRIC = "Min Execution";
	public static final String TIBCO_MOSTRECENTELAPSEDTIME_METRIC = "Most Recent Elapsed Time";
	public static final String TIBCO_MOSTRECENTEXECUTIONTIME_METRIC = "Most Recent Execution Time";
	public static final String TIBCO_QUEUED_METRIC = "Queued";
	public static final String TIBCO_SUSPENDED_METRIC = "Suspended";
	public static final String TIBCO_SWAPPED_METRIC = "Swapped";
	public static final String TIBCO_TIMESINCELASTUPDATE_METRIC = "Time Since Last Update";
	public static final String TIBCO_TOTALELAPSED_METRIC = "Total Elapsed";
	public static final String TIBCO_TOTALEXECUTION_METRIC = "Total Execution";
	public static final String TIBCO_CYCLEEXECUTION_METRIC = "Executions";
	
	//TIBCO BW Process Group
	public static final String TIBCO_PROCESS_GROUP = "TIBCO Process Group";
	public static final String TIBCO_ACTIVEPROCESSCOUNT_METRIC = "Active Process Count";	
	public static final String TIBCO_PROCESSCOUNT_METRIC = "Process Count";
	
	//TIBCO BW DB Group
	public static final String TIBCO_DB_GROUP = "TIBCO DB Group";
	public static final String TIBCO_IDLETIME_METRIC = "Idle Time";	
	public static final String TIBCO_CONNECTIONSTATE_METRIC = "Connection State";

	//TIBCO BW Status Group
	public static final String TIBCO_STATUS_GROUP = "TIBCO Status Group";
	public static final String TIBCO_NEWERRORS_METRIC = "New Errors";
	public static final String TIBCO_PROCESSID_METRIC = "Process ID";
	public static final String TIBCO_TOTALERRORS_METRIC = "Total Errors";
	public static final String TIBCO_STATUSUPTIME_METRIC = "Status Uptime";
	
	//TIBCO BW ExecInfo Group
	public static final String TIBCO_EXECINFO_GROUP = "TIBCO ExecInfo Group";
	public static final String TIBCO_STATUS_METRIC = "ExecInfo Status";
	public static final String TIBCO_THREADS_METRIC = "Threads";
	public static final String TIBCO_EXECINFOUPTIME_METRIC = "ExecInfo Uptime";
	public static final String TIBCO_VERSION_METRIC = "Version";
	
	//TIBCO BW MemoryInfo Group
	public static final String TIBCO_MEMORYINFO_GROUP = "TIBCO MemoryInfo Group";
	public static final String TIBCO_FREEBYTES_METRIC = "Free Bytes";
	public static final String TIBCO_PERCENTUSED_METRIC = "Percent Used";
	public static final String TIBCO_TOTALBYTES_METRIC = "Total Bytes";
	public static final String TIBCO_USEDBYTES_METRIC = "Used Bytes";
	
	// Java Virtual Machine Metric Group
	public static final String JMX_MEASURE_SPLIT_NAME = "Measure Name";
}
