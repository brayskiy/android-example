package com.brayskiy.example.core.ui

import android.content.Context
import android.widget.Toast
import android.view.Gravity
import android.graphics.Color
import android.widget.TextView
import android.widget.LinearLayout

/**
 * Created by brayskiy on 02/01/19.
 */

object ToastMessage {

    interface Callback {
        fun run()
    }

    fun show(context: Context, text: String, backgroundResource: Int, callback: Callback?) {
        val displayMetrics = context.applicationContext.resources.displayMetrics

        val tv = TextView(context)
        val layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        val hMargin = (32 * displayMetrics.density).toInt()
        val vMargin = (8 * displayMetrics.density).toInt()
        layoutParams.setMargins(hMargin, vMargin, hMargin, vMargin)
        tv.layoutParams = layoutParams
        tv.setTextColor(Color.WHITE)
        tv.gravity = Gravity.CENTER_VERTICAL
        tv.text = text

        val layout = LinearLayout(context)
        layout.setBackgroundResource(backgroundResource)
        layout.addView(tv)

        val toast = Toast(context)
        toast.view = layout

        val yPos = displayMetrics.heightPixels / 5
        toast.setGravity(Gravity.BOTTOM, 0, yPos)

        toast.show()
        callback?.run()
    }

    fun show(context: Context, resId: Int, backgroundResource: Int, callback: Callback?) {
        show(
            context,
            context.resources.getText(resId).toString(),
            backgroundResource,
            callback
        )
    }

    fun show(context: Context, resId: Int, callback: Callback?) {
        show(context, resId)
        callback?.run()
    }

    fun show(context: Context, resId: Int) {
        Toast.makeText(context, resId, Toast.LENGTH_LONG).show()
    }
}
