package com.example.healthylifestyleapp.utils

import android.graphics.Paint
import android.os.Build
import android.text.Html
import android.widget.TextView

fun TextView.loadHtmlText(htmlText: String?) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        text = Html.fromHtml(htmlText, Html.FROM_HTML_MODE_COMPACT)
    } else {
        @Suppress("DEPRECATION")
        text = Html.fromHtml(htmlText)
    }
}

fun TextView.strikeThrough() {
    paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
}