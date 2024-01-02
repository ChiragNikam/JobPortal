package com.brillect.jobportal.UIComponents.ApplierUI

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.brillect.jobportal.Applier.ApplierViewModel
import com.brillect.jobportal.Data.Application
import com.brillect.jobportal.Data.Company
import com.brillect.jobportal.Data.CreateJobPost
import com.brillect.jobportal.Data.JobPostsApplier
import com.brillect.jobportal.Data.MyDateFormat
import com.brillect.jobportal.FirebaseRead
import com.brillect.jobportal.FirebaseWrite
import com.brillect.jobportal.R
import com.brillect.jobportal.UIComponents.BtnCustom
import com.brillect.jobportal.UIComponents.InfoBlock
import com.brillect.jobportal.UIComponents.RecruiterUI.CompanyDetails
import com.brillect.jobportal.UIComponents.TextCustom
import com.brillect.jobportal.UIComponents.textFontFamily
import com.brillect.jobportal.ui.theme.BackgroundColor
import com.brillect.jobportal.ui.theme.JobPortalTheme
import com.brillect.jobportal.ui.theme.PrimaryColor
import com.brillect.jobportal.ui.theme.TextFieldColor
import java.util.Calendar

@Composable
fun JobCompanyDesc(viewModel: ApplierViewModel, jobId: String, onBack: () -> Unit) {
    val jobDetails by viewModel.jobDetails.collectAsState()
    val jobDescription by viewModel.jobDetails.collectAsState()
    val companyDetails by viewModel.companyDetails.collectAsState()

    // Get the current context
    val context = LocalContext.current

    JobPortalTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            color = BackgroundColor
        ) {
            val clickedState = remember { mutableIntStateOf(1) }
            Column(modifier = Modifier.padding(start = 24.dp, top = 75.dp, end = 24.dp)) {
                // go back
                SimpleBack { onBack() }
                Spacer(modifier = Modifier.height(26.dp))

                // show details of clicked job in this view
                AvailableCompanyView(
                    modifier = Modifier,
                    job = JobPostsApplier(
                        jobType = jobDetails.jobType,
                        jobPosition = jobDetails.jobPosition,
                        jobLocation = jobDetails.jobLocation,
                        salary = jobDetails.salary,
                        workplace = jobDetails.workplace
                    )
                )

                Spacer(modifier = Modifier.height(24.dp))

                BtnCustom(  // Job Description Button
                    onClicking = { clickedState.intValue = 1 },
                    text = "Job Description",
                    padStart = 45,
                    padEnd = 67
                )
                Spacer(modifier = Modifier.height(10.dp))

                BtnCustom(  // Company Details Button
                    onClicking = { clickedState.intValue = 2 },
                    text = "Company Details",
                    padStart = 45,
                    padEnd = 67
                )
                Spacer(modifier = Modifier.height(25.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(TextFieldColor)
                ) { }
                Surface(
                    modifier = Modifier.fillMaxSize(), color = BackgroundColor
                ) {
                    if (clickedState.intValue == 1) {   // if clicked Job Description
                        JobDescription(jobDescription)
                    } else if (clickedState.intValue == 2) {    // if clicked Company Details
                        CompanyDetailsApplier(companyDetails)
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
                BtnCustom(onClicking = {
                    val calendar = Calendar.getInstance()
                    val dd = calendar.get(Calendar.DATE)
                    val mm = calendar.get(Calendar.MONTH) + 1
                    val yyyy = calendar.get(Calendar.YEAR)

                    FirebaseWrite().writeApplicationToJobPost(
                        jobPostId = jobDetails.jobPostId,
                        Application(
                            jobPostId = jobDetails.jobPostId,
                            applicationDate = MyDateFormat(dd, mm, yyyy)
                        )
                    )

                    Toast.makeText(context, "Application Saved Successfully", Toast.LENGTH_SHORT).show()
                }, text = "Apply", padStart = 70, padEnd = 70)
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
fun JobDescription(detailsToShow: CreateJobPost) {
    Column(horizontalAlignment = Alignment.Start) {
        Spacer(modifier = Modifier.height(22.dp))
        InfoBlock(label = "Job Description", description = detailsToShow.jobDescription)
        Spacer(modifier = Modifier.height(24.dp))
        InfoBlock(label = "Requirements", description = detailsToShow.requirements)
        Spacer(modifier = Modifier.height(24.dp))
        InfoBlock(label = "Location", description = detailsToShow.jobLocation)
        Spacer(modifier = Modifier.height(24.dp))
        InfoBlock(label = "Facilities & Others", description = detailsToShow.facilitiesAndOther)
        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
fun SimpleBack(onBack: () -> Unit) {
    // to go back
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable { onBack() }) {
            Image(
                painter = painterResource(id = R.drawable.icon_back),
                contentDescription = "Back Button",
                modifier = Modifier
                    .height(24.dp)
                    .width(24.dp),
                contentScale = ContentScale.FillBounds
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = "Back", color = PrimaryColor, fontSize = 18.sp, fontFamily = textFontFamily
            )
        }
    }
}

@Composable
fun CompanyDetailsApplier(companyDetails: Company) {
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

}