package com.nnbinh.mvidemo.screens.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.nnbinh.mvidemo.base.BaseFragment
import com.nnbinh.mvidemo.databinding.FragmentNotificationsBinding

class NotificationsFragment : BaseFragment() {
  override fun initViewModel(): ViewModel {
    return ViewModelProviders.of(this).get(NotificationsVM::class.java)
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?): View? {
    val binding = FragmentNotificationsBinding.inflate(inflater, container, false)
    binding.lifecycleOwner = this
    return binding.root
  }
}