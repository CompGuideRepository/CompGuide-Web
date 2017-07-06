package com.compguide.web.Execution.Entities;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA. User: tiago Date: 20-08-2013 Time: 22:43 To
 * change this template use File | Settings | File Templates.
 */
public class Question extends ClinicalTask {

    private ArrayList<Parameter> parameters;
    private WaitingTime waitingTime;

    public Question() {
        super();
        parameters = new ArrayList<Parameter>();
    }

    public static Question fromJson(String json) {

        Gson gson = new GsonBuilder()
                .registerTypeHierarchyAdapter(ClinicalTask.class, new ClinicalTaskAdapter())
                .registerTypeHierarchyAdapter(ClinicalAction.class, new ClinicalActionAdapter())
                .create();
        return gson.fromJson(json, Question.class);
    }

    public String toJson() {
        Gson gson = new GsonBuilder()
                .registerTypeHierarchyAdapter(ClinicalTask.class, new ClinicalTaskAdapter())
                .registerTypeHierarchyAdapter(ClinicalAction.class, new ClinicalActionAdapter())
                .create();

        return gson.toJson(this);
    }

    public void addParameter(Parameter parameter) {
        this.parameters.add(parameter);

    }

    public ArrayList<Parameter> getParameters() {
        return parameters;
    }

    public void setParameters(ArrayList<Parameter> parameters) {
        this.parameters = parameters;
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

    public boolean isTemporalPattern() {
        if (isWaitingTime()) {
            return true;
        }
        return false;
    }

}
