package com.brillect.jobportal.Applier

import android.util.Log
import androidx.lifecycle.ViewModel
import com.brillect.jobportal.Data.Company
import com.brillect.jobportal.Data.CreateJobPost
import com.brillect.jobportal.Data.JobPostsApplier
import com.brillect.jobportal.FirebaseRead
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ApplierViewModel : ViewModel() {
    private var _showJobPosts = MutableStateFlow(mutableListOf<JobPostsApplier>())
    val showJobPost: StateFlow<List<JobPostsApplier>> = _showJobPosts

    private var _companyDetails = MutableStateFlow(Company())
    val companyDetails: StateFlow<Company> = _companyDetails

    private var _jobDetails = MutableStateFlow(CreateJobPost())
    val jobDetails: StateFlow<CreateJobPost> = _jobDetails

    // progress bar indicator
    private var _progressIndicator = MutableStateFlow(true)
    val progressIndicator: StateFlow<Boolean> = _progressIndicator

    private var _filteredJobPosts = MutableStateFlow(mutableListOf<JobPostsApplier>())
    val filteredJobPosts: StateFlow<List<JobPostsApplier>> = _filteredJobPosts

    // applier resume url
    private var _resumeUrl = MutableStateFlow("")
    val resumeUrl : StateFlow<String> = _resumeUrl

    fun setResumeUrl(url: String){
        _resumeUrl.value = url
    }

    fun updateFilteredJobPosts(query: String) {
        if (query.isEmpty()) {
            _filteredJobPosts.value = _showJobPosts.value
        } else {
            _filteredJobPosts.value = _showJobPosts.value.filter {
                it.jobPosition.contains(query, ignoreCase = true)
            }.toMutableList()
        }
    }

    fun loadJobPosts() {    // Load the list of Job Posts
        val companyNames = mutableListOf<String>()
        FirebaseRead().getJobPostsList { _jobList ->
            val updatedList = _jobList.map { job ->
                JobPostsApplier(
                    jobId = job.jobPostId,
                    jobPosition = job.jobPosition,
                    jobLocation = job.jobLocation,
                    salary = job.salary,
                    jobType = job.jobType,
                    workplace = job.workplace
                )
            }

            // Update the StateFlow with the new list
            _showJobPosts.value = updatedList.toMutableList()
            _filteredJobPosts.value = updatedList.toMutableList()

            if (_showJobPosts.value.isEmpty()) {
                _progressIndicator.value = false
            }
            // don't display progress bar if job posts loaded successfully
            _progressIndicator.value = false
        }
    }

    private fun loadCompanyDetails(
        companyId: String,
        jobDetails: CreateJobPost,
        onDataPassed: () -> Unit
    ) {
        FirebaseRead().getCompanyDetails(companyId) { companyDetails ->
            _companyDetails.value = companyDetails
            jobDetails.companyName = companyDetails.companyName
            onDataPassed()
            Log.d("company", _companyDetails.value.toString())
        }
    }

    fun loadJobCompanyDetails(jobId: String) {
        FirebaseRead().getJobPostById(jobId) { jobDetails ->
            loadCompanyDetails(jobDetails.companyId, jobDetails) {
                _jobDetails.value = jobDetails
            }
            Log.d("job", _jobDetails.value.toString())
        }
    }

    // get company name by job id
    fun getCompanyName(jobId: String, namePassed: (String) -> Unit) {
        // Initialize Firebase Auth
        val auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser  // for current user
        val database = Firebase.database.reference

        database.child("job_posts").child(jobId).child("companyId").addValueEventListener(
            object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val companyId = snapshot.getValue(String::class.java).toString()
                    FirebaseRead().getCompanyDetails(companyId) {
                        namePassed(it.companyName)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.d("company_name_err", error.message)
                }

            }
        )

    }
}
