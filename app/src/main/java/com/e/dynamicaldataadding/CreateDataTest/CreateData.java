package com.e.dynamicaldataadding.CreateDataTest;

import android.util.Log;

import com.e.dynamicaldataadding.DataBean.DateBean;
import com.e.dynamicaldataadding.DataBean.EnteryBean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Author: He Jianfeng
 * Data: 2017/10/24
 */

public class CreateData {

    private static List<EnteryBean> listData;

    public  static List<EnteryBean> setData() {
        Log.d("检查", "setData: 产生数据");
        listData = new ArrayList<>();
        float a1 = (float) (Math.random() * 10 + 60);
        float a2 = (float) (Math.random() * 10 + 60);
        float a3 = (float) (Math.random() * 10 + 60);
        float a4 = (float) (Math.random() * 10 + 60);
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        String currentDate = format.format(date);
        EnteryBean enteryBean = new EnteryBean(a1,a2,a3,a4,currentDate);
        listData.add(enteryBean);
        return listData;
    }
}

