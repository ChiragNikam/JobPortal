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

data class JobPostsRecruiter(
    val jobPostId: String = "",
    val companyId: String = "",
    val jobPosition: String = "",
    val salary: String = "",
    val workplace: String = "",
    val jobType: String = ""
)

data class AdminCardDetails(
    var companyName: String = "",
    var userName: String = "",
    var email: String = ""
)