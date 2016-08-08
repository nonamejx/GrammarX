package com.nonamejx.grammarx.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nonamejx.grammarx.R;
import com.nonamejx.grammarx.models.UserAnswerItem;

import org.androidannotations.annotations.EFragment;
import org.parceler.Parcels;

/**
 * Created by noname on 04/08/2016.
 */
@EFragment
public class QuestionResultFragment extends Fragment {
    private static final String KEY_USER_CHOICE = "USER_CHOICE";
    private UserAnswerItem mUserAnswerItem;
    private boolean isCorrect = false;

    private LinearLayout mLlAnswerOptionsContainer;
    private TextView mTvQuestionResultTitle;
    private TextView mTvQuestionTitle;
    private TextView[] mTvQuestionOptions;
    private Button btnNext;
    private INextQuestionClickListener mINextQuestionClick;

    public void setOnNextQuestionClick(INextQuestionClickListener onNextQuestionClick) {
        this.mINextQuestionClick = onNextQuestionClick;
    }

    public static QuestionResultFragment newInstance(UserAnswerItem userAnswerItem, INextQuestionClickListener iNextQuestionClick) {
        Bundle args = new Bundle();
        args.putParcelable(KEY_USER_CHOICE, Parcels.wrap(userAnswerItem));
        QuestionResultFragment fr = QuestionResultFragment_.builder().build();
        fr.setArguments(args);
        fr.setOnNextQuestionClick(iNextQuestionClick);
        return fr;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserAnswerItem = Parcels.unwrap(getArguments().getParcelable(KEY_USER_CHOICE));
        mTvQuestionOptions = new TextView[mUserAnswerItem.getQuestion().getAnswers().size()];
        isCorrect = mUserAnswerItem.getUserChoice().isCorrect();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_question_result, container, false);
        btnNext = (Button) v.findViewById(R.id.btnNext);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mINextQuestionClick.onNextQuestionClick(mUserAnswerItem);
            }
        });
        mLlAnswerOptionsContainer = (LinearLayout) v.findViewById(R.id.llAnswerOptionsContainer);
        mTvQuestionResultTitle = (TextView) v.findViewById(R.id.tvQuestionResultTitle);
        if (isCorrect) {
            mTvQuestionResultTitle.setText(getResources().getString(R.string.textView_correct));
            mTvQuestionResultTitle.setBackgroundColor(getResources().getColor(R.color.colorDimGreen));
            mTvQuestionResultTitle.setTextColor(getResources().getColor(R.color.colorGreen));

        } else {
            mTvQuestionResultTitle.setText(getResources().getString(R.string.textView_incorrect));
            mTvQuestionResultTitle.setBackgroundColor(getResources().getColor(R.color.colorDimRed));
            mTvQuestionResultTitle.setTextColor(getResources().getColor(R.color.colorRed));
        }
        mTvQuestionTitle = (TextView) v.findViewById(R.id.tvQuestionTitle);
        mTvQuestionTitle.setText(mUserAnswerItem.getQuestion().getQuestionTitle());
        for (int i = 0, size = mUserAnswerItem.getQuestion().getAnswers().size(); i < size; i++) {
            if (mUserAnswerItem.getQuestion().getAnswers().get(i).isCorrect()) {
                mTvQuestionOptions[i] = (TextView) inflater.inflate(R.layout.textview_right_answer, container, false);
            } else if (mUserAnswerItem.getQuestion().getAnswers().get(i).getAnswerId().equals(mUserAnswerItem.getUserChoice().getAnswerId())) {
                mTvQuestionOptions[i] = (TextView) inflater.inflate(R.layout.textview_wrong_answer, container, false);
            } else {
                mTvQuestionOptions[i] = (TextView) inflater.inflate(R.layout.textview_answer_option, container, false);
            }
            mTvQuestionOptions[i].setText(mUserAnswerItem.getQuestion().getAnswers().get(i).getAnswerTitle());
            View separateLine = new View(getContext());
            separateLine.setBackgroundColor(getResources().getColor(R.color.colorLightGray));
            mLlAnswerOptionsContainer.addView(separateLine, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1));
            mLlAnswerOptionsContainer.addView(mTvQuestionOptions[i], new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }

        return v;
    }

    public interface INextQuestionClickListener {
        void onNextQuestionClick(UserAnswerItem userAnswerItem);
    }
}
