package com.nnbinh.mvidemo.extensions

import android.view.View

fun View.setOnSingleClickListener(onClicked: () -> Unit) {
  this.setOnClickListener(object : SingleClickListener() {
    override fun onSingleClick(v: View?) {
      onClicked()
    }
  })
}