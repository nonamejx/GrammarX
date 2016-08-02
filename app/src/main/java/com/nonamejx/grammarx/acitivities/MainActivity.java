package com.nonamejx.grammarx.acitivities;

import android.support.v4.app.Fragment;

import com.nonamejx.grammarx.R;
import com.nonamejx.grammarx.fragments.LevelFragment;

import org.androidannotations.annotations.EActivity;

@EActivity(R.layout.activity_container)
public class MainActivity extends BaseActivity {

    @Override
    public void afterView() {
    }

    @Override
    protected Fragment createFragment() {
        return LevelFragment.newInstance();
    }
}
