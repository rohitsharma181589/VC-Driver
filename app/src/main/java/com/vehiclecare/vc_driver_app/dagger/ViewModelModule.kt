/*
 * Copyright (C) 2018 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.vehiclecare.vc_driver_app.dagger

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vehiclecare.vc_driver_app.viewmodels.LoginViaOtpViewModel
import com.vehiclecare.vc_driver_app.viewmodels.LoginViewModel
import com.vehiclecare.vc_driver_app.viewmodels.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

//    @Binds
//    @IntoMap
//    @ViewModelKey(MainActivityViewModel::class)
//    abstract fun bindUserViewModel(userViewModel: MainActivityViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    abstract fun bindSearchViewModel(searchViewModel: LoginViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoginViaOtpViewModel::class)
    abstract fun loginViaOtpViewModel(loginViaOtpViewModel: LoginViaOtpViewModel): ViewModel

    //    @Binds
//    @IntoMap
//    @ViewModelKey(RegistrationViewModel::class)
//    abstract fun bindRepoViewModel(registrationViewModel: RegistrationViewModel): ViewModel
//
//    @Binds
//    @IntoMap
//    @ViewModelKey(HomeViewModel::class)
//    abstract fun fragmentHomeViewModel(homeViewModel: HomeViewModel): ViewModel
//
//    @Binds
//    @IntoMap
//    @ViewModelKey(CartViewModel::class)
//    abstract fun cartViewModel(cartViewModel: CartViewModel): ViewModel
//
//    @Binds
//    @IntoMap
//    @ViewModelKey(ServiceDetailViewModel::class)
//    abstract fun serviceDetailViewModel(serviceDetailViewModel: ServiceDetailViewModel): ViewModel
//

//
//    @Binds
//    @IntoMap
//    @ViewModelKey(AddEditAddressViewModel::class)
//    abstract fun addressViewModelViewModel(addEditAddressViewModel: AddEditAddressViewModel): ViewModel
//
//    @Binds
//    @IntoMap
//    @ViewModelKey(UserAddressAndOrderDetailsViewModel::class)
//    abstract fun useruserAddressAndOrderDetailsViewModel(userAddressAndOrderDetailsViewModel: UserAddressAndOrderDetailsViewModel): ViewModel
//
//    @Binds
//    @IntoMap
//    @ViewModelKey(UserProfileViewModel::class)
//    abstract fun userProfileViewModelViewModel(userProfileViewModel: UserProfileViewModel): ViewModel
//
//    @Binds
//    @IntoMap
//    @ViewModelKey(SelectBrandViewModel::class)
//    abstract fun userSelectBrandViewModel(selectBrandViewModel: SelectBrandViewModel): ViewModel
//
//    @Binds
//    @IntoMap
//    @ViewModelKey(SelectModelViewModel::class)
//    abstract fun userSelectModelViewModel(selectModelViewModel: SelectModelViewModel): ViewModel
//
//    @Binds
//    @IntoMap
//    @ViewModelKey(ProfileSettingViewModel::class)
//    abstract fun userProfileSettingViewModel(profileEditViewModel: ProfileSettingViewModel): ViewModel
//
//    @Binds
//    @IntoMap
//    @ViewModelKey(UserAddressViewModel::class)
//    abstract fun userUserAddressViewModel(userAddressViewModel: UserAddressViewModel): ViewModel


}
