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

    fun setApplierDetails(applierId: String){
        FirebaseRead().getApplierProfile(applierId) { applierProfile ->
            _candidateInfo.value = applierProfile
            Log.d("applier_info", _candidateInfo.value.toString())
        }
    }
}