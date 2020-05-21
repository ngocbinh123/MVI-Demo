package com.nnbinh.mvidemo.screens.splash

import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.nnbinh.mvidemo.screens.main.MainActivity
import com.nnbinh.mvidemo.R
import com.nnbinh.mvidemo.databinding.ActivitySplashBinding
import com.nnbinh.mvidemo.base.BaseActivity
import com.nnbinh.mvidemo.base.BaseActivityVM
import com.nnbinh.mvidemo.screens.signInUp.SignInUpActivity

class SplashActivity : BaseActivity() {

  override fun initViewModel(): BaseActivityVM {
    return ViewModelProviders.of(this).get(SplashVM::class.java)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val binding: ActivitySplashBinding = DataBindingUtil.setContentView(this,
        R.layout.activity_splash)
    binding.viewmodel = baseVM as SplashVM
    binding.lifecycleOwner = this

    (baseVM as SplashVM).authorizationState.observe(this, Observer { state ->
      if (state.isChecking) {
        return@Observer
      }
      gotoNextScreen(state)

    })
  }

  override fun onResume() {
    super.onResume()
    (baseVM as SplashVM).startLoadingProcess()
  }

  private fun gotoNextScreen(state: AuthorizationState) {
    val intent = Intent(this, if (state.isSignedIn) MainActivity::class.java else SignInUpActivity::class.java)
    this.startActivity(intent)
    this.finish()
  }
}