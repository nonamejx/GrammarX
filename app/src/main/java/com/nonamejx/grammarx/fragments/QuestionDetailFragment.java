package com.nonamejx.grammarx.fragments;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nonamejx.grammarx.R;
import com.nonamejx.grammarx.database.RealmHelper;
import com.nonamejx.grammarx.models.Answer;
import com.nonamejx.grammarx.models.Question;
import com.nonamejx.grammarx.models.UserAnswerItem;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import java.util.List;

/**
 * Created by noname on 03/08/2016.
 */
@EFragment(R.layout.fragment_question_detail)
public class QuestionDetailFragment extends BaseFragment {
    private static final String TAG = QuestionDetailFragment.class.getName();

    @FragmentArg
    String mQuestionId;

    private Question mQuestion;
    private List<Answer> mAnswers;
    private UserAnswerItem mUserAnswerItem;
    private int mSelectedOptionPosition = -1;

    @ViewById(R.id.tvQuestionInstruction)
    TextView mTvQuestionInstruction;
    @ViewById(R.id.tvQuestionTitle)
    TextView mTvQuestionTitle;
    @ViewById(R.id.btnCheck)
    Button mBtnCheck;
    @ViewById(R.id.llAnswerOptionsContainer)
    LinearLayout mLlAnswerOptionsContainer;
    private TextView[] mTvAnswerOptions;
    private IAnswerOptionOnclickListener mIAnswerOptionOnclick;

    public void setIAnswerOptionOnclickListener(IAnswerOptionOnclickListener onclickListener) {
        this.mIAnswerOptionOnclick = onclickListener;
    }

    public static QuestionDetailFragment newInstance(String questionId, IAnswerOptionOnclickListener iAnswerOptionOnclick) {
        QuestionDetailFragment fr = QuestionDetailFragment_.builder().mQuestionId(questionId).build();
        fr.setIAnswerOptionOnclickListener(iAnswerOptionOnclick);
        return fr;
    }

    @Click(R.id.btnCheck)
    void onButtonCheckClick() {
        if (mSelectedOptionPosition >= 0 && mUserAnswerItem != null) {
            mIAnswerOptionOnclick.OnAnswerCheckClick(mUserAnswerItem);
        }
    }

    @Override
    public void afterView() {
        // Init data
        mQuestion = RealmHelper.getInstance(getContext()).getQuestion(mQuestionId);
        mAnswers = mQuestion.getAnswers();
        mTvAnswerOptions = new TextView[mAnswers.size()];
        mUserAnswerItem = new UserAnswerItem();
        // Create answer options
        for (int i = 0, size = mAnswers.size(); i < size; i++) {
            final int position = i;
            mTvAnswerOptions[i] = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.textview_answer_option, null, false);
            mTvAnswerOptions[i].setText(mAnswers.get(i).getAnswerTitle());
            mTvAnswerOptions[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mSelectedOptionPosition != -1) {
                        mTvAnswerOptions[mSelectedOptionPosition].setBackgroundColor(Color.WHITE);
                    }
                    mSelectedOptionPosition = position;
                    mTvAnswerOptions[mSelectedOptionPosition].setBackgroundColor(getResources().getColor(R.color.colorLightOrange));
                    mBtnCheck.setTextColor(getResources().getColor(R.color.colorBlack));
                    mUserAnswerItem.setQuestion(mQuestion);
                    mUserAnswerItem.setUserChoice(mAnswers.get(position));
                    mIAnswerOptionOnclick.OnAnswerOptionClick(mUserAnswerItem);
                }
            });
            // Draw a line
            View separateLine = new View(getContext());
            separateLine.setBackgroundColor(getResources().getColor(R.color.colorLightGray));
            mLlAnswerOptionsContainer.addView(separateLine, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1));
            mLlAnswerOptionsContainer.addView(mTvAnswerOptions[i], new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }
        mTvQuestionTitle.setText(mQuestion.getQuestionTitle());
    }

    public interface IAnswerOptionOnclickListener {
        void OnAnswerOptionClick(UserAnswerItem userAnswerItem);

        void OnAnswerCheckClick(UserAnswerItem userAnswerItem);
    }
}
