package com.nnbinh.mvidemo.screens.splash

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nnbinh.mvidemo.data.CurrentUser
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class SplashVM : ViewModel() {
  val rxDispose = CompositeDisposable()
  val authorizationState = MutableLiveData<AuthorizationState>()
  val loadingPercent = MutableLiveData<Int>()
  init {
      loadingPercent.value = 0
  }

  override fun onCleared() {
    rxDispose.clear()
    super.onCleared()
  }

  @SuppressLint("CheckResult")
  fun startLoadingProcess() {
    val dispose = Observable
        .create<Int> { emitter ->
          while (loadingPercent.value!! < 100) {
            val newPercent = loadingPercent.value!! + 2
            emitter.onNext(newPercent)
            Thread.sleep(100)
          }
          emitter.onComplete()
        }
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
            { newPercent -> loadingPercent.value = newPercent },
            { error -> },
            {
              loadingPercent.value = 100
              authorizationState.value = AuthorizationState(false, CurrentUser.isSignedIn())
            })
    rxDispose.add(dispose)

  }
}