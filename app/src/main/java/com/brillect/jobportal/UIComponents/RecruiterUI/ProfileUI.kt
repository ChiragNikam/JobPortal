package com.brillect.jobportal.UIComponents.RecruiterUI

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.brillect.jobportal.Data.CreateJobPost
import com.brillect.jobportal.R
import com.brillect.jobportal.Recruiter.RecruiterProfileViewModel
import com.brillect.jobportal.UIComponents.ApplierUI.OutlinedInfoText
import com.brillect.jobportal.UIComponents.TextCustom
import com.brillect.jobportal.UIComponents.Text_18_White
import com.brillect.jobportal.UIComponents.textFontFamily
import com.brillect.jobportal.ui.theme.BackgroundColor
import com.brillect.jobportal.ui.theme.TextFieldColor
@Composable
fun ProfileAndHistoryUI(viewModel: RecruiterProfileViewModel) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = BackgroundColor)
            .padding(start = 24.dp, end = 24.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(74.dp))

        val profileData by viewModel.adminProfileCardDetails.collectAsState()

        RecruiterInfoCard(
            companyName = profileData.companyName,
            userName = profileData.userName,
            email = profileData.email
        )

        Spacer(modifier = Modifier.height(24.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                modifier = Modifier.padding(start = 14.dp),
                text = "JobPosts",
                fontSize = MaterialTheme.typography.labelMedium.fontSize,
                fontFamily = MaterialTheme.typography.labelMedium.fontFamily,
                fontWeight = MaterialTheme.typography.labelMedium.fontWeight,
                color = Color.White
            )
            Spacer(modifier = Modifier.width(10.dp))
        }

        // list of all created job posts
        val jobPostsList by viewModel.listOfJobPostsHistory.collectAsState()
        ListOfJobPosts(jobPostsList, viewModel)

    }
}

@Composable
fun ListOfJobPosts(jobPostsList: List<CreateJobPost>, viewModel: RecruiterProfileViewModel) {

    LazyColumn(
        modifier = Modifier.height(((jobPostsList.size * 190) + (jobPostsList.size * 15)).dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(top = 10.dp, bottom = 10.dp),
        userScrollEnabled = false
    ) {
        items(jobPostsList) { jobPost ->
            JobPostsViews(jobPost, viewModel = viewModel)
        }
    }
}

@Composable
fun JobPostsViews(jobPost: CreateJobPost, viewModel: RecruiterProfileViewModel) {
    // Get local density from composable
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .background(color = Color.Black, shape = RoundedCornerShape(8.dp))
            .padding(bottom = 1.dp, end = 1.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = TextFieldColor, shape = RoundedCornerShape(8.dp))
                .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 8.dp)
        ) {
            Text_18_White(textToShow = jobPost.jobPosition)
            Spacer(modifier = Modifier.height(12.dp))

            // job location
            TextCustom(textToShow = jobPost.jobLocation, weight = 400, fontSize = 14)
            Spacer(modifier = Modifier.height(15.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                // salary
                OutlinedInfoText(description = jobPost.salary)
                Spacer(modifier = Modifier.width(12.dp))
                // workplace
                OutlinedInfoText(description = jobPost.workplace)
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedInfoText(description = jobPost.jobType)
                Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.End) {
                    TextButton(onClick = {  // delete job post
                        viewModel.deleteJobPost(jobPost.jobPostId){isDeleted->
                            if (isDeleted)
                                Toast.makeText(context, "Post deleted successfully", Toast.LENGTH_SHORT).show()
                            else
                                Toast.makeText(context, "Unable to delete Post", Toast.LENGTH_SHORT).show()
                        }
                    }) {
                        Text(
                            text = "Delete",
                            fontWeight = FontWeight(700),
                            fontFamily = textFontFamily
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun RecruiterInfoCard(companyName: String, userName: String, email: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black, shape = RoundedCornerShape(8.dp))
            .padding(bottom = 1.dp, end = 1.dp)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .background(TextFieldColor, shape = RoundedCornerShape(8.dp))
                .padding(16.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.icon_account),
                contentDescription = "Companies",
                modifier = Modifier
                    .height(48.dp)
                    .width(48.dp)
                    .clip(CircleShape)
                    .clickable { }
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                // company name
                TextCustom(textToShow = companyName, weight = 700, fontSize = 18)
                Spacer(modifier = Modifier.height(10.dp))
                // recruiter name and email
                TextCustom(
                    textToShow = userName,
                    weight = 400,
                    fontSize = 14
                )
                TextCustom(
                    textToShow = email,
                    weight = 400,
                    fontSize = 10
                )
            }
        }
    }
}
