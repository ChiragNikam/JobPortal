package com.brillect.jobportal.Recruiter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.brillect.jobportal.UIComponents.RecruiterUI.RecruiterUI
import com.brillect.jobportal.ui.theme.BackgroundColor
import com.brillect.jobportal.ui.theme.JobPortalTheme

class RecruiterHome : ComponentActivity() {
    // view model instance
    val viewModel: RecruiterViewModel by lazy { ViewModelProvider(this)[RecruiterViewModel::class.java] }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JobPortalTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()),
                    color = BackgroundColor
                ) {
                    RecruiterUI(viewModel)
                }
            }
        }
    }
}