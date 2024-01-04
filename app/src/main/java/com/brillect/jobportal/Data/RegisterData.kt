package com.brillect.jobportal.Data

data class RegisterData(
    val u_name: String,
    val email: String,
    val password: String
)

class RegisterDataWithConfirmPass{
    lateinit var fullName: String
    lateinit var email: String
    lateinit var password: String
    lateinit var confirmPassword: String
}