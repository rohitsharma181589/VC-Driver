package com.vehiclecare.vc_driver_arvind.activity

import android.app.DatePickerDialog
import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.vehiclecare.vc_driver_arvind.R
import com.vehiclecare.vc_driver_arvind.databinding.RegistrationActivityBinding
import com.vehiclecare.vc_driver_arvind.utils.AppSharedPreference
import com.vehiclecare.vc_driver_arvind.viewmodels.RegistrationViewModel

class RegistrationActivity : BaseActivity() {


    private lateinit var registrationActivityBinding: RegistrationActivityBinding
    private lateinit var registrationViewModel: RegistrationViewModel

    var mYear = 0
    var mMonth: Int = 0
    var mDay: Int = 0
    var mHour: Int = 0
    var mMinute: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        registrationActivityBinding =
            DataBindingUtil.setContentView(this, R.layout.registration_activity)
        val model: RegistrationViewModel by viewModels {
            viewModelFactory
        }

        registrationActivityBinding.lifecycleOwner = this
        registrationActivityBinding.setVariable(
            com.vehiclecare.vc_driver_arvind.BR.registrationVariable,
            model
        )
        registrationViewModel = model

    }

    override fun onStart() {
        super.onStart()

        registrationViewModel.phone.postValue(AppSharedPreference.getStringValue(AppSharedPreference.USER_PHONE))

        registrationViewModel.driverApiResponseMutable.observe(this, {


            AppSharedPreference.saveBooleanValue(
                AppSharedPreference.IS_USER_LOGGED_IN,
                true
            )

            AppSharedPreference.saveStringValue(
                AppSharedPreference.USER_ID,
                it.driverData.user.userId
            )
            AppSharedPreference.saveStringValue(
                AppSharedPreference.USER_TOKEN,
                it.driverData.user.token
            )
            AppSharedPreference.saveStringValue(
                AppSharedPreference.USER_Name,
                it.driverData.user.fullName
            )
            AppSharedPreference.saveStringValue(
                AppSharedPreference.USER_EMail,
                it.driverData.user.email
            )


            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)

        })

        registrationViewModel.firstNameError.observe(this, {
            registrationActivityBinding.tilFame.error = it
        })
        registrationViewModel.emailError.observe(this, {
            registrationActivityBinding.tilEmail.error = it
        })
        registrationViewModel.addressError.observe(this, {
            registrationActivityBinding.tiladdress.error = it
        })
        registrationViewModel.stateError.observe(this, {
            registrationActivityBinding.tilstate.error = it
        })
        registrationViewModel.cityError.observe(this, {
            registrationActivityBinding.tilCity.error = it
        })
        registrationViewModel.pinCodeError.observe(this, {
            registrationActivityBinding.tilPincode.error = it
        })
        registrationViewModel.licenceNoError.observe(this, {
            registrationActivityBinding.tilLicenceNo.error = it
        })
        registrationViewModel.aadharNoError.observe(this, {
            registrationActivityBinding.tilAadhar.error = it
        })

        registrationViewModel.genderError.observe(this, {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })



        registrationActivityBinding.rgGender.setOnCheckedChangeListener { group, checkedId ->
            val rb = group?.findViewById(checkedId) as RadioButton
            if (checkedId > -1) {
//                Toast.makeText(requireContext(), rb.text, Toast.LENGTH_SHORT).show();
                registrationViewModel.gender.postValue(rb.text.toString())
            }
        }



        registrationActivityBinding.tvDob.setOnClickListener {

// Get Current Date
            // Get Current Date
            val c: Calendar = Calendar.getInstance()
            mYear = c.get(Calendar.YEAR)
            mMonth = c.get(Calendar.MONTH)
            mDay = c.get(Calendar.DAY_OF_MONTH)


            val datePickerDialog = DatePickerDialog(
                this,
                { view, year, monthOfYear, dayOfMonth ->
                    registrationViewModel.dob.value =
                        dayOfMonth.toString() + "/" + (monthOfYear + 1) + "/" + year
                },
                mYear,
                mMonth,
                mDay
            )

//            datePickerDialog.datePicker.minDate = Calendar.getInstance().timeInMillis - 1000
            datePickerDialog.show()

        }

    }

}