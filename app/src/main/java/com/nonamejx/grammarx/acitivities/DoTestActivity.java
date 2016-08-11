package com.nonamejx.grammarx.acitivities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;

import com.nonamejx.grammarx.R;
import com.nonamejx.grammarx.common.Constant;
import com.nonamejx.grammarx.database.RealmHelper;
import com.nonamejx.grammarx.fragments.TestDetailFragment;
import com.nonamejx.grammarx.models.Test;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * Created by noname on 09/08/2016.
 */
@EActivity(R.layout.activity_container)
public class DoTestActivity extends BaseActivity {

    @ViewById(R.id.toolbarMain)
    Toolbar mToolbarMain;

    private String mTestId;
    private Test mTest;

    @Override
    protected Fragment createFragment() {
        return TestDetailFragment.newInstance(mTestId);
    }

    @Override
    public void afterView() {
        setSupportActionBar(mToolbarMain);
        getSupportActionBar().hide();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mTestId = bundle.getString(Constant.ATTR_TEST_ID);
            mTest = RealmHelper.getInstance(this).getTest(mTestId);
            mToolbarMain.setTitle(mTest.getTestTitle());
        }
    }
}
