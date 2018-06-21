package com.android.lixiang.base.utils.view

import android.content.Context
import android.content.res.Resources
import android.util.DisplayMetrics
import com.android.lixiang.base.common.BaseApplication

/**
 * Created by lixiang on 2018/1/30.
 */
class DimenUtil {
    fun getScreenWidth(): Int {
        val resources: Resources = BaseApplication().resources
        val dm: DisplayMetrics = resources.displayMetrics
        return dm.widthPixels
    }

    fun getScreenHeight(): Int {
        val resources: Resources = BaseApplication().resources
        val dm: DisplayMetrics = resources.displayMetrics
        return dm.heightPixels
    }

    fun dip2px(context: Context, dpValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    fun px2dip(context: Context, pxValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (pxValue / scale + 0.5f).toInt()
    }
}