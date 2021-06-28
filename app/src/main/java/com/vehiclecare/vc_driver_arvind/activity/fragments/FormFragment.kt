package com.vehiclecare.vc_driver_arvind.activity.fragments

import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.vehiclecare.vc_driver_arvind.R

class FormFragment : DialogFragment() {


    fun showAlertDialogButtonClicked(context: Activity) {
        // create an alert builder
        val builder = AlertDialog.Builder(context)
        val customLayout: View =
            context.layoutInflater.inflate(R.layout.trip_details_bottom_sheet_layout, null)
        builder.setView(customLayout)

        val dialog = builder.create()


        val window: Window? = dialog.window
        val wlp: WindowManager.LayoutParams = window!!.attributes

        wlp.gravity = Gravity.BOTTOM
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
    }
}