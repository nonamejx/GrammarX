package com.nonamejx.grammarx;

import android.app.Application;

import com.nonamejx.grammarx.database.RealmHelper;
import com.nonamejx.grammarx.models.Level;
import com.nonamejx.grammarx.utils.ParseJSONUtils;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by noname on 25/07/2016.
 */
public class GrammarXApp extends Application {
    private static final String TAG = "GrammarXApp";

    @Override
    public void onCreate() {
        super.onCreate();

        // Configure Realm for the application
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this).build();
        // Realm.deleteRealm(realmConfiguration); // Clean the old realm
        Realm.setDefaultConfiguration(realmConfiguration);

        // Init data at the first run
        if (RealmHelper.getInstance(this).getLevels().size() > 0) {
        } else {
            initDataAtFirstRun();
        }

    }

    /*
    * <h1>initDataAtFirstTime<h1>
    *
    * Read data from JSON files in assets folder and copy to realm
    *
    * */
    private void initDataAtFirstRun() {
        Realm realm = RealmHelper.getInstance(this).getRealm();
        List<Level> levels = new ArrayList<>();

        // Read files from assets folder and parse to Level objects
        String[] dataFileNames = getResources().getStringArray(R.array.data_file_names);
        for (String fileName : dataFileNames) {
            Level level = ParseJSONUtils.getInstance(this).parseLevel(fileName);
            levels.add(level);
        }

        // Copy to Realm
        if (levels.size() > 0) {
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(levels);
            realm.commitTransaction();
        }
    }
}
