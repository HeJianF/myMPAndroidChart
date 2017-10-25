package com.e.dynamicaldataadding.Service;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.e.dynamicaldataadding.CreateDataTest.CreateData;
import com.e.dynamicaldataadding.DataBean.EnteryBean;

import java.util.ArrayList;
import java.util.List;

public class IntentServiceTest extends IntentService {

    public static List<EnteryBean> list = new ArrayList<>();

    private String TAG = "检查问题";

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */

    //必须要有这个无参构造函数
    public IntentServiceTest(){
        super("IntentServiceTest");
    }
    public IntentServiceTest(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate: 执行");
        super.onCreate();
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        list = CreateData.setData();
        Log.d(TAG, "onStartCommand: 执行");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
