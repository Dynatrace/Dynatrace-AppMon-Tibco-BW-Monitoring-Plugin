package com.dynatrace.diagnostics.plugins.jmx.datacollection;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import javax.management.MBeanServerConnection;
import javax.management.ObjectInstance;
import javax.management.ObjectName;
import java.util.logging.Logger;
import java.util.logging.Level;

import com.dynatrace.diagnostics.plugins.jmx.variableholder.TibcoStats;

public class TibcoDataCollection {

	private static final Logger log = Logger.getLogger(TibcoDataCollection.class.getName());
	private ArrayList<TibcoStats> test2;
	
	public TibcoDataCollection(ArrayList<TibcoStats> test2){
        this.test2 = test2;
	}
	
    Options newOpt = new Options();
    SubCommand newSub = new SubCommand();
    private String NameOfBean = "";

    public ArrayList<TibcoStats> doBean2(MBeanServerConnection mbsc, ObjectInstance instance, String command[], boolean list, boolean c, String BeanName, String[] ignoreProcess)
            throws Exception {
    	
    	if (command != null) {
            c = true;
            for (int i = 0; i < command.length; i++) {
            	test2 = newSub.doSubCommand2(mbsc, instance, command[i], c, BeanName, ignoreProcess);
            }
        }
        return test2;
    }    
    
    
    public ArrayList<TibcoStats> executeTib(MBeanServerConnection mbsc, boolean list, String acommand[], boolean c, boolean p, int w, String[] ignoreProcess)
            throws Exception {
        
        String beanName;
        String command[];
        beanName = acommand[0].toString();
        NameOfBean = beanName;
        command = new String[acommand.length - 1];
        int i = 0;
        while (i < acommand.length - 1) {
            command[i] = acommand[i + 1];
            i++;
        }
    
        ObjectName objName = beanName != null && beanName.length() > 0 ? new ObjectName(beanName) : null;
        Set<ObjectInstance> beans = mbsc.queryMBeans(objName, null);
        for (Iterator<ObjectInstance> ii = beans.iterator(); ii.hasNext();) {
        	boolean b = true;
        	boolean l = true;
        	Object obj = ii.next();
        	if (obj instanceof ObjectName) {
        		String testing1 = ((ObjectName) obj).getCanonicalName();
        		//Checking to see what MBean we want to monitor or list all MBeans and Options.              

        		if (b) {
                
        			ObjectName objName1 = testing1 != null && testing1.length() > 0 ? new ObjectName(testing1) : null;
        			Set<ObjectInstance> beans1 = mbsc.queryMBeans(objName1, null);
        			ObjectInstance instance = (ObjectInstance) beans1.iterator().next();
        			doBean2(mbsc, instance, command, l, c, NameOfBean, ignoreProcess);
        		}
        	} else if (obj instanceof ObjectInstance) {
        		String testing2 = ((ObjectInstance) obj).getObjectName().getCanonicalName();
        		//Checking to see what MBean we want to monitor or list all MBeans and Options.

        		//Get selective as of what we look at...  
        		if (b) {
                
        			ObjectName objName2 = testing2 != null && testing2.length() > 0 ? new ObjectName(testing2) : null;
        			Set<ObjectInstance> beans2 = mbsc.queryMBeans(objName2, null);
        			ObjectInstance instance = (ObjectInstance) beans2.iterator().next();

        			//pass name to doBean
        			doBean2(mbsc, instance, command, l, c, NameOfBean, ignoreProcess);
        		}
        	} else {
        		if (log.isLoggable(Level.WARNING)){
        			log.warning((new StringBuilder()).append("Unexpected object type: ").append(obj).toString());
        		}
        	}
        }
        return test2;
    }    
}
