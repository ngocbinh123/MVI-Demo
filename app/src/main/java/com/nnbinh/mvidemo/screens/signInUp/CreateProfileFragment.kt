package com.nnbinh.mvidemo.screens.signInUp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.nnbinh.mvidemo.base.BaseFragment
import com.nnbinh.mvidemo.data.Gender
import com.nnbinh.mvidemo.data.tables.UserProfile
import com.nnbinh.mvidemo.databinding.FragmentCreateProfileBinding
import com.nnbinh.mvidemo.extensions.FORMAT_DATE_FOR_LOCAL
import com.nnbinh.mvidemo.extensions.FORMAT_FULL_REMOTE_DATE
import com.nnbinh.mvidemo.extensions.convertTo
import com.nnbinh.mvidemo.extensions.getDateStr
import com.nnbinh.mvidemo.extensions.getDay
import com.nnbinh.mvidemo.extensions.getMonth
import com.nnbinh.mvidemo.extensions.getYear
import com.nnbinh.mvidemo.extensions.setOnSingleClickListener
import com.nnbinh.mvidemo.screens.dialogs.DatePickerDlg
import kotlinx.android.synthetic.main.fragment_create_profile.*
import java.util.Calendar
import java.util.Date

class CreateProfileFragment : BaseFragment() {
  override fun initViewModel(): ViewModel {
    return ViewModelProviders.of(activity!!).get(SignInUpVM::class.java)
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?): View? {
    val binding = FragmentCreateProfileBinding.inflate(inflater, container, false)
    binding.lifecycleOwner = this
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    tv_birthday.setOnSingleClickListener {
//      if (VERSION.SDK_INT >= VERSION_CODES.N) {
//        val todayCal = Calendar.getInstance()
//        DatePickerDialog(context!!, DatePickerDialog.OnDateSetListener { picker, year, month, day ->
//          tv_birthday.text = "$day/$month/$year"
//        }, todayCal.getYear() - 18, todayCal.getMonth(), todayCal.getDay()).show()
//      }

      // Triple (Year, Month, Day)
      val birthDayArr: Triple<Int, Int, Int> = if (tv_birthday.text.isNullOrEmpty()) {
        val todayCal = Calendar.getInstance()
        Triple(todayCal.getYear() - 18, todayCal.getMonth(), todayCal.getDay())

      } else {
        val birthdayArr = tv_birthday.text.toString().split("/")
        Triple(birthdayArr[2].toInt(), birthdayArr[1].toInt(), birthdayArr[0].toInt())
      }

      DatePickerDlg.getNewInstance(birthDayArr.first, birthDayArr.second, birthDayArr.third)
          .show(fragmentManager, DatePickerDlg::class.java.name)
    }
    btn_save.setOnClickListener {
      val profile = UserProfile(
          fullName = edt_full_name.text.trim().toString(),
          phoneNumber = edt_phone_number.text.trim().toString(),
          birthday = tv_birthday.text.trim().toString(),
          address = edt_address.text.trim().toString(),
          gender = if (rad_male.isChecked) Gender.Male.name else Gender.Female.name,
          updatedAt = Date().getDateStr(FORMAT_FULL_REMOTE_DATE)
      )

      (baseVM as SignInUpVM).onSaveProfile(profile)
    }
  }

  fun onUpdateBirthday(date: Date) {
    val birthdayStr = date.convertTo(FORMAT_DATE_FOR_LOCAL)
    tv_birthday.text = birthdayStr
  }
}