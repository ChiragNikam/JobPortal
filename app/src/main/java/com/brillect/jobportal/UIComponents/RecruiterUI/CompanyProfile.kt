package com.brillect.jobportal.UIComponents.RecruiterUI

import android.app.Activity
import android.util.Log
import android.widget.Toast
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.brillect.jobportal.Data.Company
import com.brillect.jobportal.FirebaseRead
import com.brillect.jobportal.FirebaseWrite
import com.brillect.jobportal.Recruiter.RecruiterViewModel
import com.brillect.jobportal.UIComponents.BtnCustom
import com.brillect.jobportal.UIComponents.InfoBlock
import com.brillect.jobportal.UIComponents.MultiLineTextField
import com.brillect.jobportal.UIComponents.SingleLineTextField
import com.brillect.jobportal.UIComponents.TextCustom
import com.brillect.jobportal.UIComponents.Text_18_White
import com.brillect.jobportal.ui.theme.BackgroundColor
import com.brillect.jobportal.ui.theme.TextFieldColor

@Composable
fun CompanyProfile(viewModel: RecruiterViewModel) {
    Column(horizontalAlignment = Alignment.Start) {
        val selectedState = viewModel.selectedStateCompany.collectAsState(initial = 1)
        Spacer(modifier = Modifier.height(24.dp))
        BtnCustom(
            onClicking = {
                viewModel._selectedStateCompany.value = 2
            },
            text = "Company Details",
            padStart = 45,
            padEnd = 67
        )
        Spacer(modifier = Modifier.height(10.dp))
        BtnCustom(
            onClicking = {
                viewModel._selectedStateCompany.value = 1

            },
            text = "Create Company",
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
                Column {
                    CompanyDetails()
                    Spacer(modifier = Modifier.height(84.dp))
                }
            }
        }
    }
}

@Composable
fun CreateCompany() {

    val activity = (LocalContext.current as Activity)

    Column(horizontalAlignment = Alignment.Start) {
        Text_18_White(textToShow = "Wants to create company?")
        Spacer(modifier = Modifier.height(32.dp))
        val companyLogoUrl = SingleLineTextField(description = "Company Logo Upload*")
        Spacer(modifier = Modifier.height(22.dp))
        val companyName = SingleLineTextField(description = "Company Name*")
        Spacer(modifier = Modifier.height(22.dp))
        val aboutCompany = MultiLineTextField(description = "About Company*")
        Spacer(modifier = Modifier.height(22.dp))
        val website = SingleLineTextField(description = "Website*")
        Spacer(modifier = Modifier.height(22.dp))
        val industry = SingleLineTextField(description = "Industry")    // tech or non-tech
        Spacer(modifier = Modifier.height(22.dp))
        val empSize = SingleLineTextField(description = "Employee Size")
        Spacer(modifier = Modifier.height(22.dp))
        val headOffice = SingleLineTextField(description = "Head Office")
        Spacer(modifier = Modifier.height(22.dp))
        val since = SingleLineTextField(description = "Since")  // Apply Date Picker
        Spacer(modifier = Modifier.height(22.dp))
        val specialization = MultiLineTextField(description = "Specialization")
        Spacer(modifier = Modifier.height(24.dp))
        BtnCustom(onClicking = {
            val companyDetails = Company(
                companyLogoUrl,
                companyName,
                aboutCompany,
                website,
                industry,
                empSize,
                headOffice,
                since, specialization
            )
            val validationResult = RecruiterViewModel().validateCreateCompany(
                companyDetails
            )
            if (validationResult == "yes") {
                FirebaseWrite().writeCompanyDetails(companyDetails)
                Toast.makeText(activity, "Company Details Saved Successfully", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(activity, validationResult, Toast.LENGTH_SHORT).show()
            }
        }, text = "Create Company", padStart = 0, padEnd = 152)
        Spacer(modifier = Modifier.height(100.dp))
    }
}

@Composable
fun CompanyDetails() {
    var companyDetails by remember { mutableStateOf(Company()) }
    var companyAvailable by rememberSaveable {
        mutableStateOf(false)
    }
    FirebaseRead().getCompany { isCompanyAvailable, details ->
        if (isCompanyAvailable == "yes") {
            companyAvailable = true
            companyDetails = details
        } else{
            companyAvailable = false
        }
    }
    if (companyAvailable) {
        Column(horizontalAlignment = Alignment.Start) {
            Spacer(modifier = Modifier.height(22.dp))
            InfoBlock(label = "Name", description = companyDetails.companyName)
            Spacer(modifier = Modifier.height(22.dp))
            InfoBlock(label = "About Company", description = companyDetails.aboutCompany)
            Spacer(modifier = Modifier.height(22.dp))
            InfoBlock(label = "Industry", description = companyDetails.website)
            Spacer(modifier = Modifier.height(22.dp))
            InfoBlock(label = "Employee Size", description = companyDetails.employeeSize)
            Spacer(modifier = Modifier.height(22.dp))
            InfoBlock(label = "Head Office", description = companyDetails.headOffice)
            Spacer(modifier = Modifier.height(22.dp))
            InfoBlock(label = "Since", description = companyDetails.since)
            Spacer(modifier = Modifier.height(22.dp))
            InfoBlock(label = "Specialization", description = companyDetails.specialization)
            Spacer(modifier = Modifier.height(22.dp))
            InfoBlock(label = "Website", description = companyDetails.website)
            Spacer(modifier = Modifier.height(24.dp))

        }
    } else{

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.height(24.dp))
            TextCustom(
                textToShow = "You had not added company yet. Create Company first.",
                weight = 400,
                fontSize = 20
            )
        }

    }
}