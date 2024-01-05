package com.brillect.jobportal

import android.util.Log
import com.brillect.jobportal.Data.Application
import com.brillect.jobportal.Data.ApplicationForJobPost
import com.brillect.jobportal.Data.ApplierProfile
import com.brillect.jobportal.Data.Company
import com.brillect.jobportal.Data.CreateJobPost
import com.brillect.jobportal.Data.MyDateFormat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class FirebaseWrite {
    // Initialize Firebase Auth
    val auth = FirebaseAuth.getInstance()
    val currentUser = auth.currentUser  // for current user
    val database = Firebase.database.reference

    // write job post details to the realtime database
    fun writeJobPostToRealDb(jobPost: CreateJobPost): String {

        var writeToDbSuccess = ""

        currentUser?.let { user -> // get the current user
            val node_key = database.child("job_posts").push().key
            if (node_key != null) {
                jobPost.jobPostId = node_key
                database.child("job_posts").child(node_key)
                    .setValue(jobPost).addOnCompleteListener {
                        writeToDbSuccess = if (it.isSuccessful) {
                            Log.d("auth", "data saved successfully")
                            "Job Post Saved Successfully"
                        } else {
                            Log.e("auth_error", "Error: ${it.exception?.message.toString()}")
                            "Error while saving please try again later!"
                        }
                    }
            }
        }
        return writeToDbSuccess
    }

    // write company details to the realtime database
    fun writeCompanyDetails(companyDetails: Company): String {
        var writeToDbSuccess = ""
        val nodeKey = database.child("companies").push().key
        currentUser?.let { user -> // get the current user
            if (nodeKey != null) {
                database.child("companies").child(nodeKey)
                    .setValue(companyDetails).addOnCompleteListener {
                        writeToDbSuccess = if (it.isSuccessful) {
                            writeCompanyIdToRecruiter(nodeKey)
                            Log.d("auth", "data saved successfully")
                            "Company details saved successfully"
                        } else {
                            Log.e("auth_error", "Error: ${it.exception?.message.toString()}")
                            "Error while saving details please try again later!"
                        }
                    }
            }
        }
        return writeToDbSuccess
    }

    // write company id to recruiter details
    private fun writeCompanyIdToRecruiter(companyId: String) {

        currentUser?.let { user ->
            database.child("user").child("recruiter").child(user.uid).child("company_id")
                .setValue(companyId).addOnCompleteListener {
                    if (it.isSuccessful) {
                        Log.d("company", "company id saved successfully")
                    } else {
                        Log.e("company_error", "Error: ${it.exception?.message.toString()}")
                    }
                }
        }
    }

    // write applications to job post
    fun writeApplicationToJobPost(jobPostId: String, applicationData: Application) {
        currentUser.let { user ->
            val applicationNode = database.child("applications").push().key
            if (applicationNode != null) {
                applicationData.myId = applicationNode
                if (user != null) {
                    applicationData.applierId = user.uid
                }
                database.child("applications").child(applicationNode)
                    .setValue(applicationData).addOnCompleteListener {
                        if (it.isSuccessful) {
                            Log.d("application", "application saved successfully")
                            writeJobPostIdToApplicant(jobPostId, applicationNode, applicationData)
                        } else {
                            Log.e("application_error", "Error: ${it.exception?.message.toString()}")
                        }
                    }
            }
        }
    }

    // write job post id to user(applier) node
    private fun writeJobPostIdToApplicant(
        jobPostId: String,
        applicationNode: String,
        applicationData: Application
    ) {
        currentUser?.let { user ->
            database.child("user").child("applier").child(user.uid).child("job_post_id")
                .child(jobPostId)
                .setValue(jobPostId).addOnCompleteListener {
                    if (it.isSuccessful) {
                        Log.d("application", "job post id saved successfully")
                        writeApplierIdToJobPost(jobPostId, applicationNode, applicationData)
                    }
                }
        }
    }

    // write applier user id to job post node under applications node
    private fun writeApplierIdToJobPost(
        jobPostId: String,
        applierId: String,
        applicationData: Application
    ) {
        val applicationForJobPost = ApplicationForJobPost("", MyDateFormat(0, 0, 0))
        applicationForJobPost.applierId = applicationData.applierId
        applicationForJobPost.applicationDate = applicationData.applicationDate
        currentUser?.let { user ->
            database.child("job_posts").child(jobPostId).child("applications").child(user.uid)
                .setValue(applicationForJobPost).addOnCompleteListener {
                    if (it.isSuccessful) {
                        Log.d("application", "application id saved successfully")
                    } else {
                        Log.e("application_error", "Error: ${it.exception?.message.toString()}")
                    }
                }
        }
    }

    // write profile details of applier
    fun writeProfileDetails(profileDetails: ApplierProfile, writeStatus: (Boolean, String) -> Unit) {
        currentUser.let { user ->
            if (user != null) {
                database.child("user").child("applier").child(user.uid).setValue(profileDetails)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            writeStatus(true, "")
                        } else {
                            writeStatus(false, task.exception?.message.toString())
                        }
                    }
            }
        }
    }
}