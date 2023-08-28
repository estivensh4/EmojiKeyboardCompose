package com.estiven.emojikeyboardcompose

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.res.Configuration
import android.graphics.Rect
import android.util.TypedValue
import android.view.inputmethod.InputMethodManager
import kotlin.math.roundToInt

fun Context.findActivity(): Activity {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) return context
        context = context.baseContext
    }
    throw IllegalStateException("no activity")
}

fun Float.toPx(context: Context): Int {
    return (TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, this,
        context.resources.displayMetrics
    ) + 0.5f).roundToInt()
}

fun Activity.getProperWidth(): Int {
    val rect = windowVisibleDisplayFrame(this)
    return if (getOrientation() == Configuration.ORIENTATION_PORTRAIT) rect.right else getScreenWidth()
}

private fun Activity.getScreenWidth(): Int {
    return resources.configuration.screenWidthDp.toFloat().toPx(this)
}

fun Context.getOrientation() : Int = this.resources.configuration.orientation

private fun windowVisibleDisplayFrame(context: Activity): Rect {
    val result = Rect()
    context.window.decorView.getWindowVisibleDisplayFrame(result)
    return result
}

internal inline val Context.inputMethodManager get() = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

internal fun Activity.getProperHeight(): Int {
    return windowVisibleDisplayFrame(this).bottom
}