package com.compguide.web.Execution.Entities;

import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA. User: tiago Date: 23-08-2013 Time: 23:52 To
 * change this template use File | Settings | File Templates.
 */
public class Outcome {

    private ArrayList<ConditionSet> outcomeConditionSet;

    public Outcome() {
        outcomeConditionSet = new ArrayList<ConditionSet>();

    }

    public static Outcome fromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, Outcome.class);
    }

    public boolean isEmpty() {

        if (outcomeConditionSet.isEmpty()) {
            return true;
        }
        return false;
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public void addOutcome(ConditionSet conditionSet) {
        outcomeConditionSet.add(conditionSet);

    }

    public ArrayList<ConditionSet> getOutcomeConditionSet() {
        return outcomeConditionSet;
    }

    public void setOutcomeConditionSet(ArrayList<ConditionSet> outcomeConditionSet) {
        this.outcomeConditionSet = outcomeConditionSet;
    }
}
