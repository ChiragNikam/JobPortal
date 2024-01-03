package com.brillect.jobportal.Applier

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.brillect.jobportal.UIComponents.ApplierUI.ProfileAndSettings
import com.brillect.jobportal.ui.theme.JobPortalTheme

class ApplierProfile : ComponentActivity() {
    val viewModel: ApplierProfileViewModel by lazy { ViewModelProvider(this)[ApplierProfileViewModel::class.java] }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
           ProfileAndSettings(viewModel)
        }
    }
}
