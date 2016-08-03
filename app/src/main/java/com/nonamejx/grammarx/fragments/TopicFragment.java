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
import com.nonamejx.grammarx.adapters.TopicAdapter;
import com.nonamejx.grammarx.database.RealmHelper;
import com.nonamejx.grammarx.models.Level;
import com.nonamejx.grammarx.models.Topic;

import org.androidannotations.annotations.EFragment;

import java.util.List;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;

/**
 * Created by noname on 01/08/2016.
 */
@EFragment
public class TopicFragment extends Fragment {

    public static final String KEY_LEVEL_ID = "LEVEL_ID";

    private String mLevelId;
    private Level mLevel;

    private RecyclerView mRecyclerViewTopic;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<Topic> mTopics;

    public static TopicFragment newInstance(String levelId) {
        TopicFragment fr = new TopicFragment();
        Bundle args = new Bundle();
        args.putString(KEY_LEVEL_ID, levelId);
        fr.setArguments(args);
        return fr;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLevelId = (String) getArguments().get(KEY_LEVEL_ID);
        mLevel = RealmHelper.getInstance(getContext()).getLevel(mLevelId);
        mTopics = RealmHelper.getInstance(getContext()).getTopics(mLevelId);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_container, container, false);

        mAdapter = new TopicAdapter(getContext(), mTopics, mLevel.getLevelTitle());
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerViewTopic = (RecyclerView) v.findViewById(R.id.recyclerViewLevel);
        mRecyclerViewTopic.setLayoutManager(mLayoutManager);

        // Create animation adapter
        AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(mAdapter);
        ScaleInAnimationAdapter scaleInAdapter = new ScaleInAnimationAdapter(alphaAdapter);

        mRecyclerViewTopic.setAdapter(scaleInAdapter);

        return v;
    }
}
