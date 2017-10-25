package com.e.dynamicaldataadding;

import android.graphics.Color;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.e.dynamicaldataadding.DataBean.EnteryBean;

/**
 * 实时曲线
 */
public class MainActivity extends AppCompatActivity implements OnChartValueSelectedListener {

    private String TAG = "查询数据";

    private float xValueBang;

    List<EnteryBean> enteryBeen;

    private LineChart mChart;

    File file;

    Timer timer = new Timer(true);

    final String fileName = "record";

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                addEntery();
                mChart.invalidate();
            }
            super.handleMessage(msg);
        }
    };

    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            Message meg = new Message();
            meg.what = 1;
            handler.sendMessage(meg);
        }
    };

    public float getxValueBang() {
        return xValueBang;
    }

    public void setxValueBang(float xValueBang) {
        this.xValueBang = xValueBang;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        mChart = (LineChart) findViewById(R.id.chart1);
       /* mChart.setDrawGridBackground(false);
        mChart.setOnChartValueSelectedListener(this);
        mChart.getDescription().setEnabled(false);*/
        mChart.setData(new LineData());
        createLine();
        //mChart.invalidate();
        //创建数据库
        Connector.getDatabase();
        enteryBeen = new ArrayList<>();
        //添加数据
        //addEntery();
        timer.schedule(task, 0, 1000);
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_line_chart, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actionAddEntry:
                addEntery();
                timer.schedule(task, 0, 1000);
                break;
            case R.id.actionRemoveEntry:
                List<EnteryBean> list = DataSupport.findAll(EnteryBean.class);
                for (EnteryBean enteryBean : list) {
                    Log.d(TAG, "onOptionsItemSelected: " + enteryBean.getxValue());
                    Log.d(TAG, "onOptionsItemSelected: " + enteryBean.getyValue());
                    Log.d(TAG, "onOptionsItemSelected: " + enteryBean.getDate());
                }
                break;
        }
        return true;
    }

    private void createLine() {
        //不能触摸和放大缩小
        mChart.setTouchEnabled(false);
        mChart.setScaleEnabled(false);
        mChart.getDescription().setEnabled(false);
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
                "", "", "", "", "", "", "", "十六","", "一", "", "", "", "", "六", "", "",
                "", "", "", "", "", "", "", "十六"};
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
       // xAxis.setLabelCount(30);//设置x轴可以显示的数量
        //xAxis.setValueFormatter(axisValueFormatter);
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
    }

    private void addEntery() {

        LineData data = mChart.getLineData();
        ILineDataSet set = data.getDataSetByIndex(0);

        if (set == null) {
            set = createSet();
            data.addDataSet(set);
        }

        // choose a random dataSet
        int randomDataSetIndex = (int) (Math.random() * data.getDataSetCount());

        //随机生成xy轴的坐标，需要将数据记录下来
        float yValue = (float) (Math.random() + 20);
        float xValue = data.getDataSetByIndex(randomDataSetIndex).getEntryCount();

        data.addEntry(new Entry(xValue, yValue), randomDataSetIndex);
        data.notifyDataChanged();

        // let the chart know it's data has changed
        mChart.notifyDataSetChanged();

        mChart.setVisibleXRangeMaximum(20);
        //mChart.setVisibleYRangeMaximum(15, AxisDependency.LEFT);
//
//            // this automatically refreshes the chart (calls invalidate())
        mChart.moveViewTo(data.getEntryCount() - 7, 0f, YAxis.AxisDependency.LEFT);
        //时间，以对象的方式来存储数据
        //SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        String dateFormat = "HH:mm:ss";
        SimpleDateFormat format = new SimpleDateFormat(dateFormat);
        Date curDate = new Date(System.currentTimeMillis());
        EnteryBean entery = new EnteryBean(xValue, yValue, format.format(curDate));
        enteryBeen.add(entery);
        entery.save();
    }

    //创建一条折线
    private LineDataSet createSet() {

        LineDataSet set = new LineDataSet(null, "DataSet 1");
        set.setLineWidth(2.5f);
        set.setDrawCircles(false);//取消数据的小圆圈
        set.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);//设置为平滑的曲线显示
        set.setCubicIntensity(0.6f);
        set.setColor(Color.rgb(240, 99, 99));
        //set.setCircleColor(Color.rgb(240, 99, 99));
        set.setHighLightColor(Color.rgb(190, 190, 190));
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setValueTextSize(10f);

        return set;
    }

    @Override
    protected void onDestroy() {
        config();
        super.onDestroy();
    }

    private void config() {
        //先关掉定时器
        task.cancel();
        timer.cancel();
        Log.d(TAG, "onDestroy: 退出");
        List<EnteryBean> list = DataSupport.findAll(EnteryBean.class);

        file = new File(Environment.getExternalStorageDirectory().getPath(), fileName);

        if (file.exists()) {
            Log.d(TAG, "config: 文件存在");
            //存储数据
            // writeObjectToFile(list);
            writeRecords(list, file);
            DataSupport.deleteAll(EnteryBean.class);
        } else {
            Log.d(TAG, "config: 文件不存在");
            try {
                file.createNewFile();
                Log.d(TAG, "config: 创建成功");
            } catch (IOException e) {
                e.printStackTrace();
                Log.d(TAG, "config: 创建失败");
            }
            //writeObjectToFile(list);
            writeRecords(list, file);
            DataSupport.deleteAll(EnteryBean.class);
        }
        list.clear();
    }

    //利用对象存储所有的数据
    private void writeObjectToFile(List<EnteryBean> list) {

        FileOutputStream out;
        try {
            out = new FileOutputStream(file, true);
            WriteObject object = WriteObject.newInstance(file, out, true);
            object.writeObject(list);
            object.flush();
            object.close();
            Log.d(TAG, "writeObjectToFile: 存储成功");
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "writeObjectToFile: 存储失败");
        }
    }

    //二进制存储y轴数据和日期数据
    private void writeRecords(List<EnteryBean> list, File file) {
        try {
            DataOutputStream out = new DataOutputStream(new FileOutputStream(file, true));
            for (int i = 0; i < list.size(); i++) {
                out.writeFloat(list.get(i).getyValue());
                out.writeUTF(list.get(i).getDate());
                Log.d(TAG, "writeRecords: 存储成功");
            }
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG, "writeRecords: 存储失败");
        }
    }
}
