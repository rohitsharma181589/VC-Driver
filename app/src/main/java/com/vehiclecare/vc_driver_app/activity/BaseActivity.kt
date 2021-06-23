package com.vehiclecare.vc_driver_app.activity

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.vehiclecare.vc_driver_app.R
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

abstract class BaseActivity : AppCompatActivity(), HasAndroidInjector {

    private lateinit var progressDialog: Dialog

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>


    override fun androidInjector(): AndroidInjector<Any> {
        return dispatchingAndroidInjector
    }

    companion object {
        init {
            System.loadLibrary("native-lib")

        }

        @JvmStatic
        external fun AccessCodeFromJNI(): String
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        progressDialog = Dialog(this)
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        progressDialog.setContentView(R.layout.custom_dialog_progress)

        val progressTv = progressDialog.findViewById(R.id.progress_tv) as TextView
        progressTv.text = resources.getString(R.string.loading)
        progressTv.setTextColor(ContextCompat.getColor(this, R.color.red))
        progressTv.textSize = 19F
        progressDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        progressDialog.setCancelable(true)
    }


    fun showProgressDialog() {

        progressDialog.show()
    }

    fun hideProgressDialog() {
        progressDialog.dismiss()
    }

    private fun test() {

    }

//    fun Context.shortToast(msg: CharSequence) = Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
//
//    fun Context.longToast(msg: CharSequence) = Toast.makeText(this, msg, Toast.LENGTH_LONG).show()


    fun String.shortToast(msg: CharSequence, context: Context) =
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()

    fun String.longToast(msg: CharSequence, context: Context) =
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show()

    fun showToast(msg: String) {
        Toast.makeText(applicationContext, msg, Toast.LENGTH_LONG).show()
    }

}