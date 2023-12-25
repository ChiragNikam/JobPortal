package com.brillect.jobportal.Recruiter

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.brillect.jobportal.Data.Company
import com.brillect.jobportal.Data.CreateJobPost
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class RecruiterViewModel : ViewModel() {

    // for remembering the state and opening the view accordingly
    val _selectedState =
        MutableStateFlow(1)  // to record selected state among create-job-post, applications and company profile
    val selectedState: Flow<Int>
        get() = _selectedState.asStateFlow()

    // for remembering the state and opening the view accordingly
    val _selectedStateCompany =
        MutableStateFlow(1)  // to record selected state among create-job-post, applications and company profile
    val selectedStateCompany: Flow<Int>
        get() = _selectedStateCompany.asStateFlow()

    // validate for creating job post
    fun validateJobPostDetails(postDetails: CreateJobPost): String {
        if (postDetails.jobPosition.isEmpty()) {
            return "Write Job Position."
        } else if (postDetails.requirements.isEmpty()) {
            return "Write you requirements."
        } else if (postDetails.jobLocation.isEmpty()) {
            return "Write Job Location for you job."
        } else if (postDetails.salary.isEmpty()) {
            return "Write Salary which you will pay."
        } else {
            return "yes"
        }
    }

    // validate for creating company
    fun validateCreateCompany(companyDetails: Company): String {
        return if (companyDetails.companyLogo.isEmpty()) {
            "Mention you company logo link"
        } else if (companyDetails.companyName.isEmpty()) {
            "Mention you company name"
        } else if (companyDetails.aboutCompany.isEmpty()) {
            "Mention something about your company"
        } else if (companyDetails.website.isEmpty()) {
            "Mention you company website"
        } else {
            "yes"
        }
    }
}