package com.debug.messagereceiver.dao;

public class Festival {

    public String name;
    public String month;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Festival(String name, String month) {
        this.name = name;
        this.month = month;
    }
}
