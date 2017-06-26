package ar.com.tzulberti.archerytraining.fragments.playoff;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ar.com.tzulberti.archerytraining.R;
import ar.com.tzulberti.archerytraining.database.consts.SerieInformationConsts;
import ar.com.tzulberti.archerytraining.helper.TournamentHelper;
import ar.com.tzulberti.archerytraining.model.common.ArrowsPerScore;
import ar.com.tzulberti.archerytraining.model.common.IElementByScore;
import ar.com.tzulberti.archerytraining.model.common.SeriesPerScore;
import ar.com.tzulberti.archerytraining.model.series.ArrowsPerTrainingType;
import ar.com.tzulberti.archerytraining.model.series.DistanceTotalData;
import ar.com.tzulberti.archerytraining.model.tournament.TournamentSerie;
import ar.com.tzulberti.archerytraining.model.tournament.TournamentSerieArrow;


/**
 * Show the stats for the playoffs on the selected date range
 *
 * Created by tzulberti on 6/25/17.
 */

public class ViewPlayoffStatsFragment extends BasePlayoffFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.cleanState(container);
        this.setObjects();
        View view = inflater.inflate(R.layout.playoff_view_stats, container, false);

        List<SeriesPerScore> seriesPerScoreList = this.playoffDAO.getSeriesPerScore();
        this.showSeriesPerScore(seriesPerScoreList, (HorizontalBarChart) view.findViewById(R.id.playoff_stats_series_stats));

        List<ArrowsPerScore> arrowsPerScores = this.playoffDAO.getArrowsPerScore();
        this.showArrowsPerScore(arrowsPerScores, (HorizontalBarChart) view.findViewById(R.id.playoff_stats_arrow_stats));


        this.showTableStats(arrowsPerScores, seriesPerScoreList, (TableLayout) view.findViewById(R.id.playoff_stats_table_data));
        return view;
    }


    private void showSeriesPerScore(List<SeriesPerScore> seriesPerScores, HorizontalBarChart horizontalBarChart) {
        if (seriesPerScores == null || seriesPerScores.isEmpty()) {
            return;
        }

        ArrayList<Integer> colors = new ArrayList<>();
        for (int c : ColorTemplate.VORDIPLOM_COLORS) {
            colors.add(c);
        }

        for (int c : ColorTemplate.JOYFUL_COLORS) {
            colors.add(c);
        }

        List<BarEntry> seriesCounterSet = new ArrayList<>();
        List<String> xAxis = new ArrayList<>();

        int index = 0;
        for (SeriesPerScore data : seriesPerScores) {
            seriesCounterSet.add(new BarEntry(index, data.seriesAmount));
            xAxis.add(String.valueOf(data.serieScore));
            index += 1;
        }

        BarDataSet set1 = new BarDataSet(seriesCounterSet, "");
        set1.setColors(colors);
        BarData data = new BarData();
        data.addDataSet(set1);

        XAxis xl = horizontalBarChart.getXAxis();
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
        xl.setDrawAxisLine(true);
        xl.setDrawGridLines(false);
        xl.setValueFormatter(new IndexAxisValueFormatter(xAxis));
        xl.setGranularity(1);
        xl.setLabelCount(xAxis.size());

        YAxis yl = horizontalBarChart.getAxisLeft();
        yl.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
        yl.setDrawGridLines(false);
        yl.setEnabled(false);
        yl.setAxisMinimum(0f);

        YAxis yr = horizontalBarChart.getAxisRight();
        yr.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
        yr.setDrawGridLines(false);
        yr.setAxisMinimum(0f);

        horizontalBarChart.setData(data);
        horizontalBarChart.getLegend().setEnabled(false);
        horizontalBarChart.setEnabled(false);
        horizontalBarChart.setTouchEnabled(false);
        horizontalBarChart.setMinimumHeight(xAxis.size() * 90);
        horizontalBarChart.getDescription().setEnabled(false);
        horizontalBarChart.invalidate();
    }


    private void showArrowsPerScore(List<ArrowsPerScore> arrowsPerScores, HorizontalBarChart horizontalBarChart) {
        List<BarEntry> arrowsCounterSet = new ArrayList<>();
        List<String> xAxis = new ArrayList<>();
        List<Integer> colors = new ArrayList<>();

        int index = 0;
        for (ArrowsPerScore arrowsPerScore : arrowsPerScores) {
            String score = TournamentHelper.getUserScore(arrowsPerScore.score, arrowsPerScore.isX);
            Integer color = TournamentHelper.getBackground(arrowsPerScore.score);
            arrowsCounterSet.add(new BarEntry(index, arrowsPerScore.arrowsAmount));
            xAxis.add(score);
            colors.add(color);
            index += 1;
        }

        BarDataSet set1 = new BarDataSet(arrowsCounterSet, "");
        set1.setColors(colors);
        BarData data = new BarData();
        data.addDataSet(set1);


        XAxis xl = horizontalBarChart.getXAxis();
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
        xl.setDrawAxisLine(true);
        xl.setDrawGridLines(false);
        xl.setValueFormatter(new IndexAxisValueFormatter(xAxis));
        xl.setGranularity(1);
        xl.setLabelCount(xAxis.size());

        YAxis yl = horizontalBarChart.getAxisLeft();
        yl.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
        yl.setDrawGridLines(false);
        yl.setEnabled(false);
        yl.setAxisMinimum(0f);

        YAxis yr = horizontalBarChart.getAxisRight();
        yr.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
        yr.setDrawGridLines(false);
        yr.setAxisMinimum(0f);

        horizontalBarChart.setData(data);
        horizontalBarChart.getLegend().setEnabled(false);
        horizontalBarChart.setEnabled(false);
        horizontalBarChart.setTouchEnabled(false);
        horizontalBarChart.getDescription().setEnabled(false);
        horizontalBarChart.invalidate();
    }


    private void showTableStats(List<ArrowsPerScore> arrowsPerScores, List<SeriesPerScore> seriesPerScores, TableLayout tableLayout) {
        if (arrowsPerScores == null || arrowsPerScores.isEmpty() || seriesPerScores == null || seriesPerScores.isEmpty()) {
            return;
        }
        Context context = this.getContext();
        TableRow arrowsStats = new TableRow(context);
        TextView arrowsStatsLabel = new TextView(context);
        arrowsStatsLabel.setText(R.string.playoff_stats_arrow_table_data);
        arrowsStats.addView(arrowsStatsLabel);
        this.populateRowData(arrowsPerScores, arrowsStats, context);

        TableRow seriesStats = new TableRow(context);
        TextView seriesStatsLable = new TextView(context);
        seriesStatsLable.setText(R.string.playoff_stats_series_table_data);
        seriesStats.addView(seriesStatsLable);
        this.populateRowData(seriesPerScores, seriesStats, context);

        tableLayout.addView(arrowsStats);
        tableLayout.addView(seriesStats);
    }

    private void populateRowData(List<? extends IElementByScore> scoreData, TableRow tr, Context context) {
        int min = -1;
        int max = -1;
        int count = 0;
        float sum = 0;

        for (IElementByScore elementByScore : scoreData) {
            int currentCount = elementByScore.getAmount();
            if (currentCount == 0) {
                // the value might just be included in the list just
                // to complete missing values
                continue;
            }

            int currentScore = elementByScore.getScore();
            count += currentCount;
            sum += currentScore * currentCount;
            if (min == -1 && count > 0) {
                min = currentScore;
                max = currentScore;
            }

            if (min > currentScore) {
                min = currentScore;
            }

            if (max < currentScore) {
                max = currentScore;
            }
        }

        double median = 0;
        int currentCount = 0;
        int countIndex = count / 2;
        int previousValue = 0;
        for (IElementByScore elementByScore : scoreData) {
            if (currentCount < countIndex && countIndex < currentCount + elementByScore.getAmount()) {
                // if the value is in the current set, then the median is the current value
                median = elementByScore.getScore();
                break;
            } else if (count % 2 == 1 && (currentCount == countIndex || currentCount + elementByScore.getAmount() == countIndex)) {
                median = elementByScore.getScore();
                break;
            } else if (count % 2 == 0 && currentCount == countIndex) {
                median = (previousValue + elementByScore.getScore()) / 2.0;
                break;
            } else if (count % 2 == 0 && currentCount + elementByScore.getAmount()  == countIndex) {
                previousValue = elementByScore.getScore();
            }
            currentCount += elementByScore.getAmount();
        }

        if (count > 0) {
            TextView minTextView = new TextView(context);
            minTextView.setText(String.valueOf(min));

            TextView avgTextView = new TextView(context);
            avgTextView.setText(String.valueOf(sum / count));

            TextView medianTextView = new TextView(context);
            medianTextView.setText(String.valueOf(median));

            TextView maxTextView = new TextView(context);
            maxTextView.setText(String.valueOf(max));

            tr.addView(minTextView);
            tr.addView(avgTextView);
            tr.addView(medianTextView);
            tr.addView(maxTextView);
        }

    }

    @Override
    public void handleClick(View v) {

    }
}
