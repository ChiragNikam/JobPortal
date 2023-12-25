package com.brillect.jobportal

import android.util.Log
import com.brillect.jobportal.Data.Company
import com.brillect.jobportal.Data.CreateJobPost
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class FirebaseWrite {
    // Initialize Firebase Auth
    val auth = FirebaseAuth.getInstance()
    val currentUser = auth.currentUser  // for current user

    // write job post details to the realtime database
    fun writeJobPostToRealDb(jobPost: CreateJobPost): String {
        val database = Firebase.database.reference
        var writeToDbSuccess = ""

        currentUser?.let { user -> // get the current user
            val node_key = database.child("job_posts").push().key
            if (node_key != null) {
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
        val database = Firebase.database.reference
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
        val database = Firebase.database.reference

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

}