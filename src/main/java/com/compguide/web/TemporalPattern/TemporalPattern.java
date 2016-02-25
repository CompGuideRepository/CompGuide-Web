/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compguide.web.TemporalPattern;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author António
 */
public class TemporalPattern {

    private static final Map<String, Long> units = new HashMap<String, Long>();

    static {
        /*1*/ units.put("second", 1000L);
        /*2*/ units.put("minute", 1000L);
        /*3*/ units.put("hour", 3600000L);
        /*4*/ units.put("day", 86400000L);
        /*5*/ units.put("week", 604800000L);
        /*6*/ units.put("month", 2629743830L);
        /*7*/ units.put("year", 31556952000L);
    }

    public static Long temporalUnitToMilliseconds(String unit, double value) {
        Long result = null;

        result = Math.round(units.get(unit) * value);

        return result;
    }

    public static Date endDateFromStartDate(Date startDate, String unit, double value) {

        Date finalDate = new Date(((Date) startDate.clone()).getTime()
                + Math.round(units.get(unit) * value));

        return finalDate;
    }

    public static boolean isAllDay(String unit, double value) {
        if (units.get(unit) > units.get("day")) {
            return true;
        }

        return false;
    }

    public static boolean isAllDay(long timeMilliSecond) {
        if (timeMilliSecond > units.get("day")) {
            return true;
        }

        return false;
    }

}
