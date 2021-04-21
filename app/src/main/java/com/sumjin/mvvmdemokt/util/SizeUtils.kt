package com.sumjin.mvvmdemokt.util

import android.content.Context

object SizeUtils {
    fun dip2px(context: Context, dpValue: Float): Int {
        val scale: Float = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }
}