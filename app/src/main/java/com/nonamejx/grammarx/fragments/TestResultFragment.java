package com.nonamejx.grammarx.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.nonamejx.grammarx.R;
import com.nonamejx.grammarx.acitivities.MainActivity;
import com.nonamejx.grammarx.models.Result;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by noname on 05/08/2016.
 */
@EFragment(R.layout.fragment_test_result)
public class TestResultFragment extends BaseFragment {
    private static final String TAG = TestResultFragment.class.getName();
    private static final String KEY_TEST_RESULT = "TEST_RESULT";

    private Result mResult;

    @ViewById(R.id.tvTotalQuestions)
    TextView mTvTotalQuestions;
    @ViewById(R.id.tvCorrectAnswers)
    TextView mTvCorrectAnswers;
    @ViewById(R.id.tvIncorrectAnswers)
    TextView mTvIncorrectAnswers;
    @ViewById(R.id.btnFinish)
    Button mBtnFinish;
    @ViewById(R.id.pieChartTestResult)
    PieChart mPieChartTestResult;
    private float[] mYData = new float[2];
    private String[] mXData = {"Correct", "Incorrect"};

    public static TestResultFragment newInstance(Result result) {
        TestResultFragment fr = new TestResultFragment_();
        Bundle args = new Bundle();
        args.putParcelable(KEY_TEST_RESULT, Parcels.wrap(result));
        fr.setArguments(args);
        return fr;
    }

    @Click(R.id.btnFinish)
    void onButtonFinishClick() {
        ((MainActivity) getContext()).getSupportFragmentManager().popBackStack();
    }

    @Override
    public void afterView() {
        // Init data
        mResult = Parcels.unwrap(getArguments().getParcelable(KEY_TEST_RESULT));
        mYData[0] = countCorrectAnswer(mResult);
        mYData[1] = mResult.getUserAnswerItems().size() - mYData[0];
        // Set data to textView
        mTvTotalQuestions.setText(mResult.getUserAnswerItems().size() + "");
        mTvCorrectAnswers.setText((int) mYData[0] + "");
        mTvIncorrectAnswers.setText((int) mYData[1] + "");
        // Configure pie chart
        mPieChartTestResult.setUsePercentValues(true);
        mPieChartTestResult.setDescription("");
        mPieChartTestResult.setExtraOffsets(5, 10, 5, 5);
        mPieChartTestResult.setDragDecelerationFrictionCoef(0.95f);
        // Enable hole and configure
        mPieChartTestResult.setDrawHoleEnabled(true);
        mPieChartTestResult.setHoleColor(Color.WHITE);
        mPieChartTestResult.setTransparentCircleColor(Color.WHITE);
        mPieChartTestResult.setTransparentCircleAlpha(110);
        mPieChartTestResult.setHoleRadius(58f);
        mPieChartTestResult.setTransparentCircleRadius(61f);
        // Enable draw center text
        mPieChartTestResult.setDrawCenterText(true);
        // Enable  rotation of the chart by touch
        mPieChartTestResult.setRotationAngle(0);
        mPieChartTestResult.setRotationEnabled(true);
        mPieChartTestResult.setHighlightPerTapEnabled(true);
        // Set a chart value selected listener
        mPieChartTestResult.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                if (e == null)
                    return;
            }
            @Override
            public void onNothingSelected() {
            }
        });
        // Add data
        addDataToPieChart();
        // Set animation
        mPieChartTestResult.animateY(1400, Easing.EasingOption.EaseInOutQuad);
        // Customize legends
        Legend l = mPieChartTestResult.getLegend();
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);
    }

    private void addDataToPieChart() {
        List<PieEntry> entries = new ArrayList<>();
        for (int i = 0; i < mYData.length; i++) {
            entries.add(new PieEntry(mYData[i], mXData[i]));
        }
        // Create pie data set
        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        // Add many colors
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(getResources().getColor(R.color.colorLightBlue));
        colors.add(getResources().getColor(R.color.colorLightRed));
        dataSet.setColors(colors);
        // Instantiate pie data object
        PieData pieData = new PieData(dataSet);
        pieData.setDataSet(dataSet);
        pieData.setValueFormatter(new PercentFormatter());
        pieData.setValueTextSize(11f);
        pieData.setValueTextColor(Color.WHITE);
        // Set data to chart
        mPieChartTestResult.setData(pieData);
        // Undo all highlights
        mPieChartTestResult.highlightValues(null);
        // Update pie chart
        mPieChartTestResult.invalidate();
    }

    private int countCorrectAnswer(Result result) {
        int count = 0;
        for (int i = 0, size = result.getUserAnswerItems().size(); i < size; i++) {
            if (result.getUserAnswerItems().get(i).getUserChoice().isCorrect()) {
                count++;
            }
        }
        return count;
    }
}
