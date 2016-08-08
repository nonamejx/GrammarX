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
import com.nonamejx.grammarx.adapters.TestAdapter;
import com.nonamejx.grammarx.common.RecyclerTouchListener;
import com.nonamejx.grammarx.database.RealmHelper;
import com.nonamejx.grammarx.models.Test;
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
public class TestFragment extends Fragment {

    @FragmentArg
    String mTopicId;

    private Topic mTopic;

    private RecyclerView mRecyclerViewTest;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<Test> mTests;

    public static TestFragment newInstance(String topicId) {
        return TestFragment_.builder().mTopicId(topicId).build();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTopic = RealmHelper.getInstance(getContext()).getTopic(mTopicId);
        mTests = RealmHelper.getInstance(getContext()).getTests(mTopicId);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_container, container, false);

        mLayoutManager = new LinearLayoutManager(getContext());
        mAdapter = new TestAdapter(getContext(), mTests, mTopic.getTopicTitle());
        mRecyclerViewTest = (RecyclerView) v.findViewById(R.id.recyclerViewLevel);
        mRecyclerViewTest.setLayoutManager(mLayoutManager);

        // Create animation adapter
        AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(mAdapter);
        ScaleInAnimationAdapter scaleInAdapter = new ScaleInAnimationAdapter(alphaAdapter);

        // set adapter to recycler view
        mRecyclerViewTest.setAdapter(scaleInAdapter);

        // Register onClick for recycler view
        mRecyclerViewTest.addOnItemTouchListener(new RecyclerTouchListener(getContext(), mRecyclerViewTest, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                if (getContext() instanceof MainActivity) {
                    if (position == 0) {
                        ((MainActivity) getContext()).getSupportFragmentManager().popBackStack();
                    } else {
                        ((MainActivity) getContext()).switchFragment(TestDetailFragment.newInstance(mTests.get(position - 1).getTestId()), true);
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
