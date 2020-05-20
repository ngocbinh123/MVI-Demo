package com.nnbinh.mvidemo.screens.signInUp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.nnbinh.mvidemo.R
import com.nnbinh.mvidemo.base.BaseFragment
import com.nnbinh.mvidemo.databinding.FragmentSignUpBinding
import kotlinx.android.synthetic.main.fragment_sign_up.*

class SignUpFragment : BaseFragment() {
  override fun initViewModel(): ViewModel {
    return ViewModelProviders.of(activity!!).get(SignInUpVM::class.java)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    (baseVM as? SignInUpVM)?.userProfile?.observe(this, Observer { profile ->
      if (profile != null && profile.email.isNotEmpty()) {
        navigateToNextScreen()
      }
    })
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?): View? {
    val binding = FragmentSignUpBinding.inflate(inflater, container, false)
    binding.lifecycleOwner = this
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    btn_sign_up.setOnClickListener {
      (baseVM as? SignInUpVM)?.onClickSignUpButton(edt_email.text?.trim().toString(),
        edt_password.text?.trim().toString(),
        edt_confirm_password.text?.trim().toString())
    }
  }

  private fun navigateToNextScreen() {
    (activity as? SignInUpActivity)?.navigateTo(R.id.action_fragment_sign_up_to_fragment_create_profile)
  }
}