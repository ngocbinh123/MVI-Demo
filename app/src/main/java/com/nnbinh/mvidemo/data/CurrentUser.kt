package com.nnbinh.mvidemo.data

import com.google.firebase.auth.FirebaseAuth
import com.tumblr.remember.Remember

object CurrentUser {
  fun isSignedIn(): Boolean {
    val remoteId = Remember.getString(Constant.KEY_REMOTE_USER_ID, "")
    if (remoteId.isNotEmpty() || FirebaseAuth.getInstance().currentUser != null) {
      return true
    }
    return false
  }

  fun saveInfo(displayName: String, remoteId: String) {
    Remember.putString(Constant.KEY_USER_DISPLAY_NAME, displayName)
    Remember.putString(Constant.KEY_REMOTE_USER_ID, remoteId)
  }

  fun clearInfo() {
    FirebaseAuth.getInstance().signOut()
    Remember.clear()
  }
}