package com.nonamejx.grammarx.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nonamejx.grammarx.R;
import com.nonamejx.grammarx.adapters.LevelAdapter;
import com.nonamejx.grammarx.database.RealmHelper;
import com.nonamejx.grammarx.models.Level;

import org.androidannotations.annotations.EFragment;

import java.util.List;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;

/**
 * Created by noname on 01/08/2016.
 */
@EFragment
public class LevelFragment extends Fragment {

    private RecyclerView mRecyclerViewLevel;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<Level> mLevels;

    public static LevelFragment newInstance() {
        return new LevelFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLevels = RealmHelper.getInstance(getContext()).getLevels();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_container, container, false);

        mAdapter = new LevelAdapter(getContext(), mLevels);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerViewLevel = (RecyclerView) v.findViewById(R.id.recyclerViewLevel);
        mRecyclerViewLevel.setLayoutManager(mLayoutManager);

        // Create animation adapter
        AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(mAdapter);
        ScaleInAnimationAdapter scaleInAdapter = new ScaleInAnimationAdapter(alphaAdapter);

        mRecyclerViewLevel.setAdapter(scaleInAdapter);

        return v;
    }
}
