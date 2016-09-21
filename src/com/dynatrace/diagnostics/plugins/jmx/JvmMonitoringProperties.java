package com.dynatrace.diagnostics.plugins.jmx;

import javax.management.MBeanServerConnection;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXServiceURL;

public class JvmMonitoringProperties {
	private String dtServer;
	private String jmxPassword;
	private JMXServiceURL jmxServiceUrl;
	private JMXConnector jmxConnector;
	private MBeanServerConnection mbsConnection;
	private String objectNamePrefix;
	private String jxmEnvironment;
	private String mbeans;
	private String attributes;
	private String serverName;
	private Boolean jvmMonitor;
	private Boolean tibMonitor;
	private String processIgnore;
	
	public String getProcessIgnore() {
		return processIgnore;
	}
	public void setProcessIgnore(String processIgnore) {
		this.processIgnore = processIgnore;
	}
	public Boolean getjvmMonitor() {
		return jvmMonitor;
	}
	public void setjvmMonitor(Boolean jvmMonitor) {
		this.jvmMonitor = jvmMonitor;
	}	
	public Boolean gettibMonitor() {
		return tibMonitor;
	}
	public void settibMonitor(Boolean tibMonitor) {
		this.tibMonitor = tibMonitor;
	}	
	public String getserverName() {
		return serverName;
	}
	public void setserverName(String serverName) {
		this.serverName = serverName;
	}
	public String getAttributes() {
		return attributes;
	}
	public void setAttributes(String attributes) {
		this.attributes = attributes;
	}
	public String getMbeans() {
		return mbeans;
	}
	public void setMbeans(String mbeans) {
		this.mbeans = mbeans;
	}
	public String getJmxEnvironment() {
		return jxmEnvironment;
	}
	public void setJmxEnvironment(String jxmEnvironment) {
		this.jxmEnvironment = jxmEnvironment;
	}
	public String getDtServer() {
		return dtServer;
	}
	public void setDtServer(String dtServer) {
		this.dtServer = dtServer;
	}
	public String getJmxPassword() {
		return jmxPassword;
	}
	public void setJmxPassword(String jmxPassword) {
		this.jmxPassword = jmxPassword;
	}
	public JMXServiceURL getJmxServiceUrl() {
		return jmxServiceUrl;
	}
	public void setJmxServiceUrl(JMXServiceURL jmxServiceUrl) {
		this.jmxServiceUrl = jmxServiceUrl;
	}
	public JMXConnector getJmxConnector() {
		return jmxConnector;
	}
	public void setJmxConnector(JMXConnector jmxConnector) {
		this.jmxConnector = jmxConnector;
	}
	public MBeanServerConnection getMbsConnection() {
		return mbsConnection;
	}
	public void setMbsConnection(MBeanServerConnection mbsConnection) {
		this.mbsConnection = mbsConnection;
	}
	public String getObjectNamePrefix() {
		return objectNamePrefix;
	}
	public void setObjectNamePrefix(String objectNamePrefix) {
		this.objectNamePrefix = objectNamePrefix;
	}
}
