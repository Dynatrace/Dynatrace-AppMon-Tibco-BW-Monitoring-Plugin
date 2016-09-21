package com.dynatrace.diagnostics.plugins.jmx;

import java.io.IOException;
import java.util.logging.Logger;
import java.util.logging.Level;
import javax.management.MBeanServerConnection;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import com.dynatrace.diagnostics.plugins.jmx.datacollection.Credentials;


public class Connection  {
    
    private static MBeanServerConnection mbsc;
    private static JMXConnector jmxc;
    private static final Logger log = Logger.getLogger(Connection.class.getName());
    
    public MBeanServerConnection Connect (String hostport, String userpass) throws Exception {
        JMXServiceURL rmiurl = null;
        
        Credentials datacollection = new Credentials();
        String hostname = hostport;
        int index = hostport.indexOf(':');
        if (index > 0) {
            hostname = hostname.substring(0, index);
        }
        rmiurl = new JMXServiceURL((new StringBuilder()).append("service:jmx:rmi://").append(hostport).append("/jndi/rmi://").append(hostport).append("/jmxrmi").toString());
        
        try {
        	jmxc = JMXConnectorFactory.connect(rmiurl, datacollection.formatCredentials(userpass));
        } catch (Exception p) {
        	if (log.isLoggable(Level.WARNING)){
        		log.warning("Not able to connect to: " + hostport + ". Exception: " + p);
        	}
        	return null;
        }
        
        try {
        	mbsc = jmxc.getMBeanServerConnection();
        } catch (Exception p) {
        	if (log.isLoggable(Level.WARNING)){
        		log.warning("Not able to connect to: " + hostport + ". Exception: " + p);
        	}
        	return null;
        }
        
        if (log.isLoggable(Level.INFO)){
            log.info("Returning Tibco BW Connection");
        }
        
        return mbsc;
    }
    
    public static void closeConnection() throws IOException {
    	if (jmxc != null) {
    		try{
    			jmxc.close();
    		}catch (Exception e){
    			if (log.isLoggable(Level.WARNING)){
    				log.warning("Error closing connection: "+ e);
    			}
    		}
    		jmxc=null;
    	}
    }
}
