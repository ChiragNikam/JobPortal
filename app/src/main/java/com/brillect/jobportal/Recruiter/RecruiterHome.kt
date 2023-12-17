package com.brillect.jobportal.Recruiter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.brillect.jobportal.UIComponents.RecruiterUI.RecruiterUI

class RecruiterHome : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RecruiterUI()
        }
    }
}