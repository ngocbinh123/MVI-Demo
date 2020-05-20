package com.nnbinh.mvidemo.extensions

import java.util.Calendar

fun Calendar.getYear(): Int = this.get(Calendar.YEAR)
fun Calendar.getMonth(): Int = this.get(Calendar.MONTH) + 1
fun Calendar.getDay(): Int = this.get(Calendar.DATE)