package com.brillect.jobportal.Recruiter

import android.util.Log
import androidx.lifecycle.ViewModel
import com.brillect.jobportal.Data.AdminCardDetails
import com.brillect.jobportal.Data.CreateJobPost
import com.brillect.jobportal.Data.JobPostsRecruiter
import com.brillect.jobportal.FirebaseRead
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class RecruiterProfileViewModel : ViewModel() {

    private val read = FirebaseRead()

    // Initialize Firebase Auth
    val auth = FirebaseAuth.getInstance()
    private val currentUser = auth.currentUser  // for current user
    private val database = Firebase.database.reference

    // history of all job posts done by the recruiter
    private val _listOfJobPostsHistory = MutableStateFlow(mutableListOf<CreateJobPost>())
    val listOfJobPostsHistory: StateFlow<List<CreateJobPost>> = _listOfJobPostsHistory

    // details of profile card
    private val _adminProfileCardDetails = MutableStateFlow(AdminCardDetails())
    val adminProfileCardDetails: StateFlow<AdminCardDetails> = _adminProfileCardDetails

    fun setListOfAllJobPosts() {

        read.getCompanyId { companyId ->
            read.getJobPostsList { jobPosts ->
                val filteredJobPosts = mutableListOf<CreateJobPost>()
                for (post in jobPosts){
                    if (post.companyId == companyId)
                        filteredJobPosts.add(post)
                }
                _listOfJobPostsHistory.value = filteredJobPosts
            }
        }
    }

    // set details for profile card view
    fun setDataForProfileCard() {
        // set company name
        read.getCompanyId { companyId ->
            read.getCompanyDetails(companyId) { companyDetails ->
                _adminProfileCardDetails.value.companyName = companyDetails.companyName
            }
        }

        // set user name
        read.getUserName("recruiter", false) { userName ->
            _adminProfileCardDetails.value.userName = userName
        }

        // set user email
        if (currentUser != null) {
            database.child("user").child("recruiter").child(currentUser.uid).child("email")
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        _adminProfileCardDetails.value.email =
                            snapshot.getValue(String::class.java).toString()
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Log.d("email_err", error.message)
                    }

                })
        }
    }
}