package com.brillect.jobportal.Applier

import android.util.Log
import androidx.lifecycle.ViewModel
import com.brillect.jobportal.Data.ApplierProfile
import com.brillect.jobportal.FirebaseRead
import com.brillect.jobportal.FirebaseWrite
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ApplierProfileViewModel : ViewModel() {
    // user profile details
    private val _userProfile = MutableStateFlow(ApplierProfile())
    val userProfile: StateFlow<ApplierProfile> = _userProfile

    // user profile write status
    private val _writeStatusProfile = MutableStateFlow(false)
    val writeStatusProfile: StateFlow<Boolean> = _writeStatusProfile
    fun updateUserProfileObj(profile: ApplierProfile) {
        _userProfile.value = profile
    }
    private fun writeStatus(status: Boolean){
        _writeStatusProfile.value = status
    }
    fun uploadProfileDetails() {
        FirebaseWrite().writeProfileDetails(_userProfile.value) { status, message ->
            writeStatus(status)
            Log.d("writeProfile", message)
        }
    }

    fun setUserProfileFromDb(){
        FirebaseRead().getApplierProfile { applierProfile ->
            updateUserProfileObj(applierProfile)
        }
    }
}