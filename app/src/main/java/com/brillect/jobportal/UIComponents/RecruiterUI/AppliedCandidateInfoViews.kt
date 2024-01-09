package com.brillect.jobportal.UIComponents.RecruiterUI

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.brillect.jobportal.Applier.ApplierProfileViewModel
import com.brillect.jobportal.Recruiter.AppliedCandidateViewModel
import com.brillect.jobportal.UIComponents.InfoBlock
import com.brillect.jobportal.ui.theme.BackgroundColor

@Composable
fun CandidateProfile(viewModel: AppliedCandidateViewModel) {
    Column(
        modifier = Modifier
            .background(
                color = BackgroundColor
            )
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(start = 24.dp, top = 24.dp, end = 24.dp)
    ) {
        val userProfile by viewModel.candidateInfo.collectAsState()

        // User Name
        InfoBlock(
            label = "Name",
            description = userProfile.u_name
        )
        Spacer(modifier = Modifier.height(24.dp))
        // Email
        InfoBlock(
            label = "Email",
            description = userProfile.email
        )
        Spacer(modifier = Modifier.height(24.dp))
        // About User
        InfoBlock(
            label = "About Me",
            description = userProfile.aboutMe
        )
        Spacer(modifier = Modifier.height(24.dp))
        // Work Experience
        InfoBlock(
            label = "Work Experience", description = userProfile.workExperience
        )
        Spacer(modifier = Modifier.height(24.dp))
        // Education
        InfoBlock(
            label = "Education",
            description = userProfile.workExperience
        )
        Spacer(modifier = Modifier.height(24.dp))
        // Skills
        InfoBlock(
            label = "Skills",
            description = userProfile.skills
        )
        Spacer(modifier = Modifier.height(24.dp))
    }
}