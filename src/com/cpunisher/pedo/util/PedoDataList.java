package com.cpunisher.pedo.util;

import com.cpunisher.pedo.pojo.PedoData;
import com.cpunisher.pedo.pojo.PedoDate;
import com.google.gson.Gson;

import java.util.LinkedList;
import java.util.List;

public class PedoDataList implements Mergeable<PedoDataList> {

    private List<PedoData> dataList;

    public PedoDataList() {
        dataList = new LinkedList<>();
    }

    public PedoDataList(List<PedoData> dataList) {
        this.dataList = dataList;
    }

    public List<PedoData> getDataList() {
        return dataList;
    }

    public void setDataList(List<PedoData> dataList) {
        this.dataList = dataList;
    }

    public void add(PedoData pedoData) {
        dataList.add(pedoData);
    }

    public void addCopy(PedoData pedoData, PedoDate pedoDate) {
        PedoData copy = pedoData.copy();
        copy.setSteps(0);
        copy.setDate(pedoDate);
        this.add(copy);
    }

    public void mergeAllData(PedoDataList pedoDataList) {
        for (PedoData data : pedoDataList.dataList) {
            mergeData(data);
        }
    }

    public void mergeData(PedoData pedoData) {
        for (PedoData data : dataList) {
            data.merge(pedoData);
        }
    }

    public PedoDataList merge(PedoDataList pedoDataList) {
        this.dataList.addAll(pedoDataList.dataList);
        return this;
    }

    public boolean hasDataOfNumber(long number) {
        for (PedoData data : dataList) {
            if (data.getStuNum() == number)
                return true;
        }
        return false;
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(dataList.toArray());
    }

    public int getSize() {
        return dataList.size();
    }
}
