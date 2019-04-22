package com.application.flickr.util.extensions

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

/**
 * Created by Harsh Jain on 21/04/19.
 */

fun View.hideKeyboard() {
    val imm = context.getSystemService(
        Context
            .INPUT_METHOD_SERVICE
    ) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}