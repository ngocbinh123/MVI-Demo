package com.nnbinh.mvidemo.extensions

import android.widget.TextView
import androidx.annotation.ColorInt
import com.google.android.material.snackbar.Snackbar

fun Snackbar.withColor(@ColorInt backgroundColor: Int, @ColorInt textColor: Int): Snackbar {
  this.view.setBackgroundColor(backgroundColor)
  this.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text).setTextColor(textColor)
  return this
}