package com.nonamejx.grammarx.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nonamejx.grammarx.R;
import com.nonamejx.grammarx.acitivities.MainActivity;
import com.nonamejx.grammarx.animations.ProgressbarAnimation;
import com.nonamejx.grammarx.database.RealmHelper;
import com.nonamejx.grammarx.models.Question;
import com.nonamejx.grammarx.models.Result;
import com.nonamejx.grammarx.models.Test;
import com.nonamejx.grammarx.models.UserAnswerItem;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;

import java.util.List;

/**
 * Created by noname on 03/08/2016.
 */
@EFragment
public class TestDetailFragment extends Fragment implements QuestionDetailFragment.IAnswerOptionOnclickListener, QuestionResultFragment.INextQuestionClickListener {
    private static final String TAG = TestDetailFragment.class.getName();
    private static final int TYPE_QUESTION_DETAIL = 0;
    private static final int TYPE_QUESTION_RESULT = 1;

    @FragmentArg
    String mTestId;

    private Test mTest;
    private List<Question> mQuestions;
    private Result mResult;
    private int mCurrentQuestionPosition = 0;

    private TextView mTvGeneralHeaderTitle;
    private ProgressBar mProgressBarAnsweringProgress;

    public static TestDetailFragment newInstance(String testId) {
        return TestDetailFragment_.builder().mTestId(testId).build();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTest = RealmHelper.getInstance(getContext()).getTest(mTestId);
        mQuestions = mTest.getQuestions();
        mResult = new Result();
        RealmHelper.getInstance(getContext()).addResult(mResult);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_test_detail, container, false);
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.animation_enter_from_right,
                        R.anim.animation_exit_to_left,
                        R.anim.animation_enter_from_right,
                        R.anim.animation_exit_to_left)
                .add(R.id.flQuestionDetailContainer,
                        QuestionDetailFragment.newInstance(mTest.getQuestions()
                                .get(mCurrentQuestionPosition)
                                .getQuestionId(),
                                TestDetailFragment.this))
                .commit();
        mTvGeneralHeaderTitle = (TextView) v.findViewById(R.id.tvGeneralHeaderTitle);
        mTvGeneralHeaderTitle.setText(mTest.getTestTitle());
        mProgressBarAnsweringProgress = (ProgressBar) v.findViewById(R.id.progressBarAnsweringProgress);
        mProgressBarAnsweringProgress.setMax(mQuestions.size());
        mProgressBarAnsweringProgress.setProgressDrawable(getContext().getResources().getDrawable(R.drawable.custom_progressbar));
        return v;
    }

    @Override
    public void OnAnswerOptionClick(UserAnswerItem userAnswerItem) {
    }

    @Override
    public void OnAnswerCheckClick(UserAnswerItem userAnswerItem) {
        mCurrentQuestionPosition++;
        // set animation to progressbar
        if (mProgressBarAnsweringProgress != null) {
            ProgressbarAnimation animation = new ProgressbarAnimation(mProgressBarAnsweringProgress, mProgressBarAnsweringProgress.getProgress(), mProgressBarAnsweringProgress.getProgress() + 1);
            animation.setDuration(500);
            mProgressBarAnsweringProgress.startAnimation(animation);
        }
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.animation_enter_from_right,
                        R.anim.animation_exit_to_left,
                        R.anim.animation_enter_from_right,
                        R.anim.animation_exit_to_left)
                .replace(R.id.flQuestionDetailContainer,
                        QuestionResultFragment.newInstance(userAnswerItem, TestDetailFragment.this))
                .commit();
    }

    private boolean isEndTest() {
        return mCurrentQuestionPosition == mQuestions.size();
    }

    @Override
    public void onNextQuestionClick(UserAnswerItem userAnswerItem) {
        mResult.getUserAnswerItems().add(userAnswerItem);
        if (isEndTest()) {
            // add result to Realm

            // show TestResult fragment
            MainActivity mainActivity = (MainActivity) getContext();
            if (mainActivity != null) {
                mainActivity.switchFragment(TestResultFragment.newInstance(mResult), false);
            }
        } else {
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.animation_enter_from_right,
                            R.anim.animation_exit_to_left,
                            R.anim.animation_enter_from_right,
                            R.anim.animation_exit_to_left)
                    .replace(R.id.flQuestionDetailContainer,
                            QuestionDetailFragment.newInstance(mTest.getQuestions()
                                    .get(mCurrentQuestionPosition).getQuestionId(),
                                    TestDetailFragment.this))
                    .commit();
        }
    }
}
