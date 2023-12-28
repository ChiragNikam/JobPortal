package com.brillect.jobportal

import android.util.Log
import com.brillect.jobportal.Data.Company
import com.brillect.jobportal.Data.CreateJobPost
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class FirebaseRead {
    // Initialize Firebase Auth
    val auth = FirebaseAuth.getInstance()
    private val currentUser = auth.currentUser  // for current user
    private val database = Firebase.database.reference
    fun getCompany(isCompanyAvailable: (String, Company) -> Unit) {

        getCompanyId { companyId ->
            if (companyId.isEmpty()) {
                isCompanyAvailable("company_not_available", Company())
            } else if (companyId != null) {

                getCompanyDetails(companyId) { company ->
                    isCompanyAvailable("yes", company)
                }
            }
        }

    }

    // get company id
    fun getCompanyId(id: String = currentUser?.uid.toString(), onIdPassed: (String) -> Unit) {
        // get id of company from recruiter node
        currentUser.let { user ->
            if (user != null)
                database.child("user").child("recruiter").child(id).child("company_id")
                    .addValueEventListener(
                        object : ValueEventListener {
                            override fun onDataChange(snapshot: DataSnapshot) {
                                if (snapshot.exists()) {
                                    val companyId = snapshot.getValue(String::class.java)
                                    // after getting id pass it
                                    if (companyId != null) {
                                        Log.d("company_id", companyId)
                                        onIdPassed(companyId)
                                    } else {
                                        onIdPassed("")
                                    }
                                }
                            }

                            override fun onCancelled(error: DatabaseError) {
                                Log.d("company_error", error.message)
                            }

                        }

                    )
        }

    }

    // get company details by it's id
    fun getCompanyDetails(companyId: String, onDataSuccess: (Company) -> Unit) {
        currentUser.let {
            database.child("companies").child(companyId)
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val companyDetails = snapshot.getValue(Company::class.java)
                        if (companyDetails != null) {
                            onDataSuccess(companyDetails)
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Log.d("company_canceled", error.message)
                    }

                })
        }
    }

    fun getJobPostsList(jobPost: (List<CreateJobPost>) -> Unit) {

        database.child("job_posts").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val listForJobPost = mutableListOf<CreateJobPost>()
                if (snapshot.exists()) {
                    for (data in snapshot.children) {
                        val jobPost = data.getValue(CreateJobPost::class.java)
                        if (jobPost != null)
                            listForJobPost.add(jobPost)
                    }
                    jobPost(listForJobPost)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("error_job_post", error.message)
            }

        })
    }

    fun getJobPostById(postId: String, jobPostPassed: (CreateJobPost)-> Unit) {
        database.child("job_posts").child(postId)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val jobPost = snapshot.getValue(CreateJobPost::class.java)
                    if (jobPost != null) {
                        jobPostPassed(jobPost)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.d("job_post_error", error.message)
                }
            })
    }
}

