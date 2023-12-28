package com.brillect.jobportal.Data

data class JobPostsApplier(
    val jobId: String = "",
    val jobPosition: String = "",
    var companyName: String = "",
    val jobLocation: String = "",
    val salary: String = "",
    val jobType: String = "",
    val workplace: String = ""
)
