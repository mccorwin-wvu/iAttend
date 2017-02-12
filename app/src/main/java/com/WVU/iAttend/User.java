package com.WVU.iAttend;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matt on 1/3/2017.
 */

public class User {
    private int user_id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private int confirmed;
    private String register_code;
    private String device_code;
    private String admin_class_list;
    private String user_class_list;

    public int getUser_id() {
        return user_id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public int getConfirmed() {
        return confirmed;
    }

    public String getRegister_code() {
        return register_code;
    }

    public String getDevice_code() {
        return device_code;
    }

    public String getAdmin_class_list() {
        return admin_class_list;
    }

    public String getUser_class_list() {
        return user_class_list;
    }

    public User(int user_id, String firstName, String lastName, String email, String password,
                int confirmed, String register_code, String device_code, String admin_class_list, String user_class_list ){

        this.user_id = user_id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.confirmed = confirmed;
        this.register_code=register_code;
        this.device_code = device_code;
        this.admin_class_list = admin_class_list;
        this.user_class_list = user_class_list;






    }









}
