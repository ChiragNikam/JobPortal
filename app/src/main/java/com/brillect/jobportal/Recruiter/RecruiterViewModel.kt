package com.brillect.jobportal.Recruiter

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.brillect.jobportal.Data.ApplicationForJobPost
import com.brillect.jobportal.Data.AppliersByJob
import com.brillect.jobportal.Data.Company
import com.brillect.jobportal.Data.CreateJobPost
import com.brillect.jobportal.FirebaseRead
import com.brillect.jobportal.FirebaseWrite
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class RecruiterViewModel : ViewModel() {

    // Initialize Firebase Auth
    val auth = FirebaseAuth.getInstance()
    private val currentUser = auth.currentUser  // for current user
    private val database = Firebase.database.reference

    // remember the state and opening the view accordingly
    val _selectedState =
        MutableStateFlow(1)  // to record selected state among create-job-post, applications and company profile
    val selectedState: Flow<Int>
        get() = _selectedState.asStateFlow()

    // for remembering the state and opening the view accordingly
    val _selectedStateCompany =
        MutableStateFlow(1)  // to record selected state among create-job-post, applications and company profile
    val selectedStateCompany: Flow<Int>
        get() = _selectedStateCompany.asStateFlow()

    // progress bar status
    private val _progressIndicatorAppliedCan = MutableStateFlow(true)
    val progressIndicatorAppliedCan: StateFlow<Boolean> = _progressIndicatorAppliedCan

    // first name of user
    private val _firstName = MutableStateFlow("")
    val firstName: StateFlow<String> = _firstName

    private fun updateFirstName(firstName: String) {
        _firstName.value = firstName
    }

    // update progress status
    fun updateProgressStatus(status: Boolean) {
        _progressIndicatorAppliedCan.value = status
    }

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

    fun getFirstName(userType: String) {
        FirebaseRead().getUserName(userType) { firstName ->
            updateFirstName(firstName)
        }
    }

    // get list of appliers who had applied to the job post by there job position name
    var _appliedCandidatesToJobList = MutableStateFlow(mutableListOf<AppliersByJob>())
    val appliedCandidatesToJobList: StateFlow<List<AppliersByJob>> = _appliedCandidatesToJobList

    // get all applied candidates(id's) to the job posts of company
    fun getAllCandidatesId() {

        val filteredJobPostsIds = mutableListOf<CreateJobPost>()
        val firebaseRead = FirebaseRead()

        firebaseRead.getCompanyId { companyId ->// get company id
            Log.d("debug", "getting company id")
            firebaseRead.getJobPostsList { listJobPosts ->
                // filter job posts according which belongs to the company
                for (jobPost in listJobPosts) {
                    if (jobPost.companyId == companyId) {
                        filteredJobPostsIds.add(jobPost)
                    }
                }
                for (job in filteredJobPostsIds) {
                    val applierByJobPost = AppliersByJob()
                    // add job position
                    applierByJobPost.jobName = job.jobPosition

                    // get all applications to the job_posts
                    database.child("job_posts").child(job.jobPostId).child("applications")
                        .addValueEventListener(object :
                            ValueEventListener {
                            override fun onDataChange(snapshot: DataSnapshot) {
                                if (snapshot.exists()) {
                                    for (data in snapshot.children) {
                                        val application =
                                            data.getValue(ApplicationForJobPost::class.java)
                                        if (application != null)
                                            applierByJobPost.listOfAppliers.add(application)
                                    }
                                    val newList = _appliedCandidatesToJobList.value.toMutableList()
                                    newList.add(applierByJobPost)
                                    // update the list
                                    _appliedCandidatesToJobList.value = newList

                                    updateProgressStatus(false)

                                } else {
                                    updateProgressStatus(false)
                                }
                            }

                            override fun onCancelled(error: DatabaseError) {
                                Log.e("error", error.message)
                            }

                        })
                }
            }
        }
    }

    // for bottom sheet visibility
    private val _editCompanyBottomSheet = MutableStateFlow(false)
    val editCompanyBottomSheet: StateFlow<Boolean> = _editCompanyBottomSheet

    // details to be edited at bottom sheet for a company
    private val _updateCompanyDetails = MutableStateFlow(Company())
    val updateCompanyDetails: StateFlow<Company> = _updateCompanyDetails

    fun setCompanyDetailsForBottomSheet(details: Company) {
        _updateCompanyDetails.value = details
    }

    fun updateCompanyBottomSheet(show: Boolean) {
        _editCompanyBottomSheet.value = show
    }

    fun updateCompanyDetails(updatedDetails: Company, updateSuccess: (Boolean) -> Unit) {
        FirebaseRead().getCompanyId { companyId ->
            FirebaseWrite().updateCompanyDetails(companyId, updatedDetails) { updated ->
                updateSuccess(updated)  // details are updated or not
            }
        }
    }
}