package com.nnbinh.mvidemo.screens.signInUp

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.nnbinh.mvidemo.R
import com.nnbinh.mvidemo.base.BaseFragment
import com.nnbinh.mvidemo.data.Constant
import com.nnbinh.mvidemo.databinding.FragmentSignInBinding
import com.nnbinh.mvidemo.extensions.setOnSingleClickListener
import kotlinx.android.synthetic.main.fragment_sign_in.btn_signin
import kotlinx.android.synthetic.main.fragment_sign_in.btn_signin_gg
import kotlinx.android.synthetic.main.fragment_sign_in.edt_email
import kotlinx.android.synthetic.main.fragment_sign_in.edt_password
import kotlinx.android.synthetic.main.fragment_sign_in.txt_sign_up

class SignInFragment : BaseFragment() {
  private lateinit var googleSignInClient: GoogleSignInClient

  override fun initViewModel(): ViewModel {
    return ViewModelProviders.of(activity!!).get(SignInUpVM::class.java)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(getString(R.string.default_web_client_id))
        .requestEmail()
        .build()

    googleSignInClient = GoogleSignIn.getClient(activity!!, gso)
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?): View? {
    val binding = FragmentSignInBinding.inflate(inflater, container, false)
    binding.lifecycleOwner = this
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    btn_signin.setOnSingleClickListener {
      (baseVM as SignInUpVM).onClickSignInButton(edt_email.text.toString().trim(),
          edt_password.text.toString().trim())
    }

    btn_signin_gg.setOnSingleClickListener {
      val signInIntent = googleSignInClient.signInIntent
      startActivityForResult(signInIntent, Constant.RC_SIGN_IN_BY_GG)
    }
    txt_sign_up.setOnSingleClickListener { navigateToNextScreen() }
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    when (requestCode) {
      Constant.RC_SIGN_IN_BY_GG -> (baseVM as SignInUpVM).firebaseAuthWithGoogle(data)
      else -> super.onActivityResult(requestCode, resultCode, data)
    }
  }

  private fun navigateToNextScreen() {
    (activity as? SignInUpActivity)?.navigateTo(R.id.action_fragment_sign_in_to_fragment_sign_up)
  }
}