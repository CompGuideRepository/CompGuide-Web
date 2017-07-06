package com.compguide.web.Execution.Entities;

import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA. User: tiago Date: 20-08-2013 Time: 14:58 To
 * change this template use File | Settings | File Templates.
 */
public class Condition {

    private String id;
    private String value;
    private Boolean isNumeric;
    private ArrayList<TemporalRestriction> temporalRestriction;
    private String comparisonOperator;
    private ArrayList<String> parameterIdentifier;
    private String conditionParameter;
    private String unit;

    public static Condition fromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, Condition.class);
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public Condition() {
        temporalRestriction = new ArrayList<TemporalRestriction>();
        parameterIdentifier = new ArrayList<String>();
    }

    public void addTemporalRestriction(TemporalRestriction temporalRestriction) {
        this.temporalRestriction.add(temporalRestriction);
    }

    public void addParameterIdentifier(String parameterIdentifier) {
        this.parameterIdentifier.add(parameterIdentifier);
    }

    public String getComparisonOperator() {
        return comparisonOperator;
    }

    public void setComparisonOperator(String comparisonOperator) {
        this.comparisonOperator = comparisonOperator;
    }

    public Boolean getIsNumeric() {
        return isNumeric;
    }

    public void setIsNumeric(Boolean numeric) {
        isNumeric = numeric;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getConditionParameter() {
        return conditionParameter;
    }

    public void setConditionParameter(String conditionParameter) {
        this.conditionParameter = conditionParameter;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<TemporalRestriction> getTemporalRestriction() {
        return temporalRestriction;
    }

    public void setTemporalRestriction(ArrayList<TemporalRestriction> temporalRestriction) {
        this.temporalRestriction = temporalRestriction;
    }

    public ArrayList<String> getParameterIdentifier() {
        return parameterIdentifier;
    }

    public void setParameterIdentifier(ArrayList<String> parameterIdentifier) {
        this.parameterIdentifier = parameterIdentifier;
    }
}
