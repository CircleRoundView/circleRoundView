package com.femote.homeui.ui.homemembers;

/**
 * Created by xiangxiang on 2016/5/24.
 * name: mr.k from in china
 */
public class MathUtils {

    /**
     * 获取一度的数值大小
     */
    public static final double PI_PER_DEGREE = Math.PI/180;
    /**
     * 将数值转换成指定角度
     * @param digitExpress 数字表示的角度
     */
    public static double degressOf(double digitExpress){
        return digitExpress/PI_PER_DEGREE;
    }

    /**
     * 获取指定角度的数值
     */
    public static double valueOf(double degresses){
        return degresses * PI_PER_DEGREE;
    }

}
