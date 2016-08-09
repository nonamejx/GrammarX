package com.nonamejx.grammarx.acitivities;

import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;

import com.nonamejx.grammarx.R;
import com.nonamejx.grammarx.fragments.LevelFragment;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_container)
public class MainActivity extends BaseActivity {

    @ViewById(R.id.toolbarMain)
    Toolbar toolbarMain;

    @Override
    public void afterView() {
        setSupportActionBar(toolbarMain);
    }

    @Override
    protected Fragment createFragment() {
        return LevelFragment.newInstance();
    }
}
