package com.brillect.jobportal.Applier

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModelProvider
import com.brillect.jobportal.UIComponents.ApplierUI.ProfileAndSettings

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
                viewModel.updateWriteStatus(false)
            }
        }

    }
}
