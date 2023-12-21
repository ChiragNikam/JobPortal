package com.brillect.jobportal.Recruiter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import com.brillect.jobportal.UIComponents.RecruiterUI.RecruiterUI

class RecruiterHome : ComponentActivity() {
    // view model instance
    val viewModel: RecruiterViewModel by lazy { ViewModelProvider(this)[RecruiterViewModel::class.java] }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            RecruiterUI(viewModel)
        }
    }
}