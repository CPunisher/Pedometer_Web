package com.cpunisher.pedo.pojo;

import com.cpunisher.pedo.util.inter.Copiable;
import com.cpunisher.pedo.util.inter.Mergeable;

public class PedoData implements Mergeable<PedoData>, Copiable<PedoData> {

    private long stuNum;
    private String name;
    private int steps;
    private PedoDate date;

    public PedoData() {}

    public PedoData(long stuNum, String name, int steps) {
        this.stuNum = stuNum;
        this.name = name;
        this.steps = steps;
    }

    public PedoData(long stuNum, String name, int steps, PedoDate date) {
        this.stuNum = stuNum;
        this.name = name;
        this.steps = steps;
        this.date = date;
    }

    public PedoData copy() {
        return new PedoData(this.stuNum, this.name, this.steps, this.date.copy());
    }

    public PedoData merge(PedoData pedoData) {
        if (this.weakEquals(pedoData)) {
            this.steps += pedoData.steps;
            this.date.merge(pedoData.date);
        }
        return this;
    }

    @Override
    public boolean equals(Object object) {
        PedoData pedoData = (PedoData) object;
        return weakEquals(object) && this.date.equals(pedoData.date);
    }

    public boolean weakEquals(Object object) {
        if (object == null || !(object instanceof PedoData))
            return false;
        PedoData pedoData = (PedoData) object;
        return this.stuNum == pedoData.stuNum && this.name.equals(pedoData.name);
    }

    @Override
    public String toString() {
        return "PedoData{" +
                "stuNum=" + stuNum +
                ", name='" + name + '\'' +
                ", steps=" + steps +
                ", date=" + date +
                '}';
    }

    public PedoDate getDate() {
        return date;
    }

    public void setDate(PedoDate date) {
        this.date = date;
    }

    public long getStuNum() {
        return stuNum;
    }

    public void setStuNum(long stuNum) {
        this.stuNum = stuNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }
}
