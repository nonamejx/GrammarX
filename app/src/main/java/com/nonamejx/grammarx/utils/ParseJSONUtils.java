package com.nonamejx.grammarx.utils;

import android.content.Context;
import android.support.annotation.StringRes;
import android.util.Log;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nonamejx.grammarx.models.Level;

import java.io.IOException;
import java.io.InputStream;

import io.realm.RealmObject;

/**
 * Created by noname on 29/07/2016.
 */

/*
* This class contains general functions for parsing data from JSON files
* */
public class ParseJSONUtils {

    private static final String TAG = "ParseJSONUtils";

    private Context mContext;
    private static ParseJSONUtils mInstance;

    private ParseJSONUtils(Context context) {
        this.mContext = context;
    }

    public static ParseJSONUtils getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new ParseJSONUtils(context);
        }
        return mInstance;
    }

    /*
    * <h1>parseTopics</h1> Parse topics from JSON files
    *
    * @param fileName - This file must be in the assets folder, fileName-it's just the name of the file, not the full path
    *
    * */
    public Level parseLevel(@StringRes String fileName) {

        Gson gson = new GsonBuilder().setExclusionStrategies(new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes f) {
                /*
                * You must remember getDeclaringClass() in order to avoid bullshit parsing
                * */
                return f.getDeclaringClass().equals(RealmObject.class);
            }

            @Override
            public boolean shouldSkipClass(Class<?> clazz) {
                return false;
            }
        }).create();

        Level level = gson.fromJson(loadJSONFromAssets(fileName), Level.class);

        return level;
    }

    /*
    * <h1>loadJSONFromAssets</h1> Load JSON content from file in the assets folder
    *
    * @param fileName - This file must be in the assets folder, it's just the name of the file, not the full path
    *
    * */
    public String loadJSONFromAssets(@StringRes String fileName) {
        String json = null;
        InputStream inputStream = null;

        try {
            inputStream = mContext.getAssets().open(fileName);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            json = new String(buffer, "UTF-8");

        } catch (IOException e) {
            Log.e(TAG, "Can not open file '" + fileName + "'");
            e.printStackTrace();
            return null;
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return json;
    }

}
