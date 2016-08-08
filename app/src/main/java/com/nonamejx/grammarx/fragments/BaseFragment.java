package com.nonamejx.grammarx.fragments;

import android.support.v4.app.Fragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;

/**
 * Created by noname on 08/08/2016.
 */
@EFragment
public abstract class BaseFragment extends Fragment {
    @AfterViews
    public abstract void afterView();
}
