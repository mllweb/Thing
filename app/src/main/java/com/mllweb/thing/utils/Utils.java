package com.mllweb.thing.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

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
}
