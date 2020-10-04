package com.geanbrandao.minhasdespesas

import android.app.Activity
import android.content.Intent
import android.content.res.Resources
import android.graphics.Point
import android.graphics.Rect
import android.util.TypedValue
import android.view.TouchDelegate
import android.view.View
import java.text.SimpleDateFormat
import java.util.*

fun Activity.getScreenWidth(percent: Float): Float {
    val display = windowManager.defaultDisplay
    val size = Point()
    display.getSize(size)
    val x = size.x
    return x * percent
}

fun Activity.goToActivity(activityClass: Class<*>) = startActivity(Intent(this, activityClass))


fun Date.getMonth3LettersName(): String = SimpleDateFormat("MMM", Locale.getDefault()).format(this)

fun Date.getDayNumber(): String = SimpleDateFormat("dd", Locale.getDefault()).format(this)

fun View.increaseHitArea(dp: Float) {
    // increase the hit area
    val increasedArea = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp,
        Resources.getSystem().displayMetrics
    ).toInt()
    val parent = parent as View
    parent.post {
        val rect = Rect()
        getHitRect(rect)
        rect.top -= increasedArea
        rect.left -= increasedArea
        rect.bottom += increasedArea
        rect.right += increasedArea
        parent.touchDelegate = TouchDelegate(rect, this)
    }
}