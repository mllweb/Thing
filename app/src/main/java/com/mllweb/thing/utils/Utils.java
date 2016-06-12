package com.mllweb.thing.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

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
    public static DisplayImageOptions getListOptions() {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                // 设置图片在下载期间显示的图片
//                .showImageOnLoading(R.drawable.ic_stub)
                // 设置图片Uri为空或是错误的时候显示的图片
//                .showImageForEmptyUri(R.drawable.ic_stub)
                // 设置图片加载/解码过程中错误时候显示的图片
//                .showImageOnFail(R.drawable.ic_error)
                // 设置下载的图片是否缓存在内存中
                .cacheInMemory(true)
                .cacheOnDisk(true)
                // 设置下载的图片是否缓存在SD卡中
                .cacheOnDisc(true)
                // 保留Exif信息
                .considerExifParams(true)
                // 设置图片以如何的编码方式显示
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                // 设置图片的解码类型
                .bitmapConfig(Bitmap.Config.RGB_565)
                // .decodingOptions(android.graphics.BitmapFactory.Options
                // decodingOptions)//设置图片的解码配置
                .considerExifParams(true)
                // 设置图片下载前的延迟
                .delayBeforeLoading(100)// int
                // delayInMillis为你设置的延迟时间
                // 设置图片加入缓存前，对bitmap进行设置
                // .preProcessor(BitmapProcessor preProcessor)
                .resetViewBeforeLoading(true)// 设置图片在下载前是否重置，复位
                // .displayer(new RoundedBitmapDisplayer(20))//是否设置为圆角，弧度为多少
                .displayer(new FadeInBitmapDisplayer(100))// 淡入
                .build();
        return options;
    }
}
