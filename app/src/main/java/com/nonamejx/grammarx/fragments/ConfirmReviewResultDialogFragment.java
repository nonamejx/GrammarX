package com.nonamejx.grammarx.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.nonamejx.grammarx.R;
import com.nonamejx.grammarx.acitivities.DoTestActivity_;
import com.nonamejx.grammarx.acitivities.ReviewResultActivity_;
import com.nonamejx.grammarx.common.Constant;
import com.nonamejx.grammarx.database.RealmHelper;
import com.nonamejx.grammarx.models.Result;
import com.nonamejx.grammarx.models.Test;

/**
 * Created by noname on 09/08/2016.
 */
public class ConfirmReviewResultDialogFragment extends DialogFragment {
    private String mTestId;
    private Test mTest;

    private TextView mTvReviewResult;
    private TextView mTvTryAgain;
    private TextView mTvTestResult;

    public static ConfirmReviewResultDialogFragment newInstance(String testId) {
        ConfirmReviewResultDialogFragment fr = new ConfirmReviewResultDialogFragment();
        Bundle args = new Bundle();
        args.putString(Constant.ATTR_TEST_ID, testId);
        fr.setArguments(args);
        return fr;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTestId = getArguments().getString(Constant.ATTR_TEST_ID);
        mTest = RealmHelper.getInstance(getContext()).getTest(mTestId);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.dialog_confirm_review_result, null);
        mTvTestResult = (TextView) v.findViewById(R.id.tvTestResult);
        mTvReviewResult = (TextView) v.findViewById(R.id.tvReviewResult);
        mTvTryAgain = (TextView) v.findViewById(R.id.tvTryAgain);
        mTvTestResult.setText(String.format(getContext().getResources().getString(R.string.title_your_result, (float) mTest.getResult().countCorrectAnswer() * 100 / mTest.getQuestions().size())) + " %");
        mTvReviewResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ReviewResultActivity_.class);
                intent.putExtra(Constant.ATTR_TEST_ID, mTestId);
                getContext().startActivity(intent);
                dismiss();
            }
        });
        mTvTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Delete the old result
                Result result = mTest.getResult();
                RealmHelper.getInstance(getContext()).deleteResult(result);
                // Start a new test
                Intent intent = new Intent(getContext(), DoTestActivity_.class);
                intent.putExtra(Constant.ATTR_TEST_ID, mTestId);
                getContext().startActivity(intent);
                dismiss();
            }
        });
        return new AlertDialog.Builder(getActivity()).setView(v).create();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
