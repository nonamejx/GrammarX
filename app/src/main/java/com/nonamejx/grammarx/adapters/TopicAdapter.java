package com.nonamejx.grammarx.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nonamejx.grammarx.R;
import com.nonamejx.grammarx.acitivities.MainActivity;
import com.nonamejx.grammarx.animations.ProgressbarAnimation;
import com.nonamejx.grammarx.database.RealmHelper;
import com.nonamejx.grammarx.fragments.TestFragment;
import com.nonamejx.grammarx.models.Topic;

import java.util.List;

/**
 * Created by noname on 01/08/2016.
 */
public class TopicAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = TopicAdapter.class.getName();

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

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
            ((TopicHeaderViewHolder) holder).tvHeader.setText(mHeaderTitle);
        } else if (holder instanceof TopicItemViewHolder) {
            final Topic topic = mTopics.get(position - 1);
            int progress = RealmHelper.getInstance(mContext).countTakenTests(topic.getTopicId());
            final ProgressBar progressBarTopic = ((TopicItemViewHolder) holder).progressBarTopic;

            ((TopicItemViewHolder) holder).tvTopicTitle.setText(topic.getTopicTitle());
            ((TopicItemViewHolder) holder).tvTopicProgress.setText(String.format("%d of %d tests completed", progress, progressBarTopic.getMax()));

            // Set animation to progressbar
            ProgressbarAnimation animation = new ProgressbarAnimation(progressBarTopic, 0, progressBarTopic.getMax() / 2);
            animation.setDuration(250);
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

    private void switchFragment(Fragment fragment) {
        if (mContext instanceof MainActivity) {
            ((MainActivity) mContext).switchFragment(fragment);
        }
    }

    public class TopicHeaderViewHolder extends RecyclerView.ViewHolder {
        public TextView tvHeader;

        public TopicHeaderViewHolder(View itemView) {
            super(itemView);
            tvHeader = (TextView) itemView.findViewById(R.id.tvGeneralHeaderTitle);

            // Register onClick listener
            tvHeader.setOnClickListener(new View.OnClickListener() {
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

    public class TopicItemViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTopicTitle, tvTopicProgress, tvStudyTopic;
        public ProgressBar progressBarTopic;

        public TopicItemViewHolder(View itemView) {
            super(itemView);
            tvTopicTitle = (TextView) itemView.findViewById(R.id.tvTopicTitle);
            tvTopicProgress = (TextView) itemView.findViewById(R.id.tvTopicProgress);
            tvStudyTopic = (TextView) itemView.findViewById(R.id.tvStudyTopic);
            progressBarTopic = (ProgressBar) itemView.findViewById(R.id.progressbarTopic);

            // Set max and drawable to progressbar
            final Topic topic = mTopics.get(getAdapterPosition() - 1);
            progressBarTopic.setMax(topic.getTests().size());

            final Drawable drawable = mContext.getResources().getDrawable(R.drawable.custom_progressbar);
            progressBarTopic.setProgressDrawable(drawable);

            // Register onClick listener
            tvStudyTopic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    switchFragment(TestFragment.newInstance(topic.getTopicId()));
                }
            });
        }
    }
}
