package com.brillect.jobportal.UIComponents.RecruiterUI

import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.brillect.jobportal.Data.CreateJobPost
import com.brillect.jobportal.Data.JobType
import com.brillect.jobportal.Data.WorkPlace
import com.brillect.jobportal.Recruiter.RecruiterViewModel
import com.brillect.jobportal.UIComponents.BtnCustom
import com.brillect.jobportal.UIComponents.CustomTextFieldWithDropdownJobType
import com.brillect.jobportal.UIComponents.MultiLineTextField
import com.brillect.jobportal.UIComponents.SingleLineTextField
import com.brillect.jobportal.UIComponents.Text_18_White
import com.brillect.jobportal.ui.theme.TextFieldColor
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@Composable
fun JobPostForm(viewModelJobPost: RecruiterViewModel) {

    val activity = (LocalContext.current as Activity)

    Column(horizontalAlignment = Alignment.Start) {
        Text_18_White(textToShow = "Wants to create job post?")
        Spacer(modifier = Modifier.height(24.dp))
        val jobPosition = SingleLineTextField(description = "Job Position*")
        Spacer(modifier = Modifier.height(22.dp))
        val jobDescription = MultiLineTextField(description = "Job Description")
        Spacer(modifier = Modifier.height(22.dp))
        val requirements = MultiLineTextField(description = "Requirements*")
        Spacer(modifier = Modifier.height(22.dp))
        val facilitiesAndOther = MultiLineTextField(description = "Facilities & Other")
        Spacer(modifier = Modifier.height(16.dp))
        Spacer(
            modifier = Modifier
                .height(1.dp)
                .background(TextFieldColor)
        )
        Spacer(modifier = Modifier.height(22.dp))
        val jobLocation = SingleLineTextField(description = "Job Location*")
        Spacer(modifier = Modifier.height(22.dp))
        val salary = SingleLineTextField(description = "Salary in Rupee per year*")
        Spacer(modifier = Modifier.height(22.dp))

        // drop down for job type
        // Create a mutable state to hold the selected job type
        var selectedJobType by rememberSaveable { mutableStateOf(JobType.FULL_TIME) }
        Column(modifier = Modifier.fillMaxWidth()) {
            Text_18_White(textToShow = "Select Job Type", 400)
            Spacer(modifier = Modifier.height(22.dp))

            // dropdown implementation
            CustomTextFieldWithDropdownJobType(
                items = JobType.values().toList(),
                selectedItem = selectedJobType,
                onItemSelected = { selectedJobType = it },
                extractLabel = { it.label }
            )
            Log.d("label", "label is : ${selectedJobType.label}")
        }

        Spacer(modifier = Modifier.height(22.dp))

        // drop down for workplace
        // Create a mutable state to hold the selected job type
        var workPlace by rememberSaveable { mutableStateOf(WorkPlace.ON_SITE) }
        Column(modifier = Modifier.fillMaxWidth()) {
            Text_18_White(textToShow = "Select type of workplace", 400)
            Spacer(modifier = Modifier.height(22.dp))

            // dropdown implementation
            CustomTextFieldWithDropdownJobType(
                items = WorkPlace.values().toList(),
                selectedItem = workPlace,
                onItemSelected = { workPlace = it },
                extractLabel = { it.label }
            )
            Log.d("label", "label is : ${workPlace.label}")
        }
        Spacer(modifier = Modifier.height(16.dp))

        // button to finally create job post
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 112.dp)
        ) {
            BtnCustom(onClicking = {
                val jobPost = CreateJobPost(
                    Firebase.auth.uid.toString(),
                    jobPosition,
                    jobDescription,
                    requirements,
                    facilitiesAndOther,
                    jobLocation,
                    salary,
                )
                // validate form
                val result = viewModelJobPost.validateJobPostDetails(jobPost)
                if (result == "yes") {
                    // write job post data to realtime db
                    viewModelJobPost.writeJobPostToRealDb(jobPost)
                } else{
                    Toast.makeText(activity, result, Toast.LENGTH_SHORT).show()
                }
            }, text = "Create Job", 0, 112)
        }
        Spacer(modifier = Modifier.height(200.dp))
    }
}