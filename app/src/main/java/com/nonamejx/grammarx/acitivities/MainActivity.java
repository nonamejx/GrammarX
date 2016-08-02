package com.nonamejx.grammarx.acitivities;

import android.support.v4.app.Fragment;

import com.nonamejx.grammarx.fragments.LevelFragment;

public class MainActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return LevelFragment.newInstance();
    }
}
