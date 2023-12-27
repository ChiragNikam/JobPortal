package com.brillect.jobportal.Applier

import androidx.lifecycle.ViewModel
import com.brillect.jobportal.Data.JobPostsApplier
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ApplierViewModel: ViewModel() {
    val _showJobPosts = MutableStateFlow(mutableListOf<JobPostsApplier>())
    val showJobPost: Flow<List<JobPostsApplier>> = _showJobPosts.asStateFlow()
}