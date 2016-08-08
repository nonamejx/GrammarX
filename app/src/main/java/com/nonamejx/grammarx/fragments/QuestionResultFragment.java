package com.nonamejx.grammarx.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nonamejx.grammarx.R;
import com.nonamejx.grammarx.models.UserAnswerItem;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.parceler.Parcels;

/**
 * Created by noname on 04/08/2016.
 */
@EFragment(R.layout.fragment_question_result)
public class QuestionResultFragment extends BaseFragment {
    private static final String KEY_USER_CHOICE = "USER_CHOICE";
    private UserAnswerItem mUserAnswerItem;
    private boolean isCorrect = false;

    @ViewById(R.id.llAnswerOptionsContainer)
    LinearLayout mLlAnswerOptionsContainer;
    @ViewById(R.id.tvQuestionResultTitle)
    TextView mTvQuestionResultTitle;
    @ViewById(R.id.tvQuestionTitle)
    TextView mTvQuestionTitle;
    @ViewById(R.id.btnNext)
    Button btnNext;
    private TextView[] mTvQuestionOptions;
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

    @Click(R.id.btnNext)
    void onButtonNextClick() {
        mINextQuestionClick.onNextQuestionClick(mUserAnswerItem);
    }

    @Override
    public void afterView() {
        // Init data
        mUserAnswerItem = Parcels.unwrap(getArguments().getParcelable(KEY_USER_CHOICE));
        mTvQuestionOptions = new TextView[mUserAnswerItem.getQuestion().getAnswers().size()];
        isCorrect = mUserAnswerItem.getUserChoice().isCorrect();
        // Set background answer options
        if (isCorrect) {
            mTvQuestionResultTitle.setText(getResources().getString(R.string.textView_correct));
            mTvQuestionResultTitle.setBackgroundColor(getResources().getColor(R.color.colorDimGreen));
            mTvQuestionResultTitle.setTextColor(getResources().getColor(R.color.colorGreen));

        } else {
            mTvQuestionResultTitle.setText(getResources().getString(R.string.textView_incorrect));
            mTvQuestionResultTitle.setBackgroundColor(getResources().getColor(R.color.colorDimRed));
            mTvQuestionResultTitle.setTextColor(getResources().getColor(R.color.colorRed));
        }
        mTvQuestionTitle.setText(mUserAnswerItem.getQuestion().getQuestionTitle());
        for (int i = 0, size = mUserAnswerItem.getQuestion().getAnswers().size(); i < size; i++) {
            if (mUserAnswerItem.getQuestion().getAnswers().get(i).isCorrect()) {
                mTvQuestionOptions[i] = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.textview_right_answer, null, false);
            } else if (mUserAnswerItem.getQuestion().getAnswers().get(i).getAnswerId().equals(mUserAnswerItem.getUserChoice().getAnswerId())) {
                mTvQuestionOptions[i] = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.textview_wrong_answer, null, false);
            } else {
                mTvQuestionOptions[i] = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.textview_answer_option, null, false);
            }
            mTvQuestionOptions[i].setText(mUserAnswerItem.getQuestion().getAnswers().get(i).getAnswerTitle());
            View separateLine = new View(getContext());
            separateLine.setBackgroundColor(getResources().getColor(R.color.colorLightGray));
            mLlAnswerOptionsContainer.addView(separateLine, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1));
            mLlAnswerOptionsContainer.addView(mTvQuestionOptions[i], new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }
    }

    public interface INextQuestionClickListener {
        void onNextQuestionClick(UserAnswerItem userAnswerItem);
    }
}
