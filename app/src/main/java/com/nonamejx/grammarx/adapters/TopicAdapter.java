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

    private Context mContext;
    private List<Topic> mTopics;
    private String mHeaderTitle;

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
            ((TopicHeaderViewHolder) holder).txtHeader.setText(mHeaderTitle);
            ((TopicHeaderViewHolder) holder).txtHeader.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mContext instanceof MainActivity) {
                        MainActivity mainActivity = (MainActivity) mContext;
                        mainActivity.getSupportFragmentManager().popBackStack();
                    }
                }
            });
        } else if (holder instanceof TopicItemViewHolder) {
            final Topic topic = mTopics.get(position - 1);

            int numberOfTests = topic.getTests().size();
            int progress = RealmHelper.getInstance(mContext).countTakenTests(topic.getTopicId());

            ((TopicItemViewHolder) holder).txtTopicTitle.setText(topic.getTopicTitle());
            ((TopicItemViewHolder) holder).txtTopicProgress.setText(String.format("%d of %d tests completed", progress, numberOfTests));

            // Set drawable and animation to progressbar
            ProgressBar progressBar = ((TopicItemViewHolder) holder).progressBarTopic;
            progressBar.setMax(numberOfTests);
            Drawable drawable = mContext.getResources().getDrawable(R.drawable.custom_progressbar);
            progressBar.setProgressDrawable(drawable);
            ProgressbarAnimation animation = new ProgressbarAnimation(progressBar, 0, numberOfTests / 2);
            animation.setDuration(250);
            progressBar.startAnimation(animation);

            ((TopicItemViewHolder) holder).txtStudyTopic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    switchFragment(TestFragment.newInstance(topic.getTopicId()));
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mTopics.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        }
        return TYPE_ITEM;
    }

    private void switchFragment(Fragment fragment) {
        if (mContext instanceof MainActivity) {
            MainActivity mainActivity = (MainActivity) mContext;
            mainActivity.switchFragment(fragment);
        }
    }

    public static class TopicHeaderViewHolder extends RecyclerView.ViewHolder {
        public TextView txtHeader;

        public TopicHeaderViewHolder(View itemView) {
            super(itemView);
            txtHeader = (TextView) itemView.findViewById(R.id.txtGeneralHeaderTitle);
        }
    }

    public static class TopicItemViewHolder extends RecyclerView.ViewHolder {
        public TextView txtTopicTitle, txtTopicProgress, txtStudyTopic;
        public ProgressBar progressBarTopic;

        public TopicItemViewHolder(View itemView) {
            super(itemView);
            txtTopicTitle = (TextView) itemView.findViewById(R.id.txtTopicTitle);
            txtTopicProgress = (TextView) itemView.findViewById(R.id.txtTopicProgress);
            txtStudyTopic = (TextView) itemView.findViewById(R.id.txtStudyTopic);
            progressBarTopic = (ProgressBar) itemView.findViewById(R.id.progressbarTopic);
        }
    }
}
