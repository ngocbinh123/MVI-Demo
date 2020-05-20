package com.nnbinh.mvidemo.screens.splash

import androidx.lifecycle.MutableLiveData
import com.nnbinh.mvidemo.base.BaseActivityVM
import com.nnbinh.mvidemo.data.CurrentUser
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class SplashVM : BaseActivityVM() {
  val authorizationState = MutableLiveData<AuthorizationState>()
  val loadingPercent = MutableLiveData<Int>()

  init {
    loadingPercent.value = 0
  }

  fun startLoadingProcess() {
    Observable
        .create<Int> { emitter ->
          while (loadingPercent.value!! < 100) {
            val newPercent = loadingPercent.value!! + 5
            emitter.onNext(newPercent)
            Thread.sleep(50)
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
              authorizationState.value = AuthorizationState(isChecking = false, isSignedIn =  CurrentUser.isSignedIn())
            }).let {
          rxDispose.add(it)
        }
  }
}