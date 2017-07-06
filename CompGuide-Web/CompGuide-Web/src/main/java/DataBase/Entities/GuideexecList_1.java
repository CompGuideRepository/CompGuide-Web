package com.compguide.web.DataBase.Entities;

import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA. User: tiago Date: 26-08-2013 Time: 19:56 To
 * change this template use File | Settings | File Templates.
 */
public class GuideexecList_1 {

    private ArrayList<Guideexec> guideexecs;

    GuideexecList_1() {
        guideexecs = new ArrayList<Guideexec>();
    }

    public static GuideexecList_1 fromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, GuideexecList_1.class);
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public static GuideexecList_1 getInstance() {
        GuideexecList_1 list = new GuideexecList_1();
        list.setGuideexecs(new ArrayList<Guideexec>());
        return list;
    }

    public void addGuideexec(Guideexec guideexec) {
        guideexecs.add(guideexec);
    }

    public void setGuideexecs(ArrayList<Guideexec> guideexecs) {
        this.guideexecs = guideexecs;

    }

    public ArrayList<Guideexec> getGuideexecs() {
        return new ArrayList<Guideexec>(this.guideexecs);
    }
}
