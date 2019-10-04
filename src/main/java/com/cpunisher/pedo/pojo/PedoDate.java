package com.cpunisher.pedo.pojo;

import com.cpunisher.pedo.util.inter.Copiable;
import com.cpunisher.pedo.util.inter.Mergeable;

public class PedoDate implements Mergeable<PedoDate>, Copiable<PedoDate> {

    private static final int NORMAL = 0;
    private static final int WEEKLY = 1;
    private static final int MONTHLY = 2;
    public static final PedoDate WEEKLY_DATE = new PedoDate(WEEKLY);
    public static final PedoDate MONTHLY_DATE = new PedoDate(MONTHLY);

    private int year;
    private int month;
    private int day;
    private int type;

    public PedoDate() {}

    public PedoDate(int type) {
        this.type = type;
    }

    public PedoDate(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public PedoDate merge(PedoDate pedoDate) {
        if (this.type == NORMAL && pedoDate.type == NORMAL) {
            if (this.day == pedoDate.day) {
                return this;
            }
            this.type = Math.abs(this.day - pedoDate.day) >= 7 ? MONTHLY : WEEKLY;
        } else {
            this.type = Math.max(this.type, pedoDate.type);
        }
        return this;
    }

    public PedoDate copy() {
        PedoDate pedoDate = new PedoDate(this.year, this.month, this.day);
        pedoDate.type = type;
        return pedoDate;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof PedoData))
            return false;
        PedoDate pedoDate = (PedoDate) obj;
        return this.type == NORMAL && pedoDate.type == NORMAL &&
                this.year == pedoDate.year && this.month == pedoDate.month && this.day == pedoDate.day;
    }

    @Override
    public String toString() {
        return "PedoDate{" +
                "year=" + year +
                ", month=" + month +
                ", day=" + day +
                ", type=" + type +
                '}';
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}