package com.nonamejx.grammarx.fragments;

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
import org.androidannotations.annotations.ViewById;

import java.util.List;

import io.realm.Realm;

/**
 * Created by noname on 03/08/2016.
 */
@EFragment(R.layout.fragment_test_detail)
public class TestDetailFragment extends BaseFragment implements QuestionDetailFragment.IAnswerOptionOnclickListener, QuestionResultFragment.INextQuestionClickListener {
    private static final String TAG = TestDetailFragment.class.getName();
    private static final int TYPE_QUESTION_DETAIL = 0;
    private static final int TYPE_QUESTION_RESULT = 1;
    private static final int PROGRESSBAR_ANIMATION_DURATION = 500;

    @FragmentArg
    String mTestId;

    private Test mTest;
    private List<Question> mQuestions;
    private Result mResult;
    private int mCurrentQuestionPosition = 0;

    @ViewById(R.id.tvGeneralHeaderTitle)
    TextView mTvGeneralHeaderTitle;
    @ViewById(R.id.progressBarAnsweringProgress)
    ProgressBar mProgressBarAnsweringProgress;

    public static TestDetailFragment newInstance(String testId) {
        return TestDetailFragment_.builder().mTestId(testId).build();
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
            animation.setDuration(PROGRESSBAR_ANIMATION_DURATION);
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
            Realm realm = RealmHelper.getInstance(getContext()).getRealm();
            Result result = RealmHelper.getInstance(getContext()).addResult(mResult);
            realm.beginTransaction();
            mTest.setResult(result);
            realm.commitTransaction();

            // show TestResult fragment
            if (getContext() instanceof MainActivity) {
                ((MainActivity) getContext()).switchFragment(TestResultFragment.newInstance(mResult), false);
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

    @Override
    public void afterView() {
        // Init data
        mTest = RealmHelper.getInstance(getContext()).getTest(mTestId);
        mQuestions = mTest.getQuestions();
        mResult = new Result();

        RealmHelper.getInstance(getContext()).addResult(mResult);
        // Replace fragment
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
        mTvGeneralHeaderTitle.setText(mTest.getTestTitle());
        mProgressBarAnsweringProgress.setMax(mQuestions.size());
        mProgressBarAnsweringProgress.setProgressDrawable(getContext().getResources().getDrawable(R.drawable.custom_progressbar));
    }
}
