package com.brillect.jobportal.UIComponents.RecruiterUI

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.brillect.jobportal.Data.Company
import com.brillect.jobportal.Recruiter.RecruiterViewModel
import com.brillect.jobportal.UIComponents.BtnCustom
import com.brillect.jobportal.UIComponents.MultiLineTextField
import com.brillect.jobportal.UIComponents.ProgressBar
import com.brillect.jobportal.UIComponents.SingleLineTextField
import com.brillect.jobportal.ui.theme.BackgroundColor

@Composable
fun EditCompanyBottomSheet(viewModel: RecruiterViewModel) {
    val context = LocalContext.current

    val companyDetails by viewModel.updateCompanyDetails.collectAsState()

    val updatedCompanyDetails = Company()

    val isCompanyUpdated = rememberSaveable {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
            .padding(start = 24.dp, end = 24.dp)
            .verticalScroll(
                rememberScrollState()
            ), verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {

        updatedCompanyDetails.companyLogo =
            SingleLineTextField(
                description = "Company Logo Link",
                textSingleLine = companyDetails.companyLogo
            )

        updatedCompanyDetails.companyName =
            SingleLineTextField(
                description = "Company Name",
                textSingleLine = companyDetails.companyName
            )

        updatedCompanyDetails.aboutCompany =
            MultiLineTextField(
                description = "About Company",
                textMultiLine = companyDetails.aboutCompany
            )

        updatedCompanyDetails.website =
            SingleLineTextField(description = "Website", textSingleLine = companyDetails.website)

        updatedCompanyDetails.industry =
            SingleLineTextField(description = "Industry", textSingleLine = companyDetails.industry)

        updatedCompanyDetails.employeeSize =
            SingleLineTextField(
                description = "Employee Size",
                textSingleLine = companyDetails.employeeSize
            )

        updatedCompanyDetails.headOffice =
            SingleLineTextField(
                description = "Head Office",
                textSingleLine = companyDetails.headOffice
            )

        updatedCompanyDetails.since =
            SingleLineTextField(description = "Since", textSingleLine = companyDetails.since)

        updatedCompanyDetails.specialization = MultiLineTextField(
            description = "Specialization",
            textMultiLine = companyDetails.specialization
        )

        Spacer(modifier = Modifier.height(10.dp))

        // update company details
        BtnCustom(onClicking = {
            isCompanyUpdated.value = true

            viewModel.updateCompanyDetails(updatedDetails = updatedCompanyDetails) { updateSucess ->
                isCompanyUpdated.value = false

                if (!updateSucess) {
                    Toast.makeText(
                        context,
                        "Update Failed! Please try again after sometime",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }, text = "Update", padStart = 34, padEnd = 34)

        if(isCompanyUpdated.value)
            ProgressBar()

        Spacer(modifier = Modifier.height(24.dp))
    }

}