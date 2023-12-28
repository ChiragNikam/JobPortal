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


    fun loadJobPosts() {

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
            if (companyNames.isNotEmpty())
                setCompanyNames(companyNames)

            Log.d("job_post", _showJobPosts.value.toString())

        }
    }

    private fun setCompanyNames(companyNames: MutableList<String>) {
        for (i in 0.._companyNames.value.size) {
            _showJobPosts.value[i].companyName = companyNames[i]
        }
    }
}
