package com.mllweb.thing.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Android on 2016/5/18.
 */
public class Utils {
    public static void scoreApp(Context context) {
        Uri uri = Uri.parse("market://details?id=" + context.getPackageName());
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static String caleDate(long dateParams) {
        String returnDate = "";
        //判断是否是当天
        Date today = new Date();
        today.setHours(0);
        today.setMinutes(0);
        today.setSeconds(0);
        long todayTime = today.getTime();
        if (todayTime <= dateParams) {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            return sdf.format(new Date(dateParams));
        }
        //判断是否是昨天
        long cale = todayTime - dateParams;
        long dayTime = 1000 * 60 * 60 * 24;
        if (cale < dayTime) {
            return "昨天";
        } else if (cale < dayTime * 2) {
            return "前天";
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
            return sdf.format(new Date(dateParams));
        }
    }

    public static String caleDate(Date date) {
        return caleDate(date.getTime());
    }

    public static void toast(Activity activity, String msg) {
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
    }

    public static void log(String msg) {
        Log.i("mllweb-info", msg);
    }

    public static String getFileName() {
        return System.nanoTime() + "" + (Math.random() + "").substring(2, 8);
    }

    public static String md5(String value) {
        byte[] hash;

        try {
            hash = MessageDigest.getInstance("MD5").digest(value.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }

        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10)
                hex.append("0");
            hex.append(Integer.toHexString(b & 0xFF));
        }

        return hex.toString();
    }
}
