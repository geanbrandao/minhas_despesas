package com.geanbrandao.minhasdespesas

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.content.res.TypedArray
import android.graphics.Color
import android.graphics.Point
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.Html
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.TouchDelegate
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.geanbrandao.minhasdespesas.model.Category
import com.geanbrandao.minhasdespesas.model.Expense
import com.geanbrandao.minhasdespesas.model.database.entity_categories.CategoriesData
import com.geanbrandao.minhasdespesas.model.database.entity_expenses.ExpensesData
import io.reactivex.Flowable
import io.reactivex.Single
import kotlinx.android.synthetic.main.dialog_error.view.*
import org.jetbrains.anko.windowManager
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

fun Activity.getScreenWidth(percent: Float): Float {
    val display = windowManager.defaultDisplay
    val size = Point()
    display.getSize(size)
    val x = size.x
    return x * percent
}

fun Context.getScreenHeight(percent: Float): Float {
    val display = windowManager.defaultDisplay
    val size = Point()
    display.getSize(size)
    val y = size.y
    return y * percent
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
        LayoutInflater.from(this).inflate(R.layout.dialog_error, null)
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

fun CategoriesData.mapTo(): Category {
    return Category(this.id, this.name, this.icon, this.canRemove, false, this.colorName)
}

fun Category.mapTo(): CategoriesData {
    return CategoriesData(this.id, this.name, this.icon, this.canRemove, this.colorName)
}

fun Expense.mapTo(): ExpensesData {
    return ExpensesData(this.id, this.amount, this.title, this.date, this.description)
}

fun ExpensesData.mapTo(list: List<CategoriesData>): Expense {
    val categories = arrayListOf<Category>()
    list.forEach {
        categories.add(it.mapTo())
    }
    return Expense(this.id, this.amount, this.title, this.date, this.description, categories)
}

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.getIcon(iconId: Int, colorId: Int): Drawable? {
    val icon = ContextCompat.getDrawable(this, iconId)
    icon?.let {
        val h: Int = it.intrinsicHeight
        val w: Int = it.intrinsicWidth
        it.setBounds(0, 0, w, h)
        it.setTint(ContextCompat.getColor(this, colorId))
        return icon
    } ?: run {
        return null
    }
}

fun Context.getIconFromString(name: String, colorId: Int): Drawable? {
    return getIcon(resources.getIdentifier(name, "drawable", packageName), colorId)
}

/**
 * TODO
 *
 * @param name of color
 * @return int que corresponde ao id da cor
 */
fun Context.getColorFromString(name: String): Int {
    Timber.d("DEBUG1 - $name")
    return resources.getIdentifier(name, "color", packageName)
}


fun getTimestamp(): String {
    val format = SimpleDateFormat("E MMM d HH:mm:ss zzz yyyy", Locale.getDefault())
    return Date().toString()
}

fun String.toDate(): Date? {
    return try {
        val dateFormat = SimpleDateFormat("E MMM d HH:mm:ss zzz yyyy", Locale.US)
        dateFormat.parse(this)
    } catch (e: Exception) {
        Timber.e(e)
        null
    }
}

fun AppCompatTextView.setHtmlText(message: String) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        this.text = Html.fromHtml(message, Html.FROM_HTML_MODE_COMPACT)
    } else {
        this.text = Html.fromHtml(message)
    }
}

@Suppress("UNCHECKED_CAST")
fun <T> Iterable<Single<T>>.zip() =
    Single.zip(this) { array -> array.map { it as T } }

@Suppress("UNCHECKED_CAST")
fun <T> Iterable<Flowable<T>>.combineLatest() =
    Flowable.combineLatest(this) { array -> array.map { it as T } }


fun List<Category>.filterById(elements: List<Category>): List<Category> {

    if (this.isEmpty()) {
        return emptyList()
    }

    val elemetsById = elements.map { it.id }
    return this.filterNot {
        it.id in elemetsById
    }
}

@SuppressLint("ClickableViewAccessibility")
fun AppCompatTextView.setOnCompoudDrawableStartClickListener(onClick: () -> Unit) {
    val textView = this
    textView.setOnTouchListener(object : View.OnTouchListener {
        override fun onTouch(v: View?, event: MotionEvent): Boolean {
            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX <= textView.totalPaddingLeft) {
                    onClick.invoke()
                    return true
                }
            }
            return true
        }
    })
}

@SuppressLint("ClickableViewAccessibility")
fun AppCompatTextView.setOnCompoudDrawableEndClickListener(onClick: () -> Unit) {
    val textView = this
    textView.setOnTouchListener(object : View.OnTouchListener {
        override fun onTouch(v: View?, event: MotionEvent): Boolean {
            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= textView.right - textView.totalPaddingRight) {
                    onClick.invoke()
                    return true
                }
            }
            return true
        }
    })
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}

fun Context.getColorNameFromArray(index: Int): String {
    val array = resources.getStringArray(R.array.colorNames)
    return array[index]
}

fun Float.getPercentOfTotal(total: Float): Float {
    return (this * 100f) / total
}