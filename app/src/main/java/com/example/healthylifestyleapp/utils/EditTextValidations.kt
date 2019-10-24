package com.example.healthylifestyleapp.utils


import android.text.Editable
import android.text.InputFilter
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import android.widget.EditText
import androidx.annotation.StringRes
import com.google.android.material.textfield.TextInputLayout

/**
 * file contains validation extension methods
 */
object EditTextValidations {
    const val MIN_NAME_LENGTH = 3
    const val MAX_NAME_LENGTH = 20
}


fun EditText.applyMobileNumberFilter() {
    val blockCharacterSet = "~#^|$%&*!)(/N,;+.-"
    val filter = InputFilter { source, start, end, dest, dstart, dend ->
        if (source != null && blockCharacterSet.contains("" + source)) {
            ""
        } else null
    }
    val lengthFilter = InputFilter.LengthFilter(10)
    filters = arrayOf(filter, lengthFilter)
}

fun EditText.isValidEmail(): Boolean {
    val txt = text.trim()
    return !TextUtils.isEmpty(txt) && txt.matches(Patterns.EMAIL_ADDRESS.toRegex())
}

fun EditText.isEmpty(): Boolean {
    return TextUtils.isEmpty(text.trim())
}

fun EditText.isValidCompositeField(): Boolean {
    return isValidEmail() || isValidMobileNumber()
}

fun EditText.isNotEmpty(): Boolean {
    return !TextUtils.isEmpty(text.trim())
}

fun EditText.isValidPostalCode(): Boolean {
    val txt = text.trim()
    return !TextUtils.isEmpty(txt) && txt.matches(Regex.POSTAL_CODE.toRegex())
}

fun EditText.isValidMobileNumber(): Boolean {
    val txt = text.trim()
    return !TextUtils.isEmpty(txt) && txt.matches(Regex.PHONE_NUMBER.toRegex())
}

fun EditText.isInRange(
    min: Int = EditTextValidations.MIN_NAME_LENGTH,
    max: Int = EditTextValidations.MAX_NAME_LENGTH
): Boolean {
    return !isEmpty() && text.length <= max && text.length >= min
}

fun EditText.containsValidCharacters(): Boolean {
    return !isEmpty() && text.matches(
        Regex
            .ALPHABETIC.toRegex()
    )
}

fun EditText.isValidPassword(): Boolean {
    val txt = text.trim()
    return !isEmpty() && txt.matches(Regex.PASSWORD.toRegex())
}

fun EditText.isSamePassword(et: EditText): Boolean {
    return text.toString() == et.text.toString()
}

fun EditText.onTextChanged(cb: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            cb(s.toString())
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    })
}

fun EditText.onFocusChanged(cb: (view: View, hasFocus: Boolean) -> Unit) {
    this.setOnFocusChangeListener { v, hasFocus ->
        cb(v, hasFocus)
    }
}

fun TextInputLayout.onFocusChanged(cb: (view: View, hasFocus: Boolean) -> Unit) {
    this.setOnFocusChangeListener { v, hasFocus ->
        cb(v, hasFocus)
    }
}


fun TextInputLayout.showError(@StringRes id: Int) {
    isErrorEnabled = true
    error = context.getString(id)
}

fun TextInputLayout.helperText(@StringRes id: Int) {
    helperText = context.getString(id)
}

fun TextInputLayout.hideError() {
    error = ""
    isErrorEnabled = false
}

fun TextInputLayout.value(): String {
    return editText?.text.toString()
}

fun EditText.clear() {
    setText("")
}
