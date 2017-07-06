package DataBase.Entities;

import com.google.gson.Gson;

/**
 * Created with IntelliJ IDEA. User: tiago Date: 31-07-2013 Time: 16:41 To
 * change this template use File | Settings | File Templates.
 */
public class Guideexec {

    private String idguideline;
    private String idguideexec;
    private String iduser;
    private String time;
    private String start;
    private String idpatient;
    private String nextTasks;
    private String description;
    private Boolean completed;

    public void Guideexec() {

    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public static Guideexec fromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, Guideexec.class);
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

    public String getIdpatient() {
        return idpatient;
    }

    public void setIdpatient(String idpatient) {
        this.idpatient = idpatient;
    }

    public String getIduser() {
        return iduser;
    }

    public void setIduser(String iduser) {
        this.iduser = iduser;
    }

    public String getIdguideline() {
        return idguideline;
    }

    public void setIdguideline(String idguideline) {
        this.idguideline = idguideline;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNextTasks() {
        return nextTasks;
    }

    public void setNextTasks(String nextTasks) {
        this.nextTasks = nextTasks;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }
}
