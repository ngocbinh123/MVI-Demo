package com.nnbinh.mvidemo.screens.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import com.nnbinh.mvidemo.base.BaseDialogFragment
import com.nnbinh.mvidemo.databinding.DlgDatePickerBinding
import com.nnbinh.mvidemo.extensions.FORMAT_EEE_MMM_DD
import com.nnbinh.mvidemo.extensions.convertTo
import com.nnbinh.mvidemo.extensions.setOnSingleClickListener
import com.nnbinh.mvidemo.screens.signInUp.CreateProfileFragment
import com.nnbinh.mvidemo.screens.signInUp.SignInUpActivity
import kotlinx.android.synthetic.main.dlg_date_picker.btn_cancel
import kotlinx.android.synthetic.main.dlg_date_picker.btn_ok
import kotlinx.android.synthetic.main.dlg_date_picker.calendarView
import kotlinx.android.synthetic.main.dlg_date_picker.tv_date
import kotlinx.android.synthetic.main.dlg_date_picker.tv_year
import java.util.Calendar

class DatePickerDlg : BaseDialogFragment() {

  companion object {
    private const val KEY_YEAR = "KEY_YEAR"
    private const val KEY_MONTH = "KEY_MONTH"
    private const val KEY_DAY = "KEY_DAY"

    fun getNewInstance(year: Int = 1990, month: Int = 1, day: Int = 1): DatePickerDlg {
      return with(DatePickerDlg()) {
        val bundle = Bundle()
        bundle.putInt(KEY_YEAR, year)
        bundle.putInt(KEY_MONTH, month)
        bundle.putInt(KEY_DAY, day)
        this.arguments = bundle
        this
      }
    }
  }

  private lateinit var newCalendar: Calendar

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?): View? {
    val binding = DlgDatePickerBinding.inflate(inflater, container, false)
    binding.lifecycleOwner = this
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    val year = arguments?.getInt(KEY_YEAR, 1990)
    val month = arguments?.getInt(KEY_MONTH, 1)
    val day = arguments?.getInt(KEY_DAY, 1)
    updateOnUI(year, month, day)
    calendarView.date = newCalendar.timeInMillis

    btn_cancel.setOnSingleClickListener { dismiss() }
    btn_ok.setOnSingleClickListener {
      if (activity is SignInUpActivity) {
        val naveHostFragment = activity?.supportFragmentManager?.fragments?.firstOrNull()

        (naveHostFragment as? NavHostFragment)?.childFragmentManager?.fragments
            ?.firstOrNull()?.let { fragment ->
              (fragment as? CreateProfileFragment)?.onUpdateBirthday(newCalendar.time)
            }
      }
      dismiss()
    }
    calendarView.setOnDateChangeListener { _, year, month, day ->
      updateOnUI(year, month, day)
    }
  }

  private fun updateOnUI(year: Int?, month: Int?, day: Int?) {
    newCalendar = Calendar.getInstance()
    if (year != null && month != null && day != null) {
      newCalendar.set(year, month, day)
    }
    tv_year.text = newCalendar.get(Calendar.YEAR).toString()
    tv_date.text = newCalendar.time.convertTo(FORMAT_EEE_MMM_DD)
  }
}