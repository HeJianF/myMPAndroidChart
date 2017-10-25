package com.e.dynamicaldataadding;

/**
 * Author: He Jianfeng
 * Data: 2017/10/20
 */

public class ReocrdBean {
    private float yValues;
    private String dateValues;
    private boolean flag = false;//标志位，当整点时为true

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public float getyValues() {
        return yValues;
    }

    public void setyValues(float yValues) {
        this.yValues = yValues;
    }

    public String getDateValues() {
        return dateValues;
    }

    public void setDateValues(String dateValues) {
        this.dateValues = dateValues;
    }
}
