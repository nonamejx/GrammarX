package com.nonamejx.grammarx.acitivities;

import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import com.nonamejx.grammarx.R;
import com.nonamejx.grammarx.database.RealmHelper;
import com.nonamejx.grammarx.models.Level;
import com.nonamejx.grammarx.utils.ParseJSONUtils;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

/**
 * Created by noname on 11/08/2016.
 */
@EActivity(R.layout.activity_splash)
public class SplashActivity extends BaseActivity {
    private static final int DELAY_DURATION = 1000; // 1s

    @ViewById(R.id.tvAppName)
    TextView mTvAppName;
    @ViewById(R.id.tvLoadDataStatus)
    TextView mTvLoadDataStatus;

    @Override
    protected Fragment createFragment() {
        return null;
    }

    @Override
    public void afterView() {
        if (RealmHelper.getInstance(SplashActivity.this).getLevels().size() == 0) {
            mTvLoadDataStatus.setVisibility(View.VISIBLE);
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Init data at the first run
                if (RealmHelper.getInstance(SplashActivity.this).getLevels().size() == 0) {
                    initDataAtFirstRun();
                }
                // Start MainActivity
                MainActivity_.intent(SplashActivity.this).start();
                finish();
            }
        }, DELAY_DURATION);
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
