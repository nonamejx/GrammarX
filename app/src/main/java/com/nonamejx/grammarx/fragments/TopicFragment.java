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
import com.nonamejx.grammarx.acitivities.MainActivity;
import com.nonamejx.grammarx.adapters.TopicAdapter;
import com.nonamejx.grammarx.common.RecyclerTouchListener;
import com.nonamejx.grammarx.database.RealmHelper;
import com.nonamejx.grammarx.models.Level;
import com.nonamejx.grammarx.models.Topic;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;

import java.util.List;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;

/**
 * Created by noname on 01/08/2016.
 */
@EFragment
public class TopicFragment extends Fragment {
    @FragmentArg
    String mLevelId;

    private Level mLevel;

    private RecyclerView mRecyclerViewTopic;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<Topic> mTopics;

    public static TopicFragment newInstance(String levelId) {
        return TopicFragment_.builder().mLevelId(levelId).build();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        // Register onClick for recycler view
        mRecyclerViewTopic.addOnItemTouchListener(new RecyclerTouchListener(getContext(), mRecyclerViewTopic, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                if (getContext() instanceof MainActivity) {
                    if (position == 0) {
                        ((MainActivity) getContext()).getSupportFragmentManager().popBackStack();
                    } else {
                        ((MainActivity) getContext()).switchFragment(TestFragment.newInstance(mTopics.get(position - 1).getTopicId()), true);
                    }
                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        return v;
    }
}
