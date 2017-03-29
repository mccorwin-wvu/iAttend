package com.WVU.iAttend;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.DurationFieldType;

public class DateStuff {

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

    public String stringDate(int day, int month, int year){


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

//
//    public static void main(String[] args) {
//
//
//        String date1 = "24$5$2017";
//        String date2 = "27$8$2017";
//        String daysOfWeek = "mon$wed$fri$";
//
//
//
//        System.out.println(Arrays.toString(getDays(date1,date2,daysOfWeek).split("-")));
//
//
//
//    }



}
