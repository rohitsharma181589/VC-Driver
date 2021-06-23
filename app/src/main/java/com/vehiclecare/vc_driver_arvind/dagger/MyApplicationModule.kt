package com.vehiclecare.vc_driver_arvind.dagger

import com.vehiclecare.vc_driver_arvind.activity.LoginActivity
import com.vehiclecare.vc_driver_arvind.activity.LoginViaOtp
import com.vehiclecare.vc_driver_arvind.activity.SplashActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MyApplicationModule {


//    @ContributesAndroidInjector(
//        modules = [
//            FragmentBuildersModule::class
//        ]
//    )
//    abstract fun introLandingScreen(): IntroLandingScreen
//
//    @ContributesAndroidInjector(
//        modules = [
//            FragmentBuildersModule::class
//        ]
//    )
//    abstract fun introHomeScreen(): MainActivity

    @ContributesAndroidInjector
    abstract fun splashActivity(): SplashActivity

    @ContributesAndroidInjector
    abstract fun loginActivity(): LoginActivity

    @ContributesAndroidInjector
    abstract fun loginViaOtpActivity(): LoginViaOtp

//    @ContributesAndroidInjector
//    abstract fun registrationActivity(): RegistrationActivity
}