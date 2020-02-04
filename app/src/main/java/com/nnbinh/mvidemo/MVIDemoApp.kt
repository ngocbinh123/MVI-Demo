package com.nnbinh.mvidemo

import android.app.Application
import com.tumblr.remember.Remember

class MVIDemoApp: Application() {

  override fun onCreate() {
    super.onCreate()
    Remember.init(applicationContext, "com.nnbinh.mvidemo")
  }
}