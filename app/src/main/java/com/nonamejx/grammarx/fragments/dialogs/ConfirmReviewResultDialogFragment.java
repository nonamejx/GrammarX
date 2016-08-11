package com.nonamejx.grammarx.fragments.dialogs;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.nonamejx.grammarx.R;
import com.nonamejx.grammarx.acitivities.DoTestActivity_;
import com.nonamejx.grammarx.acitivities.ReviewResultActivity_;
import com.nonamejx.grammarx.common.Constant;
import com.nonamejx.grammarx.database.RealmHelper;
import com.nonamejx.grammarx.fragments.ConfirmReviewResultDialogFragment_;
import com.nonamejx.grammarx.models.Result;
import com.nonamejx.grammarx.models.Test;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

/**
 * Created by noname on 09/08/2016.
 */
@EFragment(R.layout.dialog_confirm_review_result)
public class ConfirmReviewResultDialogFragment extends DialogFragment {
    @FragmentArg
    String mTestId;
    private Test mTest;

    @ViewById(R.id.tvReviewResult)
    TextView mTvReviewResult;
    @ViewById(R.id.tvTryAgain)
    TextView mTvTryAgain;
    @ViewById(R.id.tvTestResult)
    TextView mTvTestResult;

    public static ConfirmReviewResultDialogFragment newInstance(String testId) {
        return ConfirmReviewResultDialogFragment_.builder().mTestId(testId).build();
    }

    @AfterViews
    void afterView() {
        mTest = RealmHelper.getInstance(getContext()).getTest(mTestId);
        mTvTestResult.setText(String.format(getContext().getResources().getString(R.string.title_your_result, (float) mTest.getResult().countCorrectAnswer() * 100 / mTest.getQuestions().size())) + " %");
    }

    @Click(R.id.tvReviewResult)
    void onTvReviewResultClick() {
        Intent intent = new Intent(getContext(), ReviewResultActivity_.class);
        intent.putExtra(Constant.ATTR_TEST_ID, mTestId);
        getContext().startActivity(intent);
        dismiss();
    }

    @Click(R.id.tvTryAgain)
    void onTvTryAgainClick() {
        // Delete the old result
        Result result = mTest.getResult();
        RealmHelper.getInstance(getContext()).deleteResult(result);
        // Start a new test
        Intent intent = new Intent(getContext(), DoTestActivity_.class);
        intent.putExtra(Constant.ATTR_TEST_ID, mTestId);
        getContext().startActivity(intent);
        dismiss();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // dialog.setContentView(R.layout.dialog_call);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return dialog;
    }
}
