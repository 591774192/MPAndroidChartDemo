package com.casic.mpandroidchartdemo;

import android.content.Context;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 郭宝
 * @project： MPAndroidChartDemo
 * @package： com.casic.mpandroidchartdemo
 * @date： 2019/5/15 0015 11:21
 * @brief: 图标管理器
 */
public class ChartManager {

    private Context mContext;

    public ChartManager(Context context) {
        mContext = context;
    }


    /**
     * 创建曲线图表
     * @param chart
     * @param chartBeans
     */
    public void createLineChart(final LineChart chart, List<ChartBean> chartBeans){


        //初始图表样式
        {
            // background color
            chart.setBackgroundColor(Color.WHITE);

            // disable description text
            chart.getDescription().setEnabled(false);

            // enable touch gestures
            chart.setTouchEnabled(true);

            // set listeners
            chart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
                @Override
                public void onValueSelected(Entry e, Highlight h) {
                    Log.i("Entry selected", e.toString());
                    Log.i("LOW HIGH", "low: " + chart.getLowestVisibleX() + ", high: " + chart.getHighestVisibleX());
                    Log.i("MIN MAX", "xMin: " + chart.getXChartMin() + ", xMax: " + chart.getXChartMax() + ", yMin: " + chart.getYChartMin() + ", yMax: " + chart.getYChartMax());
                }

                @Override
                public void onNothingSelected() {

                    Log.i("Nothing selected", "Nothing selected.");
                }
            });
            chart.setDrawGridBackground(false);

            // create marker to display box when values are selected
            MyMarkerView mv = new MyMarkerView(mContext, R.layout.custom_marker_view);

            // Set the marker to the chart
            mv.setChartView(chart);
            chart.setMarker(mv);

            // enable scaling and dragging
            chart.setDragEnabled(true);
            chart.setScaleEnabled(true);
            // chart.setScaleXEnabled(true);
            // chart.setScaleYEnabled(true);

            // force pinch zoom along both axis
            chart.setPinchZoom(true);
        }


        XAxis xAxis;
        {   // // X-Axis Style // //
            xAxis = chart.getXAxis();

            // vertical grid lines
            xAxis.enableGridDashedLine(10f, 10f, 0f);
        }


        YAxis yAxis;
        {   // // Y-Axis Style // //
            yAxis = chart.getAxisLeft();

            // disable dual axis (only use LEFT axis)
            chart.getAxisRight().setEnabled(false);

            // horizontal grid lines
            yAxis.enableGridDashedLine(10f, 10f, 0f);

            // axis range
            yAxis.setAxisMaximum(200f);
            yAxis.setAxisMinimum(-50f);
        }

        {   // // Create Limit Lines // //

            Typeface tfRegular;
            LimitLine llXAxis = new LimitLine(9f, "Index 10");
            llXAxis.setLineWidth(4f);
            llXAxis.enableDashedLine(10f, 10f, 0f);
            llXAxis.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
            llXAxis.setTextSize(10f);
            tfRegular = Typeface.createFromAsset(mContext.getAssets(), "OpenSans-Regular.ttf");
            llXAxis.setTypeface(tfRegular);

            LimitLine ll1 = new LimitLine(150f, "Upper Limit");
            ll1.setLineWidth(4f);
            ll1.enableDashedLine(10f, 10f, 0f);
            ll1.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
            ll1.setTextSize(10f);
            ll1.setTypeface(tfRegular);

            LimitLine ll2 = new LimitLine(-30f, "Lower Limit");
            ll2.setLineWidth(4f);
            ll2.enableDashedLine(10f, 10f, 0f);
            ll2.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
            ll2.setTextSize(10f);
            ll2.setTypeface(tfRegular);

            // draw limit lines behind data instead of on top
            yAxis.setDrawLimitLinesBehindData(true);
            xAxis.setDrawLimitLinesBehindData(true);

            // add limit lines
            yAxis.addLimitLine(ll1);
            yAxis.addLimitLine(ll2);
            //xAxis.addLimitLine(llXAxis);
        }


        // add data
        setData(chart, chartBeans);

        // draw points over time
        chart.animateX(1500);

        // get the legend (only possible after setting data)
        Legend l = chart.getLegend();

        // draw legend entries as lines
        l.setForm(Legend.LegendForm.LINE);
    }

    /**
     * 设置数据
     */
    private void setData(final LineChart chart, List<ChartBean> chartBeans) {

        ArrayList<Entry> values = new ArrayList<>();
        ChartBean chartBean = null;
        for (int i = 0; i < chartBeans.size(); i++) {

            chartBean = chartBeans.get(i);
            float yValue = chartBean.getyValue();

            /*
            i 表示X轴的值
            val 表示Y轴的值
            getResources().getDrawable(R.drawable.star) 表示图标头像
             */
            values.add(new Entry(i, yValue, chart.getContext().getResources().getDrawable(R.drawable.star)));
        }

        LineDataSet set1;

        /*
        chart.getData() 表示获取图标对象
         */
        if (chart.getData() != null &&
                chart.getData().getDataSetCount() > 0) {

            //获取第一个数据集
            set1 = (LineDataSet) chart.getData().getDataSetByIndex(0);
            //设置数据
            set1.setValues(values);
            set1.notifyDataSetChanged();
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();
        } else {
            // 创建一个数据集并给她一个类型,
            set1 = new LineDataSet(values, "DataSet 1");

            //不设置图标
            set1.setDrawIcons(false);

            //画虚线
            set1.enableDashedLine(10f, 5f, 0f);

            //设置虚线的颜色
            set1.setColor(Color.RED);
            //设置点的颜色
            set1.setCircleColor(Color.BLACK);

            //设置线的宽度
            set1.setLineWidth(1f);
            //设置点的半径
            set1.setCircleRadius(3f);

            // true 表示设置点为空心的
            set1.setDrawCircleHole(true);

            // 设置窗体线条的宽度
            set1.setFormLineWidth(1f);
            //设置窗体划线效果
            set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
            //设置窗体大小
            set1.setFormSize(15.f);

            // 设置值的文字大小
            set1.setValueTextSize(9f);

            // 设置虚线突出显示线
            set1.enableDashedHighlightLine(10f, 5f, 0f);

            // 设置是否填充曲线底部的区域，false表示不填充
            set1.setDrawFilled(true);
            //设置填充回调
            set1.setFillFormatter(new IFillFormatter() {
                @Override
                public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                    return chart.getAxisLeft().getAxisMinimum();
                }
            });

            //设置曲线底部填充区域的颜色
            if (Utils.getSDKInt() >= 18) {
                // drawables only supported on api level 18 and above
                Drawable drawable = ContextCompat.getDrawable(chart.getContext(), R.drawable.fade_red);
                set1.setFillDrawable(drawable);
            } else {
                set1.setFillColor(Color.BLACK);
            }

            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1); // add the data sets

            // create a data object with the data sets
            LineData data = new LineData(dataSets);

            // set data
            chart.setData(data);
        }
    }
}
