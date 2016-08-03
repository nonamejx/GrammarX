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
import com.nonamejx.grammarx.adapters.TestAdapter;
import com.nonamejx.grammarx.database.RealmHelper;
import com.nonamejx.grammarx.models.Test;
import com.nonamejx.grammarx.models.Topic;

import org.androidannotations.annotations.EFragment;

import java.util.List;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;

/**
 * Created by noname on 01/08/2016.
 */
@EFragment
public class TestFragment extends Fragment {
    private static final String TAG = "TestFragment";
    public static final String KEY_TOPIC_ID = "TOPIC_ID";

    private String mTopicId;
    private Topic mTopic;

    private RecyclerView mRecyclerViewTest;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<Test> tests;

    public static TestFragment newInstance(String topicId) {
        TestFragment fragment = new TestFragment();
        Bundle args = new Bundle();
        args.putString(KEY_TOPIC_ID, topicId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTopicId = (String) getArguments().get(KEY_TOPIC_ID);
        mTopic = RealmHelper.getInstance(getContext()).getTopic(mTopicId);
        tests = RealmHelper.getInstance(getContext()).getTests(mTopicId);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_container, container, false);

        mLayoutManager = new LinearLayoutManager(getContext());
        mAdapter = new TestAdapter(getContext(), tests, mTopic.getTopicTitle());
        mRecyclerViewTest = (RecyclerView) v.findViewById(R.id.recyclerViewLevel);
        mRecyclerViewTest.setLayoutManager(mLayoutManager);

        // Create animation adapter
        AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(mAdapter);
        ScaleInAnimationAdapter scaleInAdapter = new ScaleInAnimationAdapter(alphaAdapter);

        mRecyclerViewTest.setAdapter(scaleInAdapter);

        return v;
    }
}