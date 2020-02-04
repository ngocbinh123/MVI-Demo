package com.nnbinh.mvidemo.data

import com.tumblr.remember.Remember

object CurrentUser {
  fun isSignedIn() = Remember.getString(Constant.KEY_USER_TOKEN, "").isNotEmpty()
}