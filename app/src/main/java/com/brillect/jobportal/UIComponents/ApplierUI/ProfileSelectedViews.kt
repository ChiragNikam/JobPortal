package com.brillect.jobportal.UIComponents.ApplierUI

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toFile
import androidx.documentfile.provider.DocumentFile
import com.brillect.jobportal.Applier.ApplierProfileViewModel
import com.brillect.jobportal.BtnStartSearching
import com.brillect.jobportal.Data.ApplierProfile
import com.brillect.jobportal.UIComponents.BtnCustom
import com.brillect.jobportal.UIComponents.InfoBlock
import com.brillect.jobportal.UIComponents.MultiLineTextField
import com.brillect.jobportal.UIComponents.ProgressBar
import com.brillect.jobportal.UIComponents.SingleLineTextField
import com.brillect.jobportal.UIComponents.TextCustom
import com.brillect.jobportal.UIComponents.Text_18_White
import com.brillect.jobportal.UIComponents.customTextFieldSingleLineReadOnly
import com.brillect.jobportal.ui.theme.BackgroundColor
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@Composable
fun YourProfileView(viewModel: ApplierProfileViewModel) {
    Column(
        modifier = Modifier.background(
            color = BackgroundColor,
            shape = RoundedCornerShape(8.dp)
        )
    ) {
        val userProfile by viewModel.userProfile.collectAsState()

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

        // Resume
        InfoBlock(label = "Resume", description = "")

        Spacer(modifier = Modifier.height(24.dp))

    }
}

@Composable
fun EditProfileView(viewModel: ApplierProfileViewModel) {
    val context = LocalContext.current

    // selected file
    val result = rememberSaveable { mutableStateOf<Uri?>(null) }

    val fileName = remember {
        mutableStateOf("Choose Your Resume")
    }
    // set document picker
    val singleDocPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument(),
        onResult = { file ->
            result.value = file
            // set selected file name
            val _file = getFileNameFromUri(result.value, context)
            if (_file != null) {
                fileName.value = _file
            }
        }
    )

    Column(
        modifier = Modifier.background(
            color = BackgroundColor,
            shape = RoundedCornerShape(8.dp)
        )
    ) {
        val applierProfile by viewModel.userProfile.collectAsState()

        val context = LocalContext.current

        TextCustom(textToShow = "Wants to edit your profile?", weight = 700, fontSize = 18)
        Spacer(modifier = Modifier.height(22.dp))

        // User Name
        val u_name = SingleLineTextField(description = "Full Name", applierProfile.u_name)
        Spacer(modifier = Modifier.height(24.dp))

        // Email
        val email = SingleLineTextField(description = "Email", applierProfile.email)
        Spacer(modifier = Modifier.height(24.dp))

        // Phone No
        val phoneNo = SingleLineTextField(description = "Phone No.", applierProfile.phone)
        Spacer(modifier = Modifier.height(24.dp))

        // Location
        val location = SingleLineTextField(description = "Location", applierProfile.location)
        Spacer(modifier = Modifier.height(24.dp))

        // Profile heading
        val profileHeading =
            SingleLineTextField(description = "Profile Heading", applierProfile.profileHeading)
        Spacer(modifier = Modifier.height(24.dp))

        // About Me
        val aboutMe = MultiLineTextField(description = "About Me", applierProfile.aboutMe)
        Spacer(modifier = Modifier.height(24.dp))

        // Work Experience
        val workExperience =
            MultiLineTextField(description = "Work Experience", applierProfile.workExperience)
        Spacer(modifier = Modifier.height(24.dp))

        // Education
        val education = MultiLineTextField(description = "Education", applierProfile.education)
        Spacer(modifier = Modifier.height(24.dp))

        // Skills
        val skills = MultiLineTextField(description = "Skills", applierProfile.skills)
        Spacer(modifier = Modifier.height(24.dp))

        // Resume
        Text_18_White(textToShow = "Resume", 400)
        Spacer(modifier = Modifier.height(22.dp))
        customTextFieldSingleLineReadOnly(fileName.value, Modifier.clickable {
            singleDocPickerLauncher.launch(arrayOf("application/pdf"))
        })
        // upload progress bar of resume
        var resumeUploadProgress by rememberSaveable {
            mutableStateOf(false)
        }
        // Progress bar for uploading resume
        if (resumeUploadProgress)
            ProgressBar()

        // Button to upload resume
        TextButton(onClick = {
            if (result.value != null) {
                resumeUploadProgress = true

                viewModel.uploadResume(result.value!!) { success ->
                    // if resume uploaded success fully
                    if (success) {
                        resumeUploadProgress = false
                        Toast.makeText(context, "Resume Uploaded Successfully", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            } else {
                Toast.makeText(context, "Choose a pdf", Toast.LENGTH_SHORT).show()
            }
        }, modifier = Modifier.align(Alignment.End)) {
            Text(text = "Upload")
        }

        Spacer(modifier = Modifier.height(24.dp))

        // for progress bar of update user profile
        var profileUpdateStatus by rememberSaveable {
            mutableStateOf(false)
        }

        BtnCustom(onClicking = {
            val profile = ApplierProfile(
                u_name = u_name,
                email = email,
                phone = phoneNo,
                location = location,
                profileHeading = profileHeading,
                aboutMe = aboutMe,
                workExperience = workExperience,
                education = education,
                skills = skills
            )

            if (u_name.isEmpty()) {   // validate if any important field is empty
                Toast.makeText(context, "Please enter user name.", Toast.LENGTH_SHORT).show()
            } else if (email.isEmpty()) {
                Toast.makeText(context, "Please enter user name.", Toast.LENGTH_SHORT).show()
            } else if (phoneNo.isEmpty()) {
                Toast.makeText(context, "Please enter your valid Phone No.", Toast.LENGTH_SHORT)
                    .show()
            } else if (profileHeading.isEmpty()) {
                Toast.makeText(context, "Please write a profile heading", Toast.LENGTH_SHORT).show()
            } else {    // write profile to db
                profileUpdateStatus = true
                // set or update used profile data to view model
                viewModel.updateUserProfileObj(profile)

                // upload data to the db
                viewModel.uploadProfileDetails { status, messege ->
                    profileUpdateStatus = !status
                    if (status)
                        Toast.makeText(context, "Profile Updated Successfully", Toast.LENGTH_SHORT)
                            .show()
                    if (messege.isNotEmpty())
                        Toast.makeText(context, messege, Toast.LENGTH_SHORT).show()
                }
            }
        }, text = "Update", padStart = 0, padEnd = 200)

        if (profileUpdateStatus)
            ProgressBar()

        Spacer(modifier = Modifier.height(24.dp))

    }
}

// Function to get the file name from the URI
private fun getFileNameFromUri(uri: Uri?, context: Context): String? {
    if (uri == null) {
        return null
    }

    val documentFile = DocumentFile.fromSingleUri(context, uri)
    return documentFile?.name
}

@Preview(showSystemUi = true)
@Composable
fun ConfirmLogoutView() {
    val activity = (LocalContext.current as Activity)
    Column(
        modifier = Modifier.background(
            color = BackgroundColor,
            shape = RoundedCornerShape(8.dp)
        )
    ) {
        TextCustom(textToShow = "Are you sure? Do you want to logout?", weight = 700, fontSize = 18)
        Spacer(modifier = Modifier.height(22.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp, end = 24.dp)
        ) {
            // Cancel Button, Don't want to logout
            Button(
                onClick = { },
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.weight(1f)
            ) {
                Text_18_White(textToShow = "Cancel", weight = 700)
            }
            Spacer(modifier = Modifier.width(24.dp))
            // Confirm logout button
            Button(onClick = {
                Firebase.auth.signOut()
            }, shape = RoundedCornerShape(8.dp), modifier = Modifier.weight(1f)) {
                Text_18_White(textToShow = "Ok", weight = 700)
            }
        }
    }
}