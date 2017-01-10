package com.WVU.iAttend;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matt on 1/3/2017.
 */

public class User {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private List<Long> studentOf = new ArrayList<Long>();
    private List<Long> professorOf = new ArrayList<Long>();
    private List<AttendanceRecord> attendanceRec = new ArrayList<AttendanceRecord>();
    private long lastLoggedLocationLong;
    private long LastLoggedLocationLat;


    public User(String firstName,String lastName, String email, String password){
        studentOf = null;
        professorOf = null;
        lastLoggedLocationLong = 0;
        LastLoggedLocationLat = 0;


    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getLastLoggedLocationLong() {
        return lastLoggedLocationLong;
    }

    public void setLastLoggedLocationLong(Long lastLoggedLocationLong) {
       this.lastLoggedLocationLong = lastLoggedLocationLong;
    }

    public long getLastLoggedLocationLat() {
        return LastLoggedLocationLat;
    }

    public void setLastLoggedLocationLat(Long lastLoggedLocationLat) {
        this.LastLoggedLocationLat = lastLoggedLocationLat;
    }

    public List<AttendanceRecord> getAttendanceRec() {
        return attendanceRec;
    }

    public void setAttendanceRec(List<AttendanceRecord> AttendanceRec) {
        this.attendanceRec = AttendanceRec;
    }

}
