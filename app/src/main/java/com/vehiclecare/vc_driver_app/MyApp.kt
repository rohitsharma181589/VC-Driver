package com.vehiclecare.vc_driver_app

import android.app.Application
import com.vehiclecare.vc_driver_app.dagger.AppInjector
import com.vehiclecare.vc_driver_app.utils.AppSharedPreference
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class MyApp : Application(), HasAndroidInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()
        AppInjector.init(this)
        AppSharedPreference.initializePreference(this)

//        EventBus.builder()
//            // have a look at the index class to see which methods are picked up
//            // if not in the index @Subscribe methods will be looked up at runtime (expensive)
//            .addIndex(MyEventBusIndex())
//            .installDefaultEventBus()
    }


    override fun androidInjector(): AndroidInjector<Any> {
        return dispatchingAndroidInjector
    }
}