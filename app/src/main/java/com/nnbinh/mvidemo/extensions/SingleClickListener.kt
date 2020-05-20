package com.nnbinh.mvidemo.extensions

import android.os.SystemClock
import android.view.View

abstract class SingleClickListener(private val minimumInterval: Long = 1000L) :
    View.OnClickListener {
    private var prevClickedTime: Long = 0

    override fun onClick(v: View?) {
        val currentClickedTime = SystemClock.uptimeMillis()
        if (currentClickedTime - prevClickedTime >= minimumInterval) {
            onSingleClick(v)
        }
        prevClickedTime = currentClickedTime
    }

    abstract fun onSingleClick(v: View?)
}