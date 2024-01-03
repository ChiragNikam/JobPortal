package com.brillect.jobportal.Data

data class Application(
    var myId: String = "",
    var applierId: String = "",
    val jobPostId: String = "",
    val applicationDate: MyDateFormat
)

data class ApplicationForJobPost(
    var applierId: String = "",
    var applicationDate: MyDateFormat = MyDateFormat(0, 0, 0)
)

data class AppliersByJob(
    var jobName: String = "",
    var listOfAppliers: MutableList<ApplicationForJobPost> = mutableListOf()
)
