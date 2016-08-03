package com.nonamejx.grammarx.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nonamejx.grammarx.R;
import com.nonamejx.grammarx.acitivities.MainActivity;
import com.nonamejx.grammarx.fragments.TopicFragment;
import com.nonamejx.grammarx.models.Level;

import java.util.List;

/**
 * Created by noname on 01/08/2016.
 */
public class LevelAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = LevelAdapter.class.getName();

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    private Context mContext;
    private List<Level> mLevels;

    public LevelAdapter(Context context, List<Level> levels) {
        mContext = context;
        mLevels = levels;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        if (viewType == TYPE_HEADER) {
            View v = LayoutInflater.from(mContext).inflate(R.layout.item_level_header, viewGroup, false);
            return new LevelHeaderViewHolder(v);
        } else if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(mContext).inflate(R.layout.item_level, viewGroup, false);
            return new LevelItemViewHolder(v);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof LevelHeaderViewHolder) {
        } else if (viewHolder instanceof LevelItemViewHolder) {
            final Level level = mLevels.get(i - 1);
            ((LevelItemViewHolder) viewHolder).txtLevelTitle.setText(level.getLevelTitle());

            ((LevelItemViewHolder) viewHolder).txtStudyLevel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    switchFragment(TopicFragment.newInstance(level.getLevelId()));
                }
            });
        }
    }

    private void switchFragment(Fragment fragment) {
        if (mContext instanceof MainActivity) {
            MainActivity mainActivity = (MainActivity) mContext;
            mainActivity.switchFragment(fragment);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) return TYPE_HEADER;
        else return TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return mLevels.size() + 1;
    }

    public static class LevelItemViewHolder extends RecyclerView.ViewHolder {
        public TextView txtLevelTitle, txtStudyLevel;

        public LevelItemViewHolder(View v) {
            super(v);
            txtLevelTitle = (TextView) v.findViewById(R.id.txtLevelTitle);
            txtStudyLevel = (TextView) v.findViewById(R.id.txtStudyLevel);
        }
    }

    public static class LevelHeaderViewHolder extends RecyclerView.ViewHolder {
        public TextView txtLevelHeaderTitle;

        public LevelHeaderViewHolder(View v) {
            super(v);
            txtLevelHeaderTitle = (TextView) v.findViewById(R.id.txtHeaderTitle);
        }
    }
}