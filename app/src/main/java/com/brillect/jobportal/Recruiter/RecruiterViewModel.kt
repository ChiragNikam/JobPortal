package com.brillect.jobportal.Recruiter

import android.util.Log
import com.brillect.jobportal.Data.CreateJobPost
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class RecruiterViewModel {

    // Initialize Firebase Auth
    val auth = FirebaseAuth.getInstance()
    val currentUser = auth.currentUser  // for current user

    // write job post details to the realtime database
    fun writeJobPostToRealDb(jobPost: CreateJobPost) {
        val database = Firebase.database.reference

        currentUser?.let { user -> // get the current user
            val node_key = database.child("job_posts").push().key
            if (node_key != null) {
                database.child("job_posts").child(node_key).child("account")
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
}