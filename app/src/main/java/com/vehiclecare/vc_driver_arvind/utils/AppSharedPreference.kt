package com.vehiclecare.vc_driver_arvind.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import java.io.IOException
import java.security.GeneralSecurityException


object AppSharedPreference {

    private var mSharedPreferences: SharedPreferences? = null
    private var mSharedEditor: SharedPreferences.Editor? = null
    private const val PREFERENCE_NAME = "VehicleCarePReference"
    private const val PRIVATE_MODE = 0

    const val IS_USER_LOGGED_IN = "IS_USER_LOGGED_IN"
    const val USER_TOKEN = "USER_TOKEN"
    const val USER_ACKO_AUTHORIZATION = "USER_ACKO_AUTHORIZATION"
    const val USER_FName = "USER_FName"
    const val USER_LName = "USER_LName"
    const val USER_Name = "USER_Name"
    const val USER_EMail = "USER_EMail"
    const val USER_ID = "USER_ID"
    const val USER_PHONE = "USER_PHONE"
    const val USER_ADDRESS = "USER_ADDRESS"
    const val CAR_REG = "car_reg"
    const val BRANDS_LIST = "brands_list"
    const val MODELS_LIST = "models_list"
    const val FOR_EDIT = "for_edit"

    fun initializePreference(context: Context) {
        try {
            val mainKey: MasterKey = MasterKey.Builder(context)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build()
            mSharedPreferences = EncryptedSharedPreferences.create(
                context, PREFERENCE_NAME, mainKey,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )

            editSession()

        } catch (e: GeneralSecurityException) {
            mSharedPreferences =
                context.getSharedPreferences(PREFERENCE_NAME, PRIVATE_MODE)
        } catch (e: IOException) {

            mSharedPreferences =
                context.getSharedPreferences(PREFERENCE_NAME, PRIVATE_MODE)
        }
    }


    fun saveStringValue(key: String, value: String) {

        if (editSession()) {
            mSharedEditor?.putString(key, value)
            mSharedEditor?.apply()
        }
    }

    fun getStringValue(key: String): String? {

        if (editSession()) {
            return mSharedPreferences?.getString(key, "")
        }

        return ""
    }

    fun saveIntValue(key: String, value: Int) {

        if (editSession()) {
            mSharedEditor?.putInt(key, value)
            mSharedEditor?.apply()
        }
    }

    fun getIntValue(key: String): Int? {

        if (editSession()) {
            return mSharedPreferences?.getInt(key, -1)
        }

        return -1
    }

    fun saveBooleanValue(key: String, value: Boolean) {

        if (editSession()) {
            mSharedEditor?.putBoolean(key, value)
            mSharedEditor?.apply()
        }
    }

    fun getBooleanValue(key: String): Boolean? {

        if (editSession()) {
            return mSharedPreferences?.getBoolean(key, false)
        }
        return false
    }


    private fun editSession(): Boolean {
        if (mSharedPreferences != null) {
            mSharedEditor = mSharedPreferences!!.edit()
            return true
        }
        return false
    }


    fun clearPreference() {
        mSharedEditor?.clear();
        mSharedEditor?.apply();
    }
}