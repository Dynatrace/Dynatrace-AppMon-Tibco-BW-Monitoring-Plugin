package com.dynatrace.diagnostics.plugins.jmx.variableholder;

import java.util.*;

public class JVMTimeHolder {

    private ArrayList<JVMStats> holder = new ArrayList<JVMStats>();

    public synchronized ArrayList<JVMStats> getArrayList() {
        return holder;
    }

    public synchronized void setArrayList(ArrayList<JVMStats> obj) throws Exception {
        holder = obj;
    }

    public synchronized void addServerStats(JVMStats parm) {
        holder.add(parm);
    }

    public synchronized void addServerStatsList(ArrayList<JVMStats> obj) {
        holder.addAll(obj);
    }

    public void Clearvalue() throws Exception {
        holder.clear();
    }
}