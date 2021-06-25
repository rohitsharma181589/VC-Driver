package com.vehiclecare.vc_driver_arvind.dagger

import com.vehiclecare.vc_driver_arvind.activity.*
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

    @ContributesAndroidInjector
    abstract fun registrationActivity(): RegistrationActivity

    @ContributesAndroidInjector
    abstract fun homeActivity(): HomeActivity

    @ContributesAndroidInjector
    abstract fun mapActivity(): MapActivity
}