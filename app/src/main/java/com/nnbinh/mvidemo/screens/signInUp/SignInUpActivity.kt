package com.nnbinh.mvidemo.screens.signInUp

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.nnbinh.mvidemo.R
import com.nnbinh.mvidemo.base.BaseActivity
import com.nnbinh.mvidemo.base.BaseActivityVM
import java.util.Date

class SignInUpActivity : BaseActivity() {
  private lateinit var navController: NavController
  override fun initViewModel(): BaseActivityVM {
    return ViewModelProviders.of(this).get(SignInUpVM::class.java)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_sign_in_up)
    navController = Navigation.findNavController(this, R.id.nav_fragment)
  }

  fun navigateTo(resId: Int) = navController.navigate(resId)
}