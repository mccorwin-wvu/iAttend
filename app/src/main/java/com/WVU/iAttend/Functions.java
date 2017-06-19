package com.WVU.iAttend;

/**
 * Created by Matt on 1/4/2017.
 */

import android.app.Activity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import org.apache.commons.net.time.TimeTCPClient;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Days;
import org.joda.time.DurationFieldType;

import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;



public final class Functions {

    public static String nextCode() {
        return new BigInteger(40, new SecureRandom()).toString(32);

    }

    public static DateTime getInternetTime() {


        try {
            TimeTCPClient client = new TimeTCPClient();
            try {

                // Set timeout of 60 seconds
                client.setDefaultTimeout(60000);
                // Connecting to time server
                // Other time servers can be found at : http://tf.nist.gov/tf-cgi/servers.cgi#
                // Make sure that your program NEVER queries a server more frequently than once every 4 seconds
                client.connect("nist1-macon.macon.ga.us");
                DateTime returnDateTime = new DateTime(client.getDate());
                return returnDateTime;

            } finally {
                client.disconnect();
            }
        } catch (IOException e) {

            e.printStackTrace();
            return null;
        }
    }


    public static boolean CheckZone(double lat, double log) {


        DateTime now = getInternetTime();
        DateTime nowDevice = new DateTime();

        System.out.println(TimeZoneMapper.latLngToTimezoneString(lat, log));
        System.out.println(nowDevice.getZone().toString());
        System.out.println(nowDevice.toString());

        if (nowDevice.getZone().toString().compareTo(TimeZoneMapper.latLngToTimezoneString(lat, log)) == 0) {


            DateTime estZone = nowDevice.withZone(DateTimeZone.forTimeZone(TimeZone.getTimeZone("America/New_York")));

            if (Math.abs((now.getMillis() / 1000) - (estZone.getMillis() / 1000)) < 180) {

                return true;

            }


        } else {
            return false;
        }
        return false;


    }


    public static boolean checkCords(double lat, double log, double classLat, double classLog) {


        if ((Math.abs(lat - classLat) < 000.001) && (Math.abs(log - classLog) < 000.001)) {
            return true;
        }

        return false;


    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }


    public static String getDays(String start, String end,String daysOfWeek){


        if(start == null || end == null){
            return null;
        }




        String [] srtDay = start.split("\\$");
        String [] endDay = end.split("\\$");

        if(srtDay.length != 3 || endDay.length != 3){

            return null;
        }

        int [] days_of_week = getDays(daysOfWeek);

        DateTime startDate = new DateTime(Integer.parseInt(srtDay[2]),Integer.parseInt(srtDay[1]),Integer.parseInt(srtDay[0]),0,0);
        DateTime endDate = new DateTime(Integer.parseInt(endDay[2]),Integer.parseInt(endDay[1]),Integer.parseInt(endDay[0]),0,0);






        int days = Days.daysBetween(startDate, endDate).getDays();
        List<DateTime> dates = new ArrayList<DateTime>(days);
        for (int i=0; i < days; i++) {
            DateTime d = startDate.withFieldAdded(DurationFieldType.days(), i);

            for(int j:days_of_week){

                if(j == d.getDayOfWeek()){
                    dates.add(d);
                }

            }


        }


        String datesReturn ="";

        for(DateTime day:dates){

            String tempDay  = day.getDayOfMonth() + "$" + day.getMonthOfYear()+ "$" + day.getYear()+"-";
            datesReturn = datesReturn+tempDay;

        }






        return datesReturn;



    }

    public static String stringDate(int day, int month, int year){


        return Integer.toString(day)+"$"+Integer.toString(month)+"$"+Integer.toString(year);






    }

    public static int[] getDays(String days){
        if(days == null){
            return null;
        }
        String [] d = days.split("\\$");

        int[] returnDays = new int [d.length];

        for(int i =0; i< d.length; i++){

            if(d[i].compareTo("mon")==0){returnDays[i] = 1;} else if(d[i].compareTo("tues")==0){returnDays[i] = 2;} else if(d[i].compareTo("wed")==0){returnDays[i] = 3;}
            else if(d[i].compareTo("thurs")==0){returnDays[i] = 4;} else if(d[i].compareTo("fri")==0){returnDays[i] = 5;} else if(d[i].compareTo("sat")==0){returnDays[i] = 6;}
            else if(d[i].compareTo("sun")==0){returnDays[i] = 7;}


        }

        return returnDays;



    }






}

