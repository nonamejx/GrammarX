package com.nonamejx.grammarx;

import android.app.Application;

import com.nonamejx.grammarx.utils.TypefaceUtil;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EApplication;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by noname on 25/07/2016.
 */
@EApplication
public class GrammarXApp extends Application {
    private static final String TAG = GrammarXApp.class.getName();

    @AfterInject
    public void after(){
        // Custom font
        TypefaceUtil.overrideFont(this, getResources().getString(R.string.default_font_name), getResources().getString(R.string.custom_font_name));
        // Configure Realm for the application
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this).build();
        // Realm.deleteRealm(realmConfiguration); // Clean the old realm
        Realm.setDefaultConfiguration(realmConfiguration);
    }


}
