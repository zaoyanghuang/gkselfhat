package com.gnw.delaytask;

import com.alibaba.fastjson.JSON;

public class TaskBase {
    private String identifier;//任务id方便索引
    private String hexSocketData;
    public TaskBase(String identifier){this.identifier=identifier;}
    public TaskBase(String identifier, String hexSocketData) {
        this.identifier = identifier;
        this.hexSocketData = hexSocketData;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getHexSocketData() {
        return hexSocketData;
    }

    public void setHexSocketData(String hexSocketData) {
        this.hexSocketData = hexSocketData;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
