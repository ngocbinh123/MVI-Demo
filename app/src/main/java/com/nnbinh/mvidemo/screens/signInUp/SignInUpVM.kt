package com.nnbinh.mvidemo.screens.signInUp

import android.content.Intent
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.nnbinh.mvidemo.R
import com.nnbinh.mvidemo.base.BaseActivityVM
import com.nnbinh.mvidemo.data.CurrentUser
import com.nnbinh.mvidemo.data.DBCollection
import com.nnbinh.mvidemo.data.tables.UserProfile
import com.nnbinh.mvidemo.event.Command
import com.nnbinh.mvidemo.extensions.FORMAT_FULL_REMOTE_DATE
import com.nnbinh.mvidemo.extensions.getDateStr
import com.nnbinh.mvidemo.extensions.hashText
import com.nnbinh.mvidemo.extensions.isEmail
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*
import kotlin.collections.HashMap

class SignInUpVM : BaseActivityVM() {
  var isProgressing: Boolean = false
  private val composite: CompositeDisposable by lazy { CompositeDisposable() }
  private val db: FirebaseDatabase by lazy { FirebaseDatabase.getInstance() }
  var userProfile = MutableLiveData<UserProfile>().apply { value = null }

  fun onClickSignInButton(email: String, password: String) {
    if (!email.isEmail()) {
      command.value = Command.Snack(resId = R.string.your_email_is_incorrect)
      return
    }

    if (password.isEmpty()) {
      command.value = Command.Snack(resId = R.string.pls_fill_password)
      return
    }

    db.reference.child(DBCollection.COLL_USER)
        .addListenerForSingleValueEvent(object : ValueEventListener {
          override fun onCancelled(error: DatabaseError) {
            command.value = Command.Snack(message = error.message)
          }

          override fun onDataChange(snapshot: DataSnapshot) {
            if (snapshot.value == null) {
              command.value = Command.Snack(resId = R.string.your_email_not_register)
              return
            }
            val userMaps = snapshot.value as Map<String, HashMap<String, String>>
            for (entry in userMaps.entries) {
              if (email.equals(entry.value["email"], true)) {
                val hashedPass = password.hashText()
                if (hashedPass == entry.value["password"]) {
                  val id = entry.value["id"]!!
                  val fullName = entry.value["fullName"] ?: ""
                  CurrentUser.saveInfo(fullName, id)
                  command.value = Command.SignInSuccess()
                  return
                }
                command.value = Command.Snack(resId = R.string.password_incorrect)
                return
              }
            }

            command.value = Command.Snack(resId = R.string.your_email_not_register)
          }
        })
  }

  fun firebaseAuthWithGoogle(data: Intent?) {
    if (isProgressing) {
      return
    }
    isProgressing = true

    val task = GoogleSignIn.getSignedInAccountFromIntent(data)
    try {
      val account = task.getResult(ApiException::class.java)
      val credential = GoogleAuthProvider.getCredential(account?.idToken, null)
      FirebaseAuth.getInstance().signInWithCredential(credential)
          .addOnCompleteListener { authTask ->
            if (authTask.isSuccessful) {
              authTask.result?.user?.let { firebaseUser ->
                CurrentUser.saveInfo(firebaseUser.displayName ?: "Guest", firebaseUser.uid)
              }
              command.value = Command.SignInSuccess()
            } else {
              command.value = Command.Snack(resId = R.string.sign_in_fail)
            }
          }
    }
    catch (e: ApiException) {
      command.value = Command.Snack(message = e.message)
    }
    finally {
      isProgressing = false
    }
  }
  fun onClickSignUpButton(email: String, password: String, confirmPass: String) {
    if (!email.isEmail()) {
      command.value = Command.Snack(resId = R.string.your_email_is_incorrect)
      return
    }

    if (password.isEmpty()) {
      command.value = Command.Snack(resId = R.string.pls_fill_password)
      return
    }

    if (password != confirmPass) {
      command.value = Command.Snack(resId = R.string.confirm_pass_is_not_same_as_pass)
      return
    }

    db.reference.child(DBCollection.COLL_USER)
        .addListenerForSingleValueEvent(object : ValueEventListener {
          override fun onCancelled(error: DatabaseError) {
            command.value = Command.Snack(message = error.message)
          }

          override fun onDataChange(snapshot: DataSnapshot) {
            startSignUp(email, password, snapshot.value as? Map<String, HashMap<String, String>>)
          }
        })
  }

  private fun startSignUp(email: String, password: String,
      userMaps: Map<String, HashMap<String, String>>?) {
    Observable
        .fromCallable {
          if (userMaps != null) {
            for (entry in userMaps.entries) {
              if (email.equals(entry.value["email"], true)) {
                throw Throwable("exist")
              }
            }
          }

//          create node
          val uniqueKey = db.getReference(DBCollection.COLL_USER).push().key
          if (uniqueKey.isNullOrEmpty()) {
            throw Throwable("uniqueKey")
          }
          return@fromCallable UserProfile(
              id = uniqueKey,
              email = email,
              password = password.hashText(),
              createdAt = Date().getDateStr(FORMAT_FULL_REMOTE_DATE)
          )
        }
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({ profile ->
          db.getReference("${DBCollection.COLL_USER}/${profile.id}")
              .setValue(profile) { error, ref ->
                if (error != null) {
                  command.value = Command.Snack(error.message)
                  return@setValue
                }
                CurrentUser.saveInfo("", profile.id)
                userProfile.value = profile
              }
        }, { t ->
          command.value = when (t.message) {
            "exist" -> {
              Command.Snack(resId = R.string.this_email_has_been_used)
            }
            "uniqueKey" -> {
              Command.Snack(resId = R.string.could_not_create_unique_node)
            }
            else -> {
              Command.Snack(message = t.message)
            }
          }
        }).let { disposable -> composite.add(disposable) }
  }

  fun onSaveProfile(newProfile: UserProfile) {
    if (newProfile.fullName.isEmpty()) {
      command.value = Command.Snack(resId = R.string.pls_fill_full_name)
      return
    }

    userProfile.value?.let { userProfile ->
      newProfile.id = userProfile.id
      newProfile.email = userProfile.email
      newProfile.password = userProfile.password
      newProfile.createdAt = userProfile.createdAt
    }

    db.getReference("${DBCollection.COLL_USER}/${newProfile.id}")
        .setValue(newProfile) { error, _ ->
          if (error != null) {
            command.value = Command.Snack(message = error.message)
            return@setValue
          }

          command.value = Command.SignInSuccess()
        }
  }
}