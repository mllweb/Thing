package com.mllweb.thing.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by Android on 2016/5/18.
 */
public class Utils {
    public static void score(Context context) {
        Uri uri = Uri.parse("market://details?id=" + context.getPackageName());
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
