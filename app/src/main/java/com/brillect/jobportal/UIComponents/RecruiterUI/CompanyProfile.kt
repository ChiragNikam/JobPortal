package com.brillect.jobportal.UIComponents.RecruiterUI

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.brillect.jobportal.Recruiter.RecruiterViewModel
import com.brillect.jobportal.UIComponents.BtnCustom
import com.brillect.jobportal.UIComponents.InfoBlock
import com.brillect.jobportal.UIComponents.MultiLineTextField
import com.brillect.jobportal.UIComponents.SingleLineTextField
import com.brillect.jobportal.UIComponents.Text_18_White
import com.brillect.jobportal.ui.theme.BackgroundColor
import com.brillect.jobportal.ui.theme.TextFieldColor

@Composable
fun CompanyProfile(viewModel: RecruiterViewModel) {
    Column(horizontalAlignment = Alignment.Start) {
        var selectedState = viewModel.selectedStateCompany.collectAsState(initial = 1)
        Spacer(modifier = Modifier.height(24.dp))
        BtnCustom(
            onClicking = {
                viewModel._selectedStateCompany.value = 1
            },
            text = "Create Company",
            padStart = 45,
            padEnd = 67
        )
        Spacer(modifier = Modifier.height(10.dp))
        BtnCustom(
            onClicking = {
                viewModel._selectedStateCompany.value = 2
            },
            text = "Company Details",
            padStart = 45,
            padEnd = 67
        )
        Spacer(modifier = Modifier.height(24.dp))
        Column(
            modifier = Modifier
                .height(1.dp)
                .background(TextFieldColor)
                .fillMaxWidth()
        ) {}
        Spacer(modifier = Modifier.height(3.dp))
        Surface(modifier = Modifier.fillMaxSize(), color = BackgroundColor) {
            if (selectedState.value == 1) {
                CreateCompany()
            } else if (selectedState.value == 2) {
                CompanyDetails()
            }
        }
    }
}

@Composable
fun CreateCompany() {
    Column(horizontalAlignment = Alignment.Start) {
        Text_18_White(textToShow = "Wants to create company?")
        Spacer(modifier = Modifier.height(32.dp))
        SingleLineTextField(description = "Company Logo Upload")
        Spacer(modifier = Modifier.height(22.dp))
        SingleLineTextField(description = "Company Name")
        Spacer(modifier = Modifier.height(22.dp))
        MultiLineTextField(description = "About Company")
        Spacer(modifier = Modifier.height(22.dp))
        SingleLineTextField(description = "Website")
        Spacer(modifier = Modifier.height(22.dp))
        SingleLineTextField(description = "Industry")
        Spacer(modifier = Modifier.height(22.dp))
        SingleLineTextField(description = "Employee Size")
        Spacer(modifier = Modifier.height(22.dp))
        SingleLineTextField(description = "Head Office")
        Spacer(modifier = Modifier.height(22.dp))
        SingleLineTextField(description = "Type")
        Spacer(modifier = Modifier.height(22.dp))
        SingleLineTextField(description = "Since")  // Apply Date Picker
        Spacer(modifier = Modifier.height(22.dp))
        MultiLineTextField(description = "Specialization")
        Spacer(modifier = Modifier.height(24.dp))
        BtnCustom(onClicking = { }, text = "Create Company", padStart = 0, padEnd = 152)
        Spacer(modifier = Modifier.height(100.dp))
    }
}

@Composable
fun CompanyDetails() {
    val description =
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. "
    Column(horizontalAlignment = Alignment.Start) {
        Spacer(modifier = Modifier.height(22.dp))
        InfoBlock(label = "About Company", description = description)
        Spacer(modifier = Modifier.height(24.dp))
        BtnCustom(onClicking = {  }, text = "Remove Company", padStart = 0, padEnd = 152)
        Spacer(modifier = Modifier.height(150.dp))
    }
}