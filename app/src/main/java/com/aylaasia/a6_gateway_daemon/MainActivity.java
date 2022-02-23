package com.aylaasia.a6_gateway_daemon;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

public class MainActivity extends Activity {
    final String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        String sourceConfigFile = "/data/data/com.aylaasia.a6_gateway/files/devd.conf.startup";
        String targetConfigFile = "/storage/emulated/0/devd.conf.startup";

        if (checkAgentStartupFile(sourceConfigFile)) {
            if (BuildConfig.DEBUG)
                Log.d(TAG, "copy devd.conf.startup file to sdcard dir!");

            copyFile(sourceConfigFile, targetConfigFile);
        } else {
            if (BuildConfig.DEBUG)
                Log.d(TAG, "the devd.conf.startup file does not exist!");
        }
    }

    public boolean fileIsExists(String strFile) {
        try {
            File f = new File(strFile);
            if (!f.exists()) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    public boolean checkAgentStartupFile(String strFile) {
        if (fileIsExists(strFile)) {
            return true;
        } else {
            return false;
        }
    }

    public void copyFile(String oldPath, String newPath) {
        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) { //文件存在时
                InputStream inStream = new FileInputStream(oldPath); //读入原文件
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1444];
                int length;
                while ((byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread; //字节数 文件大小
                    System.out.println(bytesum);
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
            }
        } catch (Exception e) {
            System.out.println("复制单个文件操作出错");
            e.printStackTrace();

        }

    }

    /**
     * 删除单个文件
     *
     * @param filePath 要删除的文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public boolean deleteSingleFile(String filePath) {
        File file = new File(filePath);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                Log.d(TAG, "删除单个文件" + filePath + "成功！");
                return true;
            } else {
                if (BuildConfig.DEBUG)
                    Log.d(TAG, "删除单个文件" + filePath + "失败！");

                return false;
            }
        } else {
            if (BuildConfig.DEBUG)
                Log.d(TAG, "删除单个文件失败：" + filePath + "不存在！");

            return false;
        }
    }

    @Override
    protected void onDestroy() {
        if (BuildConfig.DEBUG)
            Log.d(TAG, "onDestroy");

        super.onDestroy();
    }

    @Override
    protected void onStop() {
        if (BuildConfig.DEBUG)
            Log.d(TAG, "onStop");

        super.onStop();
    }

    @Override
    protected void onResume() {
        if (BuildConfig.DEBUG)
            Log.d(TAG, "onResume");

        finish();
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}