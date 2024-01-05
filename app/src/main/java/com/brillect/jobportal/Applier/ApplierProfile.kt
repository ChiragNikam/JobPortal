package com.brillect.jobportal.Applier

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.brillect.jobportal.UIComponents.ApplierUI.ProfileAndSettings
import com.brillect.jobportal.ui.theme.JobPortalTheme

class ApplierProfile : ComponentActivity() {
    val viewModel: ApplierProfileViewModel by lazy { ViewModelProvider(this)[ApplierProfileViewModel::class.java] }

    override fun onStart() {
        super.onStart()

        viewModel.setUserProfileFromDb()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProfileAndSettings(viewModel) {
                onBackPressed()
            }
            val writeStatus by viewModel.writeStatusProfile.collectAsState()
            if (writeStatus) {
                Toast.makeText(this, "Profile Updated Successfully", Toast.LENGTH_SHORT).show()
                viewModel.writeStatus(false)
            }
        }

    }
}
