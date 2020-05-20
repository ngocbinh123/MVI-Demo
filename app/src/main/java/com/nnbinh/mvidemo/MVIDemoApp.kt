package com.nnbinh.mvidemo

import android.app.Application
import com.google.firebase.FirebaseApp
import com.google.firebase.database.FirebaseDatabase
import com.tumblr.remember.Remember

class MVIDemoApp: Application() {

  override fun onCreate() {
    super.onCreate()
    Remember.init(applicationContext, "com.nnbinh.mvidemo")
    FirebaseApp.initializeApp(this)
//    FirebaseDatabase.getInstance().setPersistenceEnabled(true)
  }
}