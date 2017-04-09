
package com.WVU.iAttend;


import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import org.joda.time.DateTime;
import org.joda.time.Days;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;

public class AttendanceRecordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_record);

        Typeface mytypeface = Typeface.createFromAsset(getAssets(),"Minecraftia-Regular.ttf");

        TextView attendanceRecordTitlev = (TextView) findViewById(R.id.attendanceRecordTitle);
        attendanceRecordTitlev.setTypeface(mytypeface);

        TextView DaysMissedv = (TextView) findViewById(R.id.DaysMissed);
        DaysMissedv.setTypeface(mytypeface);

        TextView DaysAttv = (TextView) findViewById(R.id.DaysAtt);
        DaysAttv.setTypeface(mytypeface);





        String classDays = "28$2$2017-2$3$2017-7$3$2017-9$3$2017-14$3$2017-16$3$2017-21$3$2017-23$3$2017-28$3$2017-30$3$2017-4$4$2017-6$4$2017-11$4$2017-13$4$2017-";
        String daysMised = "";
        String daysPres = "2$3$2017-7$3$2017-9$3$2017-16$3$2017-28$3$2017-30$3$2017-";
        String totalNumDays = "";
        String numMised = "4";
        String numPres = "6";


        DaysMissedv.setText("Days Absent: "+numMised);
        DaysAttv.setText("Days Present: "+numPres);



        MaterialCalendarView calendarView = (MaterialCalendarView) findViewById(R.id.calendarView);

        Calendar currentDay =  Calendar.getInstance();


        daysMised = missedDays(classDays);














        HashSet<CalendarDay> classDaysH = stringToCalDay(classDays);
        HashSet<CalendarDay> missedDaysH = stringToCalDay(daysMised);
        HashSet<CalendarDay> presDaysH = stringToCalDay(daysPres);





               // stringToCalDay(classDays);
        int blue = Color.BLUE;
        int red = Color.RED;
        int green = Color.GREEN;


        calendarView.addDecorator(new BookingDecorator(blue, classDaysH));
        calendarView.addDecorator(new BookingDecorator(red, missedDaysH));
        calendarView.addDecorator(new BookingDecorator(green, presDaysH));







    }

    private HashSet<CalendarDay> getCalendarDaysSet(Calendar cal1, Calendar cal2) {
        HashSet<CalendarDay> setDays = new HashSet<>();
        while (cal1.getTime().before(cal2.getTime())) {
            CalendarDay calDay = CalendarDay.from(cal1);
            setDays.add(calDay);
            cal1.add(Calendar.DATE, 1);
        }

        return setDays;
    }



    private class BookingDecorator implements DayViewDecorator {
        private int mColor;
        private HashSet<CalendarDay> mCalendarDayCollection;

        public BookingDecorator(int color, HashSet<CalendarDay> calendarDayCollection) {
            mColor = color;
            mCalendarDayCollection = calendarDayCollection;
        }

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            return mCalendarDayCollection.contains(day);
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.addSpan(new ForegroundColorSpan(mColor));
            //view.addSpan(new BackgroundColorSpan(Color.BLUE));


        }
    }

    private HashSet<CalendarDay> stringToCalDay(String sDays){

        if(sDays == null || sDays.compareTo("") == 0){
            sDays = "1$1$2017-";
        }
        String [] days = sDays.split("-");
        HashSet<CalendarDay> setDays = new HashSet<>();

        for(String day:days){

            if(day == null || day.compareTo("") == 0){
                day = "1$1$2017";
            }

            String [] dayTemp = day.split("\\$");
            CalendarDay calDay = CalendarDay.from(Integer.parseInt(dayTemp[2]),Integer.parseInt(dayTemp[1])-1,Integer.parseInt(dayTemp[0]));

            setDays.add(calDay);




        }

        return setDays;





    }


    public static String missedDays(String classDays){


        DateTime now = new DateTime();
        String missedDays = "";
        String [] days = classDays.split("-");

        for(String day:days){

            String [] dayTemp = day.split("\\$");

            DateTime tempDate = new DateTime(Integer.parseInt(dayTemp[2]),Integer.parseInt(dayTemp[1]),Integer.parseInt(dayTemp[0]),0,0);

            int numOfDays = Days.daysBetween(tempDate, now).getDays();

            if(numOfDays>0){


                missedDays = missedDays + day +"-";



            }




        }

        return missedDays;






    }


}