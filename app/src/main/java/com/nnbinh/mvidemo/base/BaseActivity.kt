package com.nnbinh.mvidemo.base

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.nnbinh.mvidemo.MainActivity
import com.nnbinh.mvidemo.R
import com.nnbinh.mvidemo.event.Command
import com.nnbinh.mvidemo.extensions.withColor
import com.nnbinh.mvidemo.screens.signInUp.SignInUpActivity

abstract class BaseActivity : AppCompatActivity() {
  val baseVM by lazy(LazyThreadSafetyMode.SYNCHRONIZED) { initViewModel() }
  protected abstract fun initViewModel(): BaseActivityVM

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    baseVM.command.observe(this, Observer { cmd -> processCommand(cmd) })
  }

  open fun processCommand(command: Command) {
    when (command) {
      is Command.Snack -> showSnack(command)
      is Command.SignInSuccess -> navigateToMainScreen()
      is Command.Logout -> navigateToSigInScreen()
    }
  }

  fun navigateToMainScreen() {
    val intent = Intent(this, MainActivity::class.java)
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    startActivity(intent)
  }

  fun navigateToSigInScreen() {
    val intent = Intent(this, SignInUpActivity::class.java)
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    startActivity(intent)
  }

  private fun showSnack(command: Command.Snack) {

    val rootView = window.decorView.rootView

    val bgColor = if (command.isSucceed)
      ContextCompat.getColor(this, R.color.colorSuccess)
    else
      ContextCompat.getColor(this, R.color.colorFail)

    val txtColor = ContextCompat.getColor(this, R.color.colorCMDText)

    if (command.message != null)
      Snackbar.make(rootView!!, command.message, 5000)
          .withColor(bgColor, txtColor).show()
    else if (command.resId != null)
      Snackbar.make(rootView!!, command.resId, 5000)
          .withColor(bgColor, txtColor).show()
  }
}