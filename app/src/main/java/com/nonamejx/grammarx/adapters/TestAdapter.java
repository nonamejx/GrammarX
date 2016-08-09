package com.nonamejx.grammarx.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nonamejx.grammarx.R;
import com.nonamejx.grammarx.acitivities.MainActivity;
import com.nonamejx.grammarx.models.Test;

import java.util.List;

/**
 * Created by noname on 01/08/2016.
 */
public class TestAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_NEW_TEST = 1;
    private static final int TYPE_TAKEN_TEST = 2;

    private final Context mContext;
    private final List<Test> mTests;
    private final String mTopicTitle;

    public TestAdapter(Context context, List<Test> tests, String topicTitle) {
        this.mContext = context;
        this.mTests = tests;
        this.mTopicTitle = topicTitle;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            View v = LayoutInflater.from(mContext).inflate(R.layout.item_general_header, parent, false);
            return new TestHeaderViewHolder(v);
        } else if (viewType == TYPE_NEW_TEST) {
            View v = LayoutInflater.from(mContext).inflate(R.layout.item_new_test, parent, false);
            return new NewTestViewHolder(v);
        } else if (viewType == TYPE_TAKEN_TEST) {
            View v = LayoutInflater.from(mContext).inflate(R.layout.item_taken_test, parent, false);
            return new TakenTestViewHolder(v);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final Test test;
        if (holder instanceof TestHeaderViewHolder) {
            ((TestHeaderViewHolder) holder).mTvTestHeaderTitle.setText(mTopicTitle);
        } else if (holder instanceof NewTestViewHolder) {
            test = mTests.get(position - 1);
            final NewTestViewHolder newTestViewHolder = (NewTestViewHolder) holder;
            newTestViewHolder.mTvTestTitle.setText(test.getTestTitle());
            newTestViewHolder.mTvTotalQuestion.setText(String.format(mContext.getResources().getString(R.string.title_total_questions), test.getQuestions().size()));
        } else if (holder instanceof TakenTestViewHolder) {
            test = mTests.get(position - 1);
            final TakenTestViewHolder takenTestViewHolder = (TakenTestViewHolder) holder;
            takenTestViewHolder.mTvTestTitle.setText(test.getTestTitle());
            takenTestViewHolder.mTvTotalQuestion.setText(String.format(mContext.getResources().getString(R.string.title_total_questions, test.getQuestions().size())));
            takenTestViewHolder.mTvTestResult.setText(String.format(mContext.getResources().getString(R.string.title_your_result, (float) test.getResult().countCorrectAnswer() * 100 / test.getQuestions().size())) + " %");
        }
    }

    @Override
    public int getItemCount() {
        return mTests.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        } else {
            return mTests.get(position - 1).getResult() == null ? TYPE_NEW_TEST : TYPE_TAKEN_TEST;
        }
    }

    public class TestHeaderViewHolder extends RecyclerView.ViewHolder {
        final TextView mTvTestHeaderTitle;

        public TestHeaderViewHolder(View v) {
            super(v);
            mTvTestHeaderTitle = (TextView) v.findViewById(R.id.tvGeneralHeaderTitle);

            // Register onClick listener
            mTvTestHeaderTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mContext instanceof MainActivity) {
                        MainActivity mainActivity = (MainActivity) mContext;
                        mainActivity.getSupportFragmentManager().popBackStack();
                    }
                }
            });
        }

    }

    public class NewTestViewHolder extends RecyclerView.ViewHolder {
        final TextView mTvTestTitle;
        final TextView mTvTotalQuestion;

        public NewTestViewHolder(View v) {
            super(v);
            mTvTestTitle = (TextView) v.findViewById(R.id.tvTestTitle);
            mTvTotalQuestion = (TextView) v.findViewById(R.id.tvTotalQuestion);
        }
    }

    public class TakenTestViewHolder extends RecyclerView.ViewHolder {
        final TextView mTvTestTitle;
        final TextView mTvTotalQuestion;
        final TextView mTvTestResult;

        public TakenTestViewHolder(View v) {
            super(v);
            mTvTestTitle = (TextView) v.findViewById(R.id.tvTestTitle);
            mTvTotalQuestion = (TextView) v.findViewById(R.id.tvTotalQuestion);
            mTvTestResult = (TextView) v.findViewById(R.id.tvTestResult);
        }
    }

}
