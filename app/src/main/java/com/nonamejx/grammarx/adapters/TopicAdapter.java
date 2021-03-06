package com.nonamejx.grammarx.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nonamejx.grammarx.R;
import com.nonamejx.grammarx.animations.ProgressbarAnimation;
import com.nonamejx.grammarx.database.RealmHelper;
import com.nonamejx.grammarx.models.Topic;

import java.util.List;

/**
 * Created by noname on 01/08/2016.
 */
public class TopicAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = TopicAdapter.class.getName();

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private static final int PROGRESSBAR_ANIMATION_DURATION = 250;

    private final Context mContext;
    private final List<Topic> mTopics;
    private final String mHeaderTitle;

    public TopicAdapter(Context context, List<Topic> topics, String headerTitle) {
        this.mContext = context;
        this.mTopics = topics;
        this.mHeaderTitle = headerTitle;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            View v = LayoutInflater.from(mContext).inflate(R.layout.item_general_header, parent, false);
            return new TopicHeaderViewHolder(v);
        } else if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(mContext).inflate(R.layout.item_topic, parent, false);
            return new TopicItemViewHolder(v);
        } else {
            return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof TopicHeaderViewHolder) {
            ((TopicHeaderViewHolder) holder).mTvHeader.setText(mHeaderTitle);
        } else if (holder instanceof TopicItemViewHolder) {
            final Topic topic = mTopics.get(position - 1);
            int progress = RealmHelper.getInstance(mContext).countTakenTests(topic.getTopicId());
            final ProgressBar progressBarTopic = ((TopicItemViewHolder) holder).mProgressBarTopic;
            progressBarTopic.setMax(topic.getTests().size());

            ((TopicItemViewHolder) holder).mTvTopicTitle.setText(topic.getTopicTitle());
            ((TopicItemViewHolder) holder).mTvTopicProgress.setText(String.format("%d of %d tests completed", progress, progressBarTopic.getMax()));

            // Set animation to progressbar
            ProgressbarAnimation animation = new ProgressbarAnimation(progressBarTopic, 0, progress);
            animation.setDuration(PROGRESSBAR_ANIMATION_DURATION);
            progressBarTopic.startAnimation(animation);
        }
    }

    @Override
    public int getItemCount() {
        return mTopics.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? TYPE_HEADER : TYPE_ITEM;
    }

    public class TopicHeaderViewHolder extends RecyclerView.ViewHolder {
        final TextView mTvHeader;

        public TopicHeaderViewHolder(View itemView) {
            super(itemView);
            mTvHeader = (TextView) itemView.findViewById(R.id.tvHeaderTitle);
        }
    }

    public class TopicItemViewHolder extends RecyclerView.ViewHolder {
        final TextView mTvTopicTitle;
        final TextView mTvTopicProgress;
        final TextView mTvStudyTopic;
        final ProgressBar mProgressBarTopic;

        public TopicItemViewHolder(View itemView) {
            super(itemView);
            mTvTopicTitle = (TextView) itemView.findViewById(R.id.tvTopicTitle);
            mTvTopicProgress = (TextView) itemView.findViewById(R.id.tvTopicProgress);
            mTvStudyTopic = (TextView) itemView.findViewById(R.id.tvStudyTopic);
            mProgressBarTopic = (ProgressBar) itemView.findViewById(R.id.progressbarTopic);
            // Set drawable to progressbar
            mProgressBarTopic.setProgressDrawable(mContext.getResources().getDrawable(R.drawable.custom_progressbar));
        }
    }
}
