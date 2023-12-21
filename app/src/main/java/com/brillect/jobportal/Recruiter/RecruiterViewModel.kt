package com.brillect.jobportal.Recruiter

import android.util.Log
import androidx.lifecycle.ViewModel
import com.brillect.jobportal.Data.CreateJobPost
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class RecruiterViewModel : ViewModel() {

    // Initialize Firebase Auth
    val auth = FirebaseAuth.getInstance()
    val currentUser = auth.currentUser  // for current user

    // write job post details to the realtime database
    fun writeJobPostToRealDb(jobPost: CreateJobPost) {
        val database = Firebase.database.reference

        currentUser?.let { user -> // get the current user
            val node_key = database.child("job_posts").push().key
            if (node_key != null) {
                database.child("job_posts").child(node_key)
                    .setValue(jobPost).addOnCompleteListener {
                        if (it.isSuccessful) {
                            Log.d("auth", "data saved successfully")
                        } else {
                            Log.e("auth_error", "Error: ${it.exception?.message.toString()}")
                        }
                    }
            }
        }
    }

    // validate for creating job post
    fun validateJobPostDetails(postDetails: CreateJobPost): String {
        if (postDetails.jobPosition.isEmpty()) {
            return "Enter Job Position."
        } else if (postDetails.requirements.isEmpty()) {
            return "Write you requirements."
        } else if (postDetails.jobLocation.isEmpty()) {
            return "Write Job Location for you job."
        } else if (postDetails.salary.isEmpty()) {
            return "Write Salary which you will pay."
        } else {
            return "yes"
        }
    }
}