package com.casic.mpandroidchartdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private LineChart chart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //initView();

        chart = findViewById(R.id.chart1);
        ArrayList<ChartBean> chartBeans = new ArrayList<>();
        ChartBean chartBean = null;
        for (int i = 0; i <10 ; i++) {
             chartBean = new ChartBean();
            chartBean.setxName(i+"");
            chartBean.setyValue((i+1));
            chartBeans.add(chartBean);
        }

        ChartManager chartManager = new ChartManager(this);
        chartManager.createLineChart(chart,chartBeans);

    }

}
