package com.brillect.jobportal.Data

data class Application(
    var myId: String = "",
    var applierId: String = "",
    val jobPostId: String = "",
    val applicationDate: MyDateFormat
)
