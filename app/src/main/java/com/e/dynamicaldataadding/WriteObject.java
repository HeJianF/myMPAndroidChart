package com.e.dynamicaldataadding;

import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;
import java.io.OutputStream;

/**
 * Author: He Jianfeng
 * Data: 2017/10/19
 */

public class WriteObject extends ObjectOutputStream {

    private static File f;
    private static boolean mflag;

    public WriteObject(OutputStream out, File f) throws IOException {
        super(out);
    }


    @Override
    protected void writeStreamHeader() throws IOException {
        if (mflag) {
            super.writeStreamHeader();
        }
        /*if (f.exists() && f.length() == 0) {
            Log.d("查询数据", "writeStreamHeader: 不存在1");
            super.writeStreamHeader();
        } else {
            Log.d("查询数据", "writeStreamHeader: 存在，没执行1");
        }*/
    }

    @Override
    protected void writeClassDescriptor(ObjectStreamClass desc) throws IOException {
        if (mflag) {
            super.writeClassDescriptor(desc);
        }
       /* if  (f.exists() && f.length() == 0) {
            Log.d("查询数据", "writeStreamHeader: 不存在2");
            super.writeClassDescriptor(desc);
        } else {
            Log.d("查询数据", "writeStreamHeader: 存在，没执行2");
        }*/
    }

    public static WriteObject newInstance(File file, OutputStream out, boolean flag) throws IOException {
        f = file;
        mflag = flag;
        return new WriteObject(out, f);
    }
}
