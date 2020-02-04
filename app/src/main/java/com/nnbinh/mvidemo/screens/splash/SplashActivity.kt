package com.nnbinh.mvidemo.screens.splash

import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.nnbinh.mvidemo.MainActivity
import com.nnbinh.mvidemo.R
import com.nnbinh.mvidemo.databinding.ActivitySplashBinding
import com.nnbinh.mvidemo.screens.BaseActivity
import com.nnbinh.mvidemo.screens.signin.SignInActivity

class SplashActivity : BaseActivity() {
  private val viewModel: SplashVM by lazy {
    ViewModelProviders.of(this).get(SplashVM::class.java)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val binding: ActivitySplashBinding = DataBindingUtil.setContentView(this,
        R.layout.activity_splash)
    binding.viewmodel = viewModel
    binding.lifecycleOwner = this

    viewModel.authorizationState.observe(this, Observer { state ->
      if (state.isChecking) {
        return@Observer
      }

      gotoNextScreen(state)
    })
  }

  override fun onResume() {
    super.onResume()
    viewModel.startLoadingProcess()
  }

  fun gotoNextScreen(state: AuthorizationState) {
    val intent = Intent(this, if (state.isSignedIn) MainActivity::class.java else SignInActivity::class.java)
    this.startActivity(intent)
    this.finish()
  }
}