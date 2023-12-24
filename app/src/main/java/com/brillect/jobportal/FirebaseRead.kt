package com.brillect.jobportal

import android.util.Log
import com.brillect.jobportal.Data.Company
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class FirebaseRead {
    // Initialize Firebase Auth
    val auth = FirebaseAuth.getInstance()
    val currentUser = auth.currentUser  // for current user
    val database = Firebase.database.reference
    fun getCompany(companyDetails: (Company) -> Unit): Company {
        var company = Company()

        // get id of company from recruiter node
        currentUser.let { user ->
            if (user != null)
                database.child("user").child("recruiter").child(user.uid).child("company_id")
                    .addValueEventListener(
                        object : ValueEventListener {
                            override fun onDataChange(snapshot: DataSnapshot) {
                                if (snapshot.exists()) {
                                    snapshot.getValue(String::class.java)
                                        ?.let { companyId ->
                                            Log.d("company_id", companyId)

                                            // pass company id to get details about company
                                            getCompanyDetails(companyId) {
//                                                Log.d("company_details", it.toString())
                                                companyDetails(it)
                                            }
                                        }

                                }
                            }

                            override fun onCancelled(error: DatabaseError) {
                                Log.d("company_error", error.message)
                            }

                        }

                    )
        }

        return company
    }

    // get company details by it id
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
}

