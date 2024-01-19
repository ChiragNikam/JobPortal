package com.brillect.jobportal.UIComponents.RecruiterUI

import android.content.Intent
import android.util.Log
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.brillect.jobportal.Applier.ApplierProfileViewModel
import com.brillect.jobportal.Recruiter.AppliedCandidateViewModel
import com.brillect.jobportal.UIComponents.BtnCustom
import com.brillect.jobportal.UIComponents.InfoBlock
import com.brillect.jobportal.ui.theme.BackgroundColor
import com.rizzi.bouquet.HorizontalPDFReader
import com.rizzi.bouquet.HorizontalPdfReaderState
import com.rizzi.bouquet.ResourceType

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

        val context = LocalContext.current

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

        BtnCustom(onClicking = {
            // on below line we are creating
            // an intent to send an email
            val i = Intent(Intent.ACTION_SEND)

            // on below line we are passing email address,
            // email subject and email body
            i.putExtra(Intent.EXTRA_EMAIL, arrayOf(userProfile.email))
            i.putExtra(Intent.EXTRA_SUBJECT, "Short Listed for Interview")
            i.putExtra(Intent.EXTRA_TEXT, "Body")

            // on below line we are
            // setting type of intent
            i.setType("message/rfc822")

            // on the below line we are starting our activity to open email application.
            context.startActivity(Intent.createChooser(i, "Choose an Email client : "))
        }, text = "Send Email", padStart = 100, padEnd = 100)

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
fun CandidateResume(viewModel: AppliedCandidateViewModel, applierId: String) {
    val resumeUrl by viewModel.resumeUrl.collectAsState()

    // pdf reader state
    val pdfHorizontalReaderState = remember {
        mutableStateOf(
            HorizontalPdfReaderState(
                resource = ResourceType.Remote(resumeUrl),
                isZoomEnable = true,
                isAccessibleEnable = true
            )
        )
    }

    Column(
        modifier = Modifier
            .background(
                color = BackgroundColor
            )
            .fillMaxSize()
    ) {
        HorizontalPDFReader(
            state = pdfHorizontalReaderState.value,
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.surfaceVariant)
        )
    }
}