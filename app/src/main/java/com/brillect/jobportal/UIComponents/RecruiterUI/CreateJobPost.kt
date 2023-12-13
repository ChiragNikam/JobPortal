package com.brillect.jobportal.UIComponents.RecruiterUI

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.brillect.jobportal.UIComponents.BtnCustom
import com.brillect.jobportal.UIComponents.MultiLineTextField
import com.brillect.jobportal.UIComponents.SingleLineTextField
import com.brillect.jobportal.UIComponents.Text_18_White
import com.brillect.jobportal.ui.theme.TextFieldColor

@Preview(showSystemUi = true)
@Composable
fun JobPostForm() {
    Column(horizontalAlignment = Alignment.Start) {
        Text_18_White(textToShow = "Wants to create job post?")
        Spacer(modifier = Modifier.height(24.dp))
        SingleLineTextField(description = "Job Position")
        Spacer(modifier = Modifier.height(22.dp))
        MultiLineTextField(description = "Job Description")
        Spacer(modifier = Modifier.height(22.dp))
        MultiLineTextField(description = "Requirements")
        Spacer(modifier = Modifier.height(22.dp))
        MultiLineTextField(description = "Facilities & Other")
        Spacer(modifier = Modifier.height(16.dp))
        Spacer(
            modifier = Modifier
                .height(1.dp)
                .background(TextFieldColor)
        )
        Spacer(modifier = Modifier.height(22.dp))
        SingleLineTextField(description = "Job Location")
        Spacer(modifier = Modifier.height(22.dp))
        SingleLineTextField(description = "Salary in Rupee per year")
        Spacer(modifier = Modifier.height(22.dp))
        SingleLineTextField(description = "Select Job Type")
        Spacer(modifier = Modifier.height(22.dp))
        SingleLineTextField(description = "Select type of workplace")
        Spacer(modifier = Modifier.height(16.dp))
        Column(modifier = Modifier.fillMaxWidth().padding(end = 112.dp)) {
            BtnCustom(onClicking = { }, text = "Create Job", 0, 112)
        }
        Spacer(modifier = Modifier.height(200.dp))
    }
}