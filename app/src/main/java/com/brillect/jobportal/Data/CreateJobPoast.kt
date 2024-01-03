package com.brillect.jobportal.Data

data class CreateJobPost (
    val recruiterId: String = "",
    val jobPosition: String = "",
    val jobDescription: String = "",
    val requirements: String = "",
    val facilitiesAndOther: String = "",
    val jobLocation: String = "",
    val salary: String = "",
    val jobType: String = "",
    val workplace: String = "",
    var companyId: String = "",
    var jobPostId: String = "",
    var companyName: String = ""
//    val validTill: MyDateFormat
)

data class MyDateFormat(
    val dd: Int = 0 ,
    val mm: Int = 0 ,
    val yyyy: Int = 0
)

// class for job type to be set to the drop down
enum class JobType(val label: String) {
    FULL_TIME("Full Time"),
    PART_TIME("Part Time"),
    INTERN("Intern")
}

// class for work place to be set to the drop down
enum class WorkPlace(val label: String) {
    REMOTE("Remote"),
    ON_SITE("On Site"),
}
