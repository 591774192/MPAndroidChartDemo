package com.casic.mpandroidchartdemo;

/**
 * @author 郭宝
 * @project： MPAndroidChartDemo
 * @package： com.casic.mpandroidchartdemo
 * @date： 2019/5/15 0015 13:52
 * @brief: 图标bean
 */
public class ChartBean {


    //X轴的名称
    private String xName;
    //Y轴对应的值
    private float yValue;

    public String getxName() {
        return xName;
    }

    public void setxName(String xName) {
        this.xName = xName;
    }

    public float getyValue() {
        return yValue;
    }

    public void setyValue(float yValue) {
        this.yValue = yValue;
    }
}
