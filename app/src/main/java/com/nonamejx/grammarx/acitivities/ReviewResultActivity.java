package com.nonamejx.grammarx.acitivities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.nonamejx.grammarx.R;
import com.nonamejx.grammarx.common.Constant;
import com.nonamejx.grammarx.database.RealmHelper;
import com.nonamejx.grammarx.fragments.QuestionResultFragment;
import com.nonamejx.grammarx.fragments.TestResultFragment;
import com.nonamejx.grammarx.models.Result;
import com.nonamejx.grammarx.models.Test;
import com.nonamejx.grammarx.models.UserAnswerItem;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * Created by noname on 09/08/2016.
 */
@EActivity(R.layout.activity_review_result)
public class ReviewResultActivity extends AppCompatActivity {

    @ViewById(R.id.viewPageResult)
    ViewPager mViewPageResult;
    @ViewById(R.id.tvHeaderTitle)
    TextView mTvHeaderTitle;

    private ReviewResultViewPagerAdapter mAdapter;

    private TestResultFragment mTestResultFragment;
    private String mTestId;
    private Test mTest;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // getSupportActionBar().hide();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mTestId = bundle.getString(Constant.ATTR_TEST_ID);
            mTest = RealmHelper.getInstance(this).getTest(mTestId);
            mAdapter = new ReviewResultViewPagerAdapter(getSupportFragmentManager(), this, mTest.getResult());
        } else {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @AfterViews
    void afterView() {
        mTvHeaderTitle.setText(mTest.getTestTitle());
        mViewPageResult.setAdapter(mAdapter);
        // mViewPageResult.setPageMargin((int) getResources().getDimension(R.dimen.default_margin));
    }

    @Click(R.id.tvHeaderTitle)
    void onHeaderTitleClick() {
        finish();
    }

    private class ReviewResultViewPagerAdapter extends FragmentStatePagerAdapter {
        private final Context mContext;
        private final Result mResult;

        public ReviewResultViewPagerAdapter(FragmentManager fm, Context context, Result result) {
            super(fm);
            mContext = context;
            mResult = result;
        }

        @Override
        public Fragment getItem(int position) {
            return (position == (getCount() - 1) ? TestResultFragment.newInstance(mTest.getResult()) : QuestionResultFragment
                    .newInstance(mResult.getUserAnswerItems()
                            .get(position), new QuestionResultFragment.INextQuestionClickListener() {
                        @Override
                        public void onNextQuestionClick(UserAnswerItem userAnswerItem) {
                            // move to the next page
                            if (mViewPageResult != null) {
                                int currentPosition = mViewPageResult.getCurrentItem();
                                if (currentPosition < getCount() - 1) {
                                    mViewPageResult.setCurrentItem(currentPosition + 1, true);
                                }
                            }
                        }
                    }));
        }

        @Override
        public int getCount() {
            return mResult.getUserAnswerItems().size() + 1;
        }
    }
}
