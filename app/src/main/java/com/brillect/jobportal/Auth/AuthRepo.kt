package com.brillect.jobportal.Auth

import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class AuthRepo {
    private val auth = FirebaseAuth.getInstance()
    private val currentUser = auth.currentUser
    fun registerUser(
        email: String,
        password: String,
        onCompleteListener: OnCompleteListener<AuthResult>
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(onCompleteListener)
    }
}