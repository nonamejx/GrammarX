package com.nonamejx.grammarx.acitivities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.nonamejx.grammarx.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * Created by Noname on 28/6/2016.
 */

@EActivity
public abstract class BaseActivity extends AppCompatActivity {

    // Create a single fragment
    protected abstract Fragment createFragment();
    public abstract void afterView();

    @ViewById(R.id.flContainer)
    FrameLayout mFlContainer;

    @AfterViews
    public void init() {
        afterView();

        // Add fragment
        if (mFlContainer != null) {
            FragmentManager fm = getSupportFragmentManager();
            Fragment fragment = fm.findFragmentById(R.id.flContainer);
            if (fragment == null) {
                fragment = createFragment();
                fm.beginTransaction().add(R.id.flContainer, fragment).commit();
            }
        }
    }

    public void switchFragment(Fragment fragment, boolean addToBackStack) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.flContainer, fragment);
        if (addToBackStack) {
            ft.addToBackStack(null);
        }
        ft.commit();
    }
}
