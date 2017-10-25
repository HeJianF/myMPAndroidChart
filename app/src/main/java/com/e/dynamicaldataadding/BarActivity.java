package com.e.dynamicaldataadding;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.e.dynamicaldataadding.CreateDataTest.CreateData;
import com.e.dynamicaldataadding.DataBean.EnteryBean;
import com.e.dynamicaldataadding.Service.IntentServiceTest;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 柱状图
 */
public class BarActivity extends AppCompatActivity {

    BarChart mChart;

    List<EnteryBean> listData;

    Timer timer = new Timer(true);

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==1){
                Log.d("检查", "handleMessage: 启动了3");
                setData(15,100);
                mChart.invalidate();
            }
            super.handleMessage(msg);
        }
    };

    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            Log.d("检查", "run: 启动了2");
            Message message = new Message();
            message.what = 1;
            handler.sendMessage(message);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar);

        Intent intent = new Intent(this,IntentServiceTest.class);
        startService(intent);

        mChart = (BarChart) findViewById(R.id.barChart);
        //不能触摸和放大缩小
        mChart.setTouchEnabled(false);
        mChart.setScaleEnabled(false);

        mChart.setDrawValueAboveBar(true);
        mChart.setDrawBarShadow(false);

        mChart.getDescription().setEnabled(false);

        mChart.setMaxVisibleValueCount(60);

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

        final String b[]={"","一","","","","","六","","",
                "","","","","","","","十六"};

        IAxisValueFormatter axisValueFormatter = new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return b[(int) value];
            }
        };

        //x轴
        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f); // only intervals of 1 day
        xAxis.setLabelCount(16);//设置x轴可以显示的数量
        xAxis.setValueFormatter(axisValueFormatter);
        //xAxis.setValueFormatter(null);
        //IAxisValueFormatter custom = new MyAxisValueFormatter();

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setLabelCount(8, false);
        leftAxis.setValueFormatter(null);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        leftAxis.setAxisMinimum(-10f); // this replaces setStartAtZero(true)
        leftAxis.setAxisMaximum(100f);
        leftAxis.removeAllLimitLines();
        leftAxis.addLimitLine(ll1);
        leftAxis.addLimitLine(ll2);

        //mChart.getAxisLeft().setEnabled(false);  //不显示左边y轴
        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setLabelCount(8, false);
        rightAxis.setValueFormatter(null);
        rightAxis.setSpaceTop(15f);
        rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
        rightAxis.setAxisMaximum(100f);

        Legend l = mChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setForm(Legend.LegendForm.SQUARE);
        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(4f);

       // setData(15, 100);
        Log.d("检查", "onCreate: 启动了1");

        Log.d("检查", "onCreate: 启动Intent");
        timer.schedule(task,0,1000);
    }

    private void setData(int count, float range) {
        float start = 1f;
        ArrayList<BarEntry> yVals1 = new ArrayList<>();
        //设置随机值
        for (int i = 1; i < count + 2; i++) {
            //float mult = (range + 1);
            float mult = 50;
            float val = (float) (Math.random()*20 + mult);
            //float val = (float) (Math.random() * mult);

            if (Math.random() * 100 < 25) {
                yVals1.add(new BarEntry(i, val, getResources().getDrawable(R.drawable.star)));
            } else {
                yVals1.add(new BarEntry(i, val));
            }

        }
       /* listData = CreateData.setData();
        yVals1.add(new BarEntry(1,listData.get(0).getyValue1()));
        yVals1.add(new BarEntry(2,listData.get(0).getyValue2()));
        yVals1.add(new BarEntry(3,listData.get(0).getyValue3()));
        yVals1.add(new BarEntry(4,listData.get(0).getyValue4()));*/
        BarDataSet set1;

        if (mChart.getData() != null && mChart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) mChart.getData().getDataSetByIndex(0);
            set1.setValues(yVals1);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();

        } else {
            set1 = new BarDataSet(yVals1, "通道");

            set1.setDrawIcons(false);

            set1.setColors(ColorTemplate.MATERIAL_COLORS);

            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);

            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);
            data.setBarWidth(0.9f);

            mChart.setData(data);
        }
    }
}
