package com.example.vgxchange.tool;

import android.util.Log;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.vgxchange.Constants;

import java.util.Calendar;
import java.util.Date;

public class DateTool {

    public static String formatStringDateDDMMYY(String dateStringToFormat) {
        String formattedStringDate = null;
        try {
            Date date = null;
            date = Constants.dateIn.parse((dateStringToFormat));
            formattedStringDate = Constants.dateOut.format(date);

        } catch (Exception e) {
            Log.e("DATE PARSE", e.getMessage());
        }
        return formattedStringDate;
    }

    public static Date parseStringToDate(String dateStringToFormat) {
        Date date = null;
        try {
            date = Constants.dateIn.parse((dateStringToFormat));
        } catch (Exception e) {
            Log.e("DATE PARSE", e.getMessage());
        }
        return date;
    }

    public static java.util.Date getDateFromDatePicker(DatePicker datePicker){
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year =  datePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        return calendar.getTime();
    }


}
