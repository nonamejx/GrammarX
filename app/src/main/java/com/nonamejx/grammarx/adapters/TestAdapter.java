package com.nonamejx.grammarx.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
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

    private Context mContext;
    private List<Test> mTests;
    private String mTopicTitle;

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
        Test test;
        if (holder instanceof TestHeaderViewHolder) {
            ((TestHeaderViewHolder) holder).txtTestHeaderTitle.setText(mTopicTitle);
            ((TestHeaderViewHolder) holder).txtTestHeaderTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mContext instanceof MainActivity) {
                        MainActivity mainActivity = (MainActivity) mContext;
                        mainActivity.getSupportFragmentManager().popBackStack();
                    }
                }
            });
        } else if (holder instanceof NewTestViewHolder) {
            test = mTests.get(position - 1);
            ((NewTestViewHolder) holder).txtTestTitle.setText(test.getTestTitle());
            ((NewTestViewHolder) holder).txtTotalQuestion.setText(String.format("Total question: %d", test.getQuestions().size()));
        } else if (holder instanceof TakenTestViewHolder) {
            test = mTests.get(position - 1);
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
            Test t = mTests.get(position - 1);
            if (t.getResult() == null) {
                return TYPE_NEW_TEST;
            } else {
                return TYPE_TAKEN_TEST;
            }
        }
    }

    public static class TestHeaderViewHolder extends RecyclerView.ViewHolder {
        public TextView txtTestHeaderTitle;

        public TestHeaderViewHolder(View v) {
            super(v);
            txtTestHeaderTitle = (TextView) v.findViewById(R.id.txtGeneralHeaderTitle);
        }

    }

    public static class NewTestViewHolder extends RecyclerView.ViewHolder {
        public TextView txtTestTitle, txtTotalQuestion;

        public NewTestViewHolder(View v) {
            super(v);
            txtTestTitle = (TextView) v.findViewById(R.id.txtTestTitle);
            txtTotalQuestion = (TextView) v.findViewById(R.id.txtTotalQuestion);
        }
    }

    public static class TakenTestViewHolder extends RecyclerView.ViewHolder {
        public TextView txtTestTitle, txtScore;
        public ProgressBar progressBarTest;

        public TakenTestViewHolder(View v) {
            super(v);
            txtTestTitle = (TextView) v.findViewById(R.id.txtTestTitle);
            txtScore = (TextView) v.findViewById(R.id.txtScore);
            progressBarTest = (ProgressBar) v.findViewById(R.id.progressbarTest);
        }
    }

}
