package com.nnbinh.mvidemo.extensions

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

const val FORMAT_EEE_MMM_DD = "EEE, MMM dd"
const val FORMAT_DATE_FOR_LOCAL = "dd/MM/yyyy"
const val FORMAT_FULL_REMOTE_DATE = "yyyy-MM-dd HH:mm:ss"
fun Date.convertTo(patternFormat: String): String {
  val sdf = SimpleDateFormat(patternFormat, Locale.US)
  return sdf.format(this)
}

fun Date.getDateStr(patternFormat: String = FORMAT_DATE_FOR_LOCAL): String = this.convertTo(
    patternFormat)