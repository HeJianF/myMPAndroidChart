package com.e.dynamicaldataadding;

import android.content.Context;
import android.drm.DrmStore;
import android.graphics.Color;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;

/**
 * Author: He Jianfeng
 * Data: 2017/10/19
 */

public class LinerChartManger {

    private static String lineName = null;
    private static String lineName1 = null;

    public static void initDoubleLineChart(Context context, LineChart mLineChart,
                                           ArrayList<Entry> yValue) {

        initDataStyle(context, mLineChart);

        LineDataSet dataSet = new LineDataSet(yValue, lineName);
        dataSet.setColor(Color.RED);
        dataSet.setDrawCircles(false);//不绘制坐标轴的交点
        dataSet.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);//平滑的贝塞尔曲线
        //dataSet.setCircleColor(Color.RED);
        //dataSet.setDrawValues(true);

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(dataSet);


        LineData lineData = new LineData(dataSets);
        mLineChart.setData(lineData);

        //设置动画效果
        /*mLineChart.animateX(2000, Easing.EasingOption.Linear);
        mLineChart.animateY(2000, Easing.EasingOption.Linear);*/
        mLineChart.invalidate();
    }

    //绘制折线图的坐标轴
    private static void initDataStyle(Context context, LineChart mLineChart) {
        mLineChart.setTouchEnabled(false);
        mLineChart.setScaleEnabled(false);
        mLineChart.getDescription().setEnabled(false);

        /*Legend title = mLineChart.getLegend();
        title.setForm(Legend.LegendForm.LINE);*/

        /*XAxis xAxis = mLineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        *//*xAxis.setAxisLineColor(Color.parseColor("#66CDAA"));
        xAxis.setAxisLineWidth(5);*//*
        xAxis.setDrawAxisLine(true);
        xAxis.setAxisMaximum(3600);
        xAxis.setDrawGridLines(false);

        YAxis yAxisLeft = mLineChart.getAxisLeft();*//*
        yAxisLeft.setAxisLineColor(Color.parseColor("#66CDAA"));
        yAxisLeft.setAxisLineWidth(5);*//*
        yAxisLeft.setDrawGridLines(false);
        yAxisLeft.setAxisMaximum(100);//将y轴固定到100，不会随着y轴数据的变化而变化
        yAxisLeft.setAxisMinimum(0);

        //不显示右边坐标轴
        YAxis yAxisRight = mLineChart.getAxisRight();
        yAxisRight.setEnabled(false);*/

        //最高值
        LimitLine ll1 = new LimitLine(80f, "Upper Limit");
        ll1.setLineWidth(4f);
        ll1.enableDashedLine(10f, 10f, 0f);
        ll1.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        ll1.setTextSize(10f);
        //最低值
        LimitLine ll2 = new LimitLine(-5f, "Lower Limit");
        ll2.setLineWidth(4f);
        ll2.enableDashedLine(10f, 10f, 0f);
        ll2.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        ll2.setTextSize(10f);
        //为x轴自定义数据
        final String b[] = {"", "一", "", "", "", "", "六", "", "",
                "", "", "", "", "", "", "", "十六"};
        IAxisValueFormatter axisValueFormatter = new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return b[(int) value];
            }
        };
        //x轴
        XAxis xAxis = mLineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f); // only intervals of 1 day
        //xAxis.setLabelCount(16);//设置x轴可以显示的数量
        xAxis.setAxisMaximum(3600);
       // xAxis.setValueFormatter(axisValueFormatter);
        xAxis.setValueFormatter(null);
        //IAxisValueFormatter custom = new MyAxisValueFormatter();
        YAxis leftAxis = mLineChart.getAxisLeft();
        leftAxis.setLabelCount(8, false);
        leftAxis.setValueFormatter(null);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        leftAxis.setAxisMinimum(-10f); // this replaces setStartAtZero(true)
        leftAxis.setAxisMaximum(100f);
        leftAxis.removeAllLimitLines();
        leftAxis.addLimitLine(ll1);
        leftAxis.addLimitLine(ll2);
        //mLineChart.getAxisLeft().setEnabled(false);  //不显示左边y轴
        YAxis rightAxis = mLineChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setLabelCount(8, false);
        rightAxis.setValueFormatter(null);
        rightAxis.setSpaceTop(15f);
        rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
        rightAxis.setAxisMaximum(100f);
    }

    static void setLineName(String name) {
        lineName = name;
    }
}
