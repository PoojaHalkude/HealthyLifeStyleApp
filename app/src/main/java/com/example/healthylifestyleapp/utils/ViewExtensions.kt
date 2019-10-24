package com.example.healthylifestyleapp.utils

import android.view.View

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.disable() {
    isEnabled = false
}

fun View.enable() {
    isEnabled = true
}