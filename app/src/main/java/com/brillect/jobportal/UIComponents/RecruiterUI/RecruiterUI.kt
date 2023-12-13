package com.brillect.jobportal.UIComponents.RecruiterUI

import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.brillect.jobportal.UIComponents.BtnCustom
import com.brillect.jobportal.UIComponents.HelloUserNameProfilePhoto
import com.brillect.jobportal.ui.theme.BackgroundColor
import com.brillect.jobportal.ui.theme.JobPortalTheme

@Preview(showSystemUi = true)
@Composable
fun RecruiterUI() {
    JobPortalTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            color = BackgroundColor
        ) {
            // for remembering the state and opening the view accordingly
            val selectedState = remember { mutableStateOf(0) }
            Column(modifier = Modifier.padding(start = 24.dp, top = 75.dp, end = 24.dp)) {
                HelloUserNameProfilePhoto() // Top Bar with User name and
                Spacer(modifier = Modifier.height(24.dp))
                BtnCustom(onClicking = {
                    selectedState.value = 1
                }, "Create Job Post", 45, 67)
                Spacer(modifier = Modifier.height(10.dp))
                BtnCustom(onClicking = {
                    selectedState.value = 2
                }, text = "Applications", 45, 67)
                Spacer(modifier = Modifier.height(10.dp))
                BtnCustom(onClicking = {
                    selectedState.value = 3
                }, text = "Company Profile", 45, 67)
                Spacer(modifier = Modifier.height(24.dp))
                Surface(
                    modifier = Modifier
                        .fillMaxSize(),
                    color = BackgroundColor
                ) {
                    if (selectedState.value == 1) {
                        JobPostForm()
                    } else if(selectedState.value == 2){
                        ApplicantsInfo()
                    } else if(selectedState.value == 3){
                        CompanyProfile()
                    }
                }
            }
        }
    }
}
