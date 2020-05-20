package com.nnbinh.mvidemo.base

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel

abstract class BaseFragment : Fragment() {
  val baseVM by lazy(LazyThreadSafetyMode.SYNCHRONIZED) { initViewModel() }
  protected abstract fun initViewModel() : ViewModel
}