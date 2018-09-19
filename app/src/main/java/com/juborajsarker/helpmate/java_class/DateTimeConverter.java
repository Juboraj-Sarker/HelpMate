package com.juborajsarker.helpmate.java_class;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public  class DateTimeConverter {

    public String getCurrentDate(){

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c);

        return formattedDate;
    }

    public String getCurrentTime(){

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("h:mm a");
        String formattedDate = df.format(c);

        return formattedDate;

    }
}
