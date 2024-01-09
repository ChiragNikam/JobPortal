package com.brillect.jobportal.Applier

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import com.brillect.jobportal.Data.ApplierProfile
import com.brillect.jobportal.FirebaseFiles
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

    // pdf url
    private val _pdfUrl = MutableStateFlow("")
    val pdfUrl: StateFlow<String> = _pdfUrl

    fun updateUserProfileObj(profile: ApplierProfile) {
        _userProfile.value = profile
    }

    fun updateWriteStatus(status: Boolean) {
        _writeStatusProfile.value = status
    }

    fun uploadProfileDetails() {
        FirebaseWrite().writeProfileDetails(_userProfile.value) { status, message ->
            updateWriteStatus(status)
            Log.d("writeProfile", message)
        }
    }

    fun setUserProfileFromDb() {
        FirebaseRead().getApplierProfile { applierProfile ->
            updateUserProfileObj(applierProfile)
        }
    }

    fun uploadResume(fileToUpload: Uri) {
        FirebaseFiles().uploadDoc(fileToUpload, { url ->
            // write resume url to the db
            FirebaseWrite().writeResumeLink(url)
        }, { progress ->
            if (progress == 100){

            }
        })
    }
}