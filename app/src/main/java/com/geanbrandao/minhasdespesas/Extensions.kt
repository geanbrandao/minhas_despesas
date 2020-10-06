package com.geanbrandao.minhasdespesas

import android.app.Activity
import android.content.Intent
import android.content.res.Resources
import android.graphics.Color
import android.graphics.Point
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.TouchDelegate
import android.view.View
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.custom_error_dialog.view.*
import java.text.SimpleDateFormat
import java.time.Month
import java.util.*

fun Activity.getScreenWidth(percent: Float): Float {
    val display = windowManager.defaultDisplay
    val size = Point()
    display.getSize(size)
    val x = size.x
    return x * percent
}

fun Activity.goToActivity(activityClass: Class<*>) = startActivity(Intent(this, activityClass))

fun Activity.goToActivityFoResult(activityClass: Class<*>, requestCode: Int) {
    val intent = Intent(this, activityClass)
    startActivityForResult(intent, requestCode)
}

fun Date.getMonth3LettersName(): String = SimpleDateFormat("MMM", Locale.getDefault()).format(this)

fun Date.getDayNumber(): String = SimpleDateFormat("dd", Locale.getDefault()).format(this)

fun Date.getMonthName(): String =
    SimpleDateFormat("MMMM", Locale.getDefault()).format(this)

fun Date.getDayName(): String =
    SimpleDateFormat("EEEE", Locale.getDefault()).format(this)

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

fun Activity.formatDateString(year: Int, month: Int, day: Int): Date? {
    return SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse("$day/$month/$year")
}

fun Date.toStringDateFormated(): String {
    val builder = StringBuilder()
    builder.append(this.getDayName().capitalize())
    builder.append(", ")
    builder.append(this.getDayNumber())
    builder.append(" de ")
    builder.append(this.getMonthName().capitalize())
    return builder.toString()
}

fun Activity.showDialogMessage(message: String): AlertDialog {
    val dialogView =
        LayoutInflater.from(this).inflate(R.layout.custom_error_dialog, null)
    val builder = AlertDialog.Builder(this)
    builder.setView(dialogView)
    val alertDialog = builder.create()


    if (message.isNotEmpty()) {
        dialogView.text_message.text = message
    }

    dialogView.action_ok.setOnClickListener {
        alertDialog.dismiss()
    }

    alertDialog.setCanceledOnTouchOutside(false)
    alertDialog.setCancelable(false)

    alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

    alertDialog.show()

    return alertDialog
}