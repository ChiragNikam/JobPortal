package com.brillect.jobportal.Applier

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.brillect.jobportal.Auth.AuthViewModel
import com.brillect.jobportal.Recruiter.RecruiterViewModel
import com.brillect.jobportal.UIComponents.ApplierUI.AvailableJobs

class ApplierHome : ComponentActivity() {
    // view model instance
    val viewModel: ApplierViewModel by lazy { ViewModelProvider(this)[ApplierViewModel::class.java] }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AvailableJobs(
                viewModel,
                onClickSearch = {
                    startActivity(
                        Intent(
                            this,
                            JobCompanyDescription::class.java
                        )
                    )
                },
                onImageClick = { startActivity(Intent(this, ApplierProfile::class.java)) },
                Modifier.clickable {

                })
        }
    }
}