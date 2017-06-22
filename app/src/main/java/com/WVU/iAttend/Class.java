package com.WVU.iAttend;

import java.io.Serializable;

/**
 * Created by Matt on 6/5/2017.
 */

public class Class implements Serializable {


    private int class_id, numberOfDays;
    private String className;
    private String startTime;
    private String endTime;
    private String daysOfWeek;
    private String startDate;
    private String endDate;
    private String joinCode;
    private String log;
    private String lat;
    private String locationEnabled;
    private String codeEnabled;
    private String admin_id;
    private String classRoster;
    private String dates;


    public Class(int class_id, int numberOfDays, String className, String startTime, String endTime, String daysOfWeek, String startDate, String endDate, String joinCode, String log, String lat, String locationEnabled,
                 String codeEnabled, String admin_id, String classRoster, String dates, String currentCode) {
        this.class_id = class_id;
        this.numberOfDays = numberOfDays;
        this.className = className;
        this.startTime = startTime;
        this.endTime = endTime;
        this.daysOfWeek = daysOfWeek;
        this.startDate = startDate;
        this.endDate = endDate;
        this.joinCode = joinCode;
        this.log = log;
        this.lat = lat;
        this.locationEnabled = locationEnabled;
        this.codeEnabled = codeEnabled;
        this.admin_id = admin_id;
        this.classRoster = classRoster;
        this.dates = dates;
        this.currentCode = currentCode;
    }



    public int getClass_id() {
        return class_id;
    }

    public void setClass_id(int class_id) {
        this.class_id = class_id;
    }

    public int getNumberOfDays() {
        return numberOfDays;
    }

    public void setNumberOfDays(int numberOfDays) {
        this.numberOfDays = numberOfDays;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDaysOfWeek() {
        return daysOfWeek;
    }

    public void setDaysOfWeek(String daysOfWeek) {
        this.daysOfWeek = daysOfWeek;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getJoinCode() {
        return joinCode;
    }

    public void setJoinCode(String joinCode) {
        this.joinCode = joinCode;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLocationEnabled() {
        return locationEnabled;
    }

    public void setLocationEnabled(String locationEnabled) {
        this.locationEnabled = locationEnabled;
    }

    public String getCodeEnabled() {
        return codeEnabled;
    }

    public void setCodeEnabled(String codeEnabled) {
        this.codeEnabled = codeEnabled;
    }

    public String getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(String admin_id) {
        this.admin_id = admin_id;
    }

    public String getClassRoster() {
        return classRoster;
    }

    public void setClassRoster(String classRoster) {
        this.classRoster = classRoster;
    }

    public String getDates() {
        return dates;
    }

    public void setDates(String dates) {
        this.dates = dates;
    }

    public String getCurrentCode() {
        return currentCode;
    }

    public void setCurrentCode(String currentCode) {
        this.currentCode = currentCode;
    }

    private String currentCode;












}
