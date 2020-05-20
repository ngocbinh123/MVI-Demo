package com.nnbinh.mvidemo.base

import androidx.lifecycle.ViewModel
import com.nnbinh.mvidemo.event.Command
import com.nnbinh.mvidemo.event.SingleLiveEvent
import io.reactivex.disposables.CompositeDisposable

abstract class BaseActivityVM: ViewModel() {
  val command: SingleLiveEvent<Command> = SingleLiveEvent() //has only 1 observer
  val rxDispose = CompositeDisposable()

  override fun onCleared() {
    rxDispose.clear()
    super.onCleared()
  }
}