package DataBase.Entities;

import com.google.gson.Gson;

/**
 * Created with IntelliJ IDEA. User: tiago Date: 31-07-2013 Time: 16:41 To
 * change this template use File | Settings | File Templates.
 */
public class Exam {

    private String idexam;
    private String idguideexec;
    private String name;
    private String description;
    private String time;
    private String guideline;
    private String user;
    private String patient;

    public void Exam() {

    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public static Exam fromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, Exam.class);
    }

    //GETTERS & SETTERS
    public String getIdguideexec() {
        return idguideexec;
    }

    public void setIdguideexec(String idguideexec) {
        this.idguideexec = idguideexec;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getIdexam() {
        return idexam;
    }

    public void setIdexam(String idexam) {
        this.idexam = idexam;
    }

    public String getName() {
        return name;
    }

    public String getGuideline() {
        return guideline;
    }

    public void setGuideline(String guideline) {
        this.guideline = guideline;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
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
