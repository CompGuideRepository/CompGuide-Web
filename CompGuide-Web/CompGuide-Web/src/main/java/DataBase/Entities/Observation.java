package DataBase.Entities;

import com.google.gson.Gson;

/**
 * Created by IntelliJ IDEA. Observation: tiago Date: 18/07/12 Time: 10:25 PM
 */
public class Observation {

    private String idobservation;
    private String parameter;
    private String unit;
    private String parameteridentifier;
    private String task;
    private String variablename;
    private String parametervalue;
    private String time;
    private String idguideexec;
    private Boolean isnumeric;
    private String guideline;
    private String user;
    private String patient;

    public Observation() {
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public static Observation fromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, Observation.class);
    }

    public String toJsonListElement() {
        return "{\"idobservation\":\"" + idobservation + "\",\"parameter\":\"" + parameter + "\",\"parametervalue\":\"" + parametervalue + "\",\"idguideexec\":\"" + idguideexec + "\"}";
    }

    // getters and setters
    public void setIdobservation(String idobservation) {
        this.idobservation = idobservation;
    }

    public String getIdobservation() {
        return this.idobservation;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getParameteridentifier() {
        return parameteridentifier;
    }

    public void setParameteridentifier(String parameteridentifier) {
        this.parameteridentifier = parameteridentifier;
    }

    public String getVariablename() {
        return variablename;
    }

    public void setVariablename(String variablename) {
        this.variablename = variablename;
    }

    public String getParametervalue() {
        return parametervalue;
    }

    public void setParametervalue(String parametervalue) {
        this.parametervalue = parametervalue;
    }

    public String getIdguideexec() {
        return idguideexec;
    }

    public void setTipo(String idguideexec) {
        this.idguideexec = idguideexec;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public Boolean getIsnumeric() {
        return isnumeric;
    }

    public void setIsnumeric(Boolean isnumeric) {
        this.isnumeric = isnumeric;
    }

    public String getGuideline() {
        return guideline;
    }

    public void setGuideline(String guideline) {
        this.guideline = guideline;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPatient() {
        return patient;
    }

    public void setPatient(String patient) {
        this.patient = patient;
    }
}
