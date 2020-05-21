package com.nnbinh.mvidemo.screens.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.nnbinh.mvidemo.base.BaseFragment
import com.nnbinh.mvidemo.databinding.FragmentDashboardBinding

class DashboardFragment : BaseFragment() {
  override fun initViewModel(): ViewModel {
    return ViewModelProviders.of(this).get(DashboardVM::class.java)
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?): View? {
    val binding = FragmentDashboardBinding.inflate(inflater, container, false)
    binding.lifecycleOwner = this
    return binding.root
  }
}