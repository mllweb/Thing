package com.mllweb.thing.manager;

import android.app.Activity;

import com.google.gson.Gson;
import com.mllweb.cache.ACache;
import com.mllweb.model.Thing;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android on 2016/6/14.
 */
public class ACacheManager {
    private static final String key = "thing";
    public static ACacheManager instance;
    private ACache aCache;

    public ACacheManager(Activity a) {
        aCache = ACache.get(a);
    }

    public static synchronized ACacheManager getInstatnce(Activity a) {
        if (instance == null) {
            instance = new ACacheManager(a);
        }
        return instance;
    }

    public void insertThing(Thing thing) {
        try {
            JSONObject jsonObject = new JSONObject(new Gson().toJson(thing));
            insertThing(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void insertThing(JSONObject object) {
        String value = aCache.getAsString(key);
        if (value == null || value.equals("")) {
            aCache.put(key, String.format("[%s]", object.toString()));
        } else {
            value = value.substring(1);
            aCache.put(key, String.format("[%s,%s", object.toString(), value));
        }
    }

    public void insertThing(List<Thing> list) {
        if (list != null && list.size() > 0) {
            JSONArray array = new JSONArray(list);
            String value = aCache.getAsString(key);
            if (value == null || value.equals("")) {
                aCache.put(key, array.toString());
            } else {
                value = value.substring(1);
                String arrayStr = array.toString();
                aCache.put(key, String.format("%s,%s", arrayStr.substring(0, arrayStr.length() - 1), value));
            }
        }
    }

    public List<Thing> selectThing() {
        List<Thing> list = new ArrayList<>();
        String value = aCache.getAsString(key);
        if (value != null && (!value.equals(""))) {
            try {
                JSONArray jsonArray = new JSONArray(value);
                Gson gson = new Gson();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject object = jsonArray.optJSONObject(i);
                    list.add(gson.fromJson(object.toString(), Thing.class));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return list;
    }
}
