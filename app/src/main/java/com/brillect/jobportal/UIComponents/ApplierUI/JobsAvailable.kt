package com.brillect.jobportal.UIComponents.ApplierUI

import android.adservices.customaudience.CustomAudience
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.brillect.jobportal.Applier.ApplierViewModel
import com.brillect.jobportal.Data.CreateJobPost
import com.brillect.jobportal.Data.JobPostsApplier
import com.brillect.jobportal.FirebaseRead
import com.brillect.jobportal.UIComponents.BtnCustom
import com.brillect.jobportal.UIComponents.HelloUserNameProfilePhoto
import com.brillect.jobportal.UIComponents.HelloUserNameProfilePhotoClickable
import com.brillect.jobportal.UIComponents.SingleLineTextField
import com.brillect.jobportal.UIComponents.TextCustom
import com.brillect.jobportal.UIComponents.Text_18_White
import com.brillect.jobportal.UIComponents.textFontFamily
import com.brillect.jobportal.ui.theme.BackgroundColor
import com.brillect.jobportal.ui.theme.JobPortalTheme
import com.brillect.jobportal.ui.theme.PrimaryColor
import com.brillect.jobportal.ui.theme.TextFieldColor

@Composable
fun AvailableJobs(
    viewModel: ApplierViewModel,
    onClickSearch: () -> Unit,
    onImageClick: () -> Unit,
    modifierAvailCompanies: Modifier
) {
    JobPortalTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier
                .fillMaxSize(),
            color = BackgroundColor
        ) {
            Column(
                modifier = Modifier
                    .padding(start = 24.dp, top = 75.dp, end = 24.dp)
            ) {
                HelloUserNameProfilePhotoClickable {
                    onImageClick()
                }
                Spacer(modifier = Modifier.height(35.dp))
                SingleLineTextField(description = "Search by Company/Post")
                Spacer(modifier = Modifier.height(16.dp))
                BtnCustom(onClicking = {
                    onClickSearch()
                }, text = "Search", padStart = 0, padEnd = 220)
                Spacer(modifier = Modifier.height(30.dp))
                var jobList by rememberSaveable { mutableStateOf(listOf<CreateJobPost>()) }
                var showJobPost by rememberSaveable { mutableStateOf(listOf<JobPostsApplier>()) }
                val _showJobPost = mutableListOf<JobPostsApplier>()

                var companyId by rememberSaveable {
                    mutableStateOf("")
                }

                var companyName by rememberSaveable {
                    mutableStateOf("")
                }

                FirebaseRead().getJobPostsList { _jobList ->
                    jobList = _jobList
                    jobList.forEach { job ->
                        val firebaseRead = FirebaseRead()

                        firebaseRead.getCompanyId {
                            companyId = it
                            firebaseRead.getCompanyDetails(companyId) {
                                companyName = it.companyName
                                Log.d("company_name", companyName)
                            }
                        }

                        _showJobPost.add(
                            JobPostsApplier(
                                jobPosition = job.jobPosition,
                                companyName = companyName,
                                jobLocation = job.jobLocation,
                                salary = job.salary,
                                jobType = job.jobType,
                                workplace = job.workplace
                            )
                        )
                        Log.d("job_post", _showJobPost.toString())
                    }
                    showJobPost = _showJobPost
                }
                AvailableCompaniesList(modifierAvailCompanies, showJobPost)
            }
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AvailableCompaniesList(modifier: Modifier, jobList: List<JobPostsApplier>) {
    LazyColumn(verticalArrangement = Arrangement.spacedBy(22.dp)) {
        items(jobList) { job ->
            Box(
                modifier = modifier
                    .background(color = Color.Black, shape = RoundedCornerShape(8.dp))
                    .padding(bottom = 1.dp, end = 1.dp), contentAlignment = Alignment.BottomCenter
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = TextFieldColor, shape = RoundedCornerShape(8.dp))
                        .padding(16.dp)
                ) {
                    Text_18_White(textToShow = job.jobPosition)
                    Spacer(modifier = Modifier.height(12.dp))
                    TextCustom(textToShow = job.companyName, 400, 16)
                    Spacer(modifier = Modifier.height(12.dp))
                    TextCustom(textToShow = job.jobLocation, weight = 400, fontSize = 14)
                    Spacer(modifier = Modifier.height(15.dp))
                    Row(modifier = Modifier.fillMaxWidth()) {
                        OutlinedInfoText(description = job.salary)
                        Spacer(modifier = Modifier.width(12.dp))
                        OutlinedInfoText(description = job.workplace)
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    OutlinedInfoText(description = job.jobType)
                    Spacer(modifier = Modifier.height(12.dp))
                    OutlinedInfoText(description = "Posted on 16/08/2000")
                    Spacer(modifier = Modifier.height(12.dp))
                    OutlinedInfoText(description = "Expired on 25/08/2000")
                }
            }
        }
    }
}

@Composable
fun OutlinedInfoText(description: String) {
    Box(
        modifier = Modifier
            .border(1.dp, color = PrimaryColor, shape = RoundedCornerShape(10.dp))
            .padding(top = 2.dp, bottom = 2.dp, start = 16.dp, end = 16.dp)
    ) {
        TextCustom(textToShow = description, weight = 400, fontSize = 12)
    }
}