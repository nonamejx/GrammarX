package com.nonamejx.grammarx.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nonamejx.grammarx.R;
import com.nonamejx.grammarx.models.Level;

import java.util.List;

/**
 * Created by noname on 01/08/2016.
 */
public class LevelAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = LevelAdapter.class.getName();

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    private final Context mContext;
    private final List<Level> mLevels;

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
        if (viewHolder instanceof LevelItemViewHolder) {
            ((LevelItemViewHolder) viewHolder).mTvLevelTitle.setText(mLevels.get(i - 1).getLevelTitle());
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? TYPE_HEADER : TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return mLevels.size() + 1;
    }

    public class LevelItemViewHolder extends RecyclerView.ViewHolder {
        final TextView mTvLevelTitle;
        final TextView mTvStudyLevel;

        public LevelItemViewHolder(View v) {
            super(v);
            mTvLevelTitle = (TextView) v.findViewById(R.id.tvLevelTitle);
            mTvStudyLevel = (TextView) v.findViewById(R.id.tvStudyLevel);
        }
    }

    public class LevelHeaderViewHolder extends RecyclerView.ViewHolder {
        final TextView mTvLevelHeaderTitle;

        public LevelHeaderViewHolder(View v) {
            super(v);
            mTvLevelHeaderTitle = (TextView) v.findViewById(R.id.tvHeaderTitle);
        }
    }
}
