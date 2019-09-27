package com.cpunisher.pedo.util;

import com.cpunisher.pedo.pojo.PedoDate;

import java.util.Calendar;

public class TimeUtil {

    private static final int mDays[] = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    public int getMonthDays(int month) {
        return mDays[month];
    }

    public String createTableName(PedoDate pedoDate) {
        return "pedo_" + pedoDate.getYear() + "_" + pedoDate.getMonth() + "_" + pedoDate.getDay();
    }

    public PedoDate now() {
        int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        int year = Calendar.getInstance().get(Calendar.YEAR) % 100;
        return new PedoDate(year, month, day);
    }
}
