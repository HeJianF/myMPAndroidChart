package com.e.dynamicaldataadding.DataBean;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * Author: He Jianfeng
 * Data: 2017/10/19
 */

public class EnteryBean extends DataSupport implements Serializable {

    private float xValue;

    private float yValue;//

    private float yValue1;//通道1的数据

    private float yValue2;//通道2的数据

    private float yValue3;

    private float yValue4;

    private String date;

    public EnteryBean(float xValue, float yValue, String date) {
        this.xValue = xValue;
        this.yValue = yValue;
        this.date = date;
    }

    public EnteryBean(float yValue1, float yValue2, float yValue3, float yValue4, String date) {
        this.yValue1 = yValue1;
        this.yValue2 = yValue2;
        this.yValue3 = yValue3;
        this.yValue4 = yValue4;
        this.date = date;
    }

    public float getyValue1() {
        return yValue1;
    }

    public void setyValue1(float yValue1) {
        this.yValue1 = yValue1;
    }

    public float getyValue2() {
        return yValue2;
    }

    public void setyValue2(float yValue2) {
        this.yValue2 = yValue2;
    }

    public float getyValue3() {
        return yValue3;
    }

    public void setyValue3(float yValue3) {
        this.yValue3 = yValue3;
    }

    public float getyValue4() {
        return yValue4;
    }

    public void setyValue4(float yValue4) {
        this.yValue4 = yValue4;
    }


    public float getxValue() {
        return xValue;
    }

    public void setxValue(float xValue) {
        this.xValue = xValue;
    }

    public float getyValue() {
        return yValue;
    }

    public void setyValue(float yValue) {
        this.yValue = yValue;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
