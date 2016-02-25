package com.compguide.web.Execution.Entities;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA. User: tiago Date: 20-08-2013 Time: 23:16 To
 * change this template use File | Settings | File Templates.
 */
public class Decision extends ClinicalTask {

    private ArrayList<Option> options;
    private WaitingTime waitingTime;

    public Decision() {
        options = new ArrayList<Option>();
    }

    public static Decision fromJson(String json) {

        Gson gson = new GsonBuilder()
                .registerTypeHierarchyAdapter(ClinicalTask.class, new ClinicalTaskAdapter())
                .registerTypeHierarchyAdapter(ClinicalAction.class, new ClinicalActionAdapter())
                .create();
        return gson.fromJson(json, Decision.class);
    }

    public String toJson() {
        Gson gson = new GsonBuilder()
                .registerTypeHierarchyAdapter(ClinicalTask.class, new ClinicalTaskAdapter())
                .registerTypeHierarchyAdapter(ClinicalAction.class, new ClinicalActionAdapter())
                .create();

        return gson.toJson(this);
    }

    public ArrayList<Option> getOptions() {
        return options;
    }

    public void setOptions(ArrayList<Option> options) {
        this.options = options;
    }

    public void addOption(Option option) {
        this.options.add(option);
    }

    public WaitingTime getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(WaitingTime waitingTime) {
        this.waitingTime = waitingTime;
    }

    public boolean isWaitingTime() {
        if (waitingTime != null) {
            return true;
        }
        return false;
    }
}
