package com.shirun.wechatsendgroupchat.util;

import android.content.Context;

/**
 * ==========================================
 * <p/>
 * 作    者 : ying
 * <p/>
 * 创建时间 ： 2015/11/17.
 * <p/>
 * 用   途 :
 * <p/>
 * <p/>
 * ==========================================
 */
public class DensityUtil {
    /**
     * dp转换为px的方法
     * @param context
     * @param dpValue 需要被转换的dp的值
     * @return
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
