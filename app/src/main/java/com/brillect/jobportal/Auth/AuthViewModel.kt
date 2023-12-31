package com.brillect.jobportal.Auth

import android.util.Log
import androidx.compose.ui.text.toLowerCase
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import com.brillect.jobportal.Data.RegisterData
import com.brillect.jobportal.Data.RegisterDataWithConfirmPass
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class AuthViewModel : ViewModel() {

    // Initialize Firebase Auth
    val auth = FirebaseAuth.getInstance()
    val currentUser = auth.currentUser  // for current user

    fun isReadyToRegister(details: RegisterDataWithConfirmPass): String {
        if (details.fullName.isEmpty()) {
            return "Please fill your Full Name"
        } else if (details.email.isEmpty()) {
            return "Please fill your E-mail"
        } else if (details.password.isEmpty()) {
            return "Please enter you password"
        } else if (details.password != details.confirmPassword) {
            return "Password didn't match"
        } else {
            return "yes"
        }
    }

    // write user details to the realtime database
    fun writeUserToDb(registerDetails: RegisterData, profile: String) {
        val database = Firebase.database.reference
        currentUser?.let { user -> // get the current user
            database.child("user").child(profile.lowercase()).child(user.uid)
                .setValue(registerDetails).addOnCompleteListener {
                if (it.isSuccessful) {
                    Log.d("auth", "data saved successfully")
                } else {
                    Log.e("auth_error", "Error: ${it.exception?.message.toString()}")
                }
            }
        }
    }

    fun testWriteToRealTimeDb() {
        val database = Firebase.database.reference
        currentUser?.let { user -> // get the current user
            database.child("Applier").child(user.uid)
                .setValue("Test Sucessful").addOnCompleteListener {
                    if (it.isSuccessful) {
                        Log.d("auth", "data saved successfully")
                    } else {
                        Log.e("auth_error", "Error: ${it.exception?.message.toString()}")
                    }
                }
        }
    }
}