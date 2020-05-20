package com.nnbinh.mvidemo.base

import android.util.DisplayMetrics
import android.view.ViewGroup
import android.view.WindowManager.LayoutParams
import androidx.fragment.app.DialogFragment

abstract class BaseDialogFragment : DialogFragment () {

  override fun onResume() {
    super.onResume()
    val defaultDisplay = activity?.windowManager?.defaultDisplay
    if (defaultDisplay != null &&  dialog.window != null) {
      val displayMetrics = DisplayMetrics()
      defaultDisplay.getMetrics(displayMetrics)
      val params: ViewGroup.LayoutParams = dialog.window!!.attributes
      val dlgWidth = displayMetrics.widthPixels * 0.9
      params.width = dlgWidth.toInt()
      params.height = LayoutParams.WRAP_CONTENT
      dialog.window!!.attributes = params as LayoutParams
    }
  }
}