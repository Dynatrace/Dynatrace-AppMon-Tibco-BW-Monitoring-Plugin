package com.dynatrace.diagnostics.plugins.jmx.variableholder;


public class TibcoStats {

     
    public String getValue() {
        return Value;
    }
    public void setValue(String Value) {
        this.Value = Value;
    }
    public String getCommand() {
        return Command;
    }
    public void setCommand(String Command) {
        this.Command = Command;
    }
    
     public String getbeanName() {
        return beanName;
    }

    public void setbeanName(String beanName) {
        this.beanName = beanName;
    }

    public String getmeasure() {
        return measure;
    }

    public void setmeasure(String measure) {
        this.measure = measure;
    }	
	
    public String getsubName() {
        return subName;
    }

    public void setsubName(String subName) {
        this.subName = subName;
    }	
	
	
    String Value;
    String Command;
    String beanName;
    String measure;
    String subName;
    
    @Override
    public String toString() {
    	return beanName + " " + Command + " " + subName + " " + measure  + " " + Value;
    }

    public TibcoStats(String input) {
        super();
        parse(input);
    }

    public TibcoStats() {
        super();
    }

    public void parse(String stuff) {
        String[] parts = stuff.split(" ");
        beanName = parts[0];
        Command = parts[1];
        subName = parts[2];
        measure = parts[3];
        Value = parts[4];
    }     
}
