package com.dynatrace.diagnostics.plugins.jmx.variableholder;

import java.util.*;

public class TibcoTimeHolder {

    private ArrayList<TibcoStats> holder = new ArrayList<TibcoStats>();

    public synchronized ArrayList<TibcoStats> getArrayList() {
        return holder;
    }

    public synchronized void setArrayList(ArrayList<TibcoStats> obj) throws Exception {
        holder = obj;
    }

    public synchronized void addServerStats(TibcoStats parm) {
        holder.add(parm);
    }

    public synchronized void addServerStatsList(ArrayList<TibcoStats> obj) {
        holder.addAll(obj);
    }

    public void Clearvalue() throws Exception {
        holder.clear();
    }
}