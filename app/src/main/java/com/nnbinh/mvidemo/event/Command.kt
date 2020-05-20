package com.nnbinh.mvidemo.event

sealed class Command {
  class Snack(val message: String? = null, val resId: Int? = null, val isSucceed: Boolean = false) : Command()
  class SignInSuccess : Command()
  class Logout : Command()
}