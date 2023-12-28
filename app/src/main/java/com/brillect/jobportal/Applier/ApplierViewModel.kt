package com.brillect.jobportal.Applier

import android.util.Log
import androidx.lifecycle.ViewModel
import com.brillect.jobportal.Data.Company
import com.brillect.jobportal.Data.CreateJobPost
import com.brillect.jobportal.Data.JobPostsApplier
import com.brillect.jobportal.FirebaseRead
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ApplierViewModel : ViewModel() {
    private var _showJobPosts = MutableStateFlow(mutableListOf<JobPostsApplier>())
    val showJobPost: StateFlow<List<JobPostsApplier>> = _showJobPosts

    private val _companyNames = MutableStateFlow(mutableListOf<String>())

    private var _companyDetails = MutableStateFlow(Company())
    val companyDetails: StateFlow<Company> = _companyDetails

    private var _jobDetails = MutableStateFlow(CreateJobPost())
    val jobDetails: StateFlow<CreateJobPost> = _jobDetails

    fun loadJobPosts() {    // Load the list of Job Posts
        val companyNames = mutableListOf<String>()
        FirebaseRead().getJobPostsList { _jobList ->
            val updatedList = _jobList.map { job ->

                FirebaseRead().getCompanyId { companyId ->
                    FirebaseRead().getCompanyDetails(companyId) { companyDetails ->
                        companyNames.add(companyDetails.companyName)
                        Log.d("company_name", companyNames.toString())
                    }
                }
                _companyNames.value = companyNames

                Log.d("company_name", _companyNames.value.toString())
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

            Log.d("job_post", _showJobPosts.value.toString())
        }
    }

    fun loadCompanyDetails(companyId: String, jobDetails: CreateJobPost, onDataPassed: () -> Unit) {
        FirebaseRead().getCompanyDetails(companyId) { companyDetails ->
            _companyDetails.value = companyDetails
            jobDetails.companyName = companyDetails.companyName
            onDataPassed()
            Log.d("company", _companyDetails.value.toString())
        }
    }

    fun loadJobCompanyDetails(jobId: String) {
        FirebaseRead().getJobPostById(jobId) { jobDetails ->
            loadCompanyDetails(jobDetails.companyId, jobDetails){
                _jobDetails.value = jobDetails
            }
            Log.d("job", _jobDetails.value.toString())
        }
    }

}
