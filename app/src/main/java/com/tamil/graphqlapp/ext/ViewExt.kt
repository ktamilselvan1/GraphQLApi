package com.tamil.graphqlapp.ext

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun View.showAToast(content: String) {
    Snackbar.make(this, content, Snackbar.LENGTH_LONG).show()
}

fun View.showAToast(content: String, onUndoClicked: () -> Unit) {
    Snackbar.make(this, content, Snackbar.LENGTH_LONG)
        .setAction("Undo") {
            onUndoClicked()
        }.show()
}