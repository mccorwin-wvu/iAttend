package com.WVU.iAttend;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Matt on 1/3/2017.
 */

public class Classes {
    private String className;
    private DateTime startTime;
    private String joinCode;
    private boolean locVer;
    private boolean codeVer;
    private long admin;
    private List<Long> students = new ArrayList<Long>();

    //Test for commit again

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public DateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(DateTime startTime) {
        this.startTime = startTime;
    }

    public String getJoinCode() {
        return joinCode;
    }

    public void setJoinCode(String joinCode) {
        this.joinCode = joinCode;
    }

    public boolean isLocVer() {
        return locVer;
    }

    public void setLocVer(boolean locVer) {
        this.locVer = locVer;
    }

    public boolean isCodeVer() {
        return codeVer;
    }

    public void setCodeVer(boolean codeVer) {
        this.codeVer = codeVer;
    }

    public long getAdmin() {
        return admin;
    }

    public void setAdmin(long admin) {
        this.admin = admin;
    }
}
