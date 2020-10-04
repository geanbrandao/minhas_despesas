package com.geanbrandao.minhasdespesas

import android.app.Activity
import android.content.Intent
import android.graphics.Point
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
