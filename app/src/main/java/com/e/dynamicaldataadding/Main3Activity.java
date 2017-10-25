package com.e.dynamicaldataadding;

import android.content.IntentFilter;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 历史曲线
 */
public class Main3Activity extends AppCompatActivity {

    private String TAG = "查询数据";

    LineChart lineChart;

    File file;

    List<ReocrdBean> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        lineChart = (LineChart) findViewById(R.id.line_chart);
        IntentFilter filter = new IntentFilter();
        list = new ArrayList<>();
        file = new File(Environment.getExternalStorageDirectory().getPath(), "record");
        //从文件中读取对象数据
        //readObjectFromFile(file);
        readRecords(file);
        Log.d(TAG, "onCreate: " + list.size());

        ArrayList<Entry> yValue = new ArrayList<>();
        //可以在这里加入标识，让曲线可以显示出标志时间（历史记录的开头，结尾，整点）
        for (int i = 0; i < list.size(); i++) {

            yValue.add(new Entry(i, list.get(i).getyValues()));
            //yValue.add(new Entry(i,list.get(i).getyValues(),"整点"));
        }

        LinerChartManger.setLineName("通道的历史曲线记录");

        LinerChartManger.initDoubleLineChart(this, lineChart, yValue);
    }

    //读取存储的对象文件
    private void readObjectFromFile(File file) {
        Object temp;
        FileInputStream in;
        if (file.exists()) {
            try {
                in = new FileInputStream(file);
                ObjectInputStream objIn = new ObjectInputStream(in);
                //读取对象数据
                list = (List<ReocrdBean>) objIn.readObject();
                objIn.close();
                Log.d(TAG, "readObjectFromFile: 读取成功");
            } catch (Exception e) {
                e.printStackTrace();
                Log.d(TAG, "readObjectFromFile: 读取失败");
            }
        } else {
            Toast.makeText(this, "文件不存在", Toast.LENGTH_SHORT).show();
        }
    }

    private void readRecords(File file){
        try {
            Log.d(TAG, "readRecords: 文件是否存在 " + file.exists());
            DataInputStream in = new DataInputStream(new FileInputStream(file));
            try{
                while (true){
                    ReocrdBean record = new ReocrdBean();
                    float yValue = in.readFloat();
                    String dateValues = in.readUTF();
                    //这个判断用来显示整点，标识
                    //if(dateValues.equals("00:00:00")||dateValues.equals("01:00:00"))
                    record.setyValues(yValue);
                    record.setDateValues(dateValues);
                    list.add(record);
                }
            }catch (EOFException e){
            }finally {
                in.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
