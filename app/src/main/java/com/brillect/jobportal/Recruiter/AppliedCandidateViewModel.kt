package com.brillect.jobportal.Recruiter

import android.util.Log
import androidx.lifecycle.ViewModel
import com.brillect.jobportal.Data.ApplierProfile
import com.brillect.jobportal.FirebaseRead
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AppliedCandidateViewModel: ViewModel() {
    private val _candidateInfo = MutableStateFlow(ApplierProfile())
    val candidateInfo : StateFlow<ApplierProfile> = _candidateInfo

    // pdf url
    private val _resumeUrl = MutableStateFlow("")
    val resumeUrl: StateFlow<String> = _resumeUrl

    fun setApplierDetails(applierId: String){
        FirebaseRead().getApplierProfile(applierId) { applierProfile ->
            _candidateInfo.value = applierProfile
            Log.d("applier_info", _candidateInfo.value.toString())
        }
    }

    // get resume link
    fun getResumeLink(applierId: String){
        FirebaseRead().getResumeUrlOfApplier(applierId){url ->
            _resumeUrl.value = url
        }
    }
}