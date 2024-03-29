package com.brillect.jobportal.UIComponents.RecruiterUI

import android.app.Activity
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.brillect.jobportal.Auth.Login
import com.brillect.jobportal.R
import com.brillect.jobportal.Recruiter.RecruiterViewModel
import com.brillect.jobportal.UIComponents.BtnCustom
import com.brillect.jobportal.UIComponents.HelloUserNameProfilePhoto
import com.brillect.jobportal.ui.theme.BackgroundColor
import com.brillect.jobportal.ui.theme.JobPortalTheme
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@Composable
fun RecruiterUI(viewModel: RecruiterViewModel) {

    val selectedState =
        viewModel.selectedState.collectAsState(initial = 1) // selected state for views

    Column(
        modifier = Modifier
            .background(BackgroundColor)
            .padding(start = 24.dp, end = 24.dp)
            .verticalScroll(rememberScrollState())
    ) {
        viewModel.getFirstName("recruiter")

        Spacer(modifier = Modifier.height(75.dp))
        BtnCustom(onClicking = {
            viewModel._selectedState.value = 1
        }, "Applications", 45, 67)
        Spacer(modifier = Modifier.height(10.dp))
        BtnCustom(onClicking = {
            viewModel._selectedState.value = 2
        }, text = "Create Job Post", 45, 67)
        Spacer(modifier = Modifier.height(10.dp))
        BtnCustom(onClicking = {
            viewModel._selectedState.value = 3
        }, text = "Company Profile", 45, 67)
        Spacer(modifier = Modifier.height(24.dp))
        Surface(
            modifier = Modifier
                .fillMaxSize(),
            color = BackgroundColor
        ) {
            if (selectedState.value == 1) {
                ApplicantsInfo(viewModel)
            } else if (selectedState.value == 2) {
                JobPostForm(viewModel)
            } else if (selectedState.value == 3) {
                CompanyProfile(viewModel)
            }
        }

    }
}

@Composable
fun LogoutDialog(showDialog: MutableState<Boolean>) {
    val activity = (LocalContext.current as Activity)

    AlertDialog(
        onDismissRequest = {
            // Dismiss the dialog when the user clicks outside the dialog or on the back
            // button.
            showDialog.value = !showDialog.value
        },
        title = { Text(text = stringResource(R.string.title_dialog_logout)) },
        text = { Text(text = stringResource(R.string.text_dialog_logout)) },
        modifier = Modifier,
        dismissButton = {
            TextButton(
                onClick = {
                    showDialog.value = !showDialog.value
                }
            ) {
                Text(text = "Cancel")
            }
        },
        confirmButton = {
            TextButton(onClick = {
                Firebase.auth.signOut()
                activity.finish()
                val intent = Intent(activity, Login::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                activity.startActivity(intent)
            }) {
                Text(text = "Logout")
            }
        }
    )
}
