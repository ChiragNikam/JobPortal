package com.brillect.jobportal.UIComponents.ApplierUI

import android.adservices.customaudience.CustomAudience
import android.content.Intent
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.brillect.jobportal.Applier.ApplierViewModel
import com.brillect.jobportal.Applier.JobCompanyDescription
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
    onImageClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(start = 24.dp, end = 24.dp)
    ) {
        var query by rememberSaveable {
            mutableStateOf("")
        }

        // for search result
        LaunchedEffect(query){
            viewModel.updateFilteredJobPosts(query)
        }

        Spacer(modifier = Modifier.height(60.dp))
        HelloUserNameProfilePhotoClickable {
            onImageClick()
        }
        Spacer(modifier = Modifier.height(35.dp))

        query = SingleLineTextField(description = "Search by Post")
        Spacer(modifier = Modifier.height(16.dp))

        Spacer(modifier = Modifier.height(30.dp))

        // progress bar
        val progressIndicator by viewModel.progressIndicator.collectAsState()
        if (progressIndicator) {    // only show if content is loaded
            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(alignment = Alignment.CenterHorizontally),
                trackColor = BackgroundColor
            )
        }

        // load all available jobs
        AvailableCompaniesList(viewModel)
    }
}

@Composable
fun AvailableCompaniesList(viewModel: ApplierViewModel) {
    // Get the current context
    val context = LocalContext.current

    val jobList by viewModel.filteredJobPosts.collectAsState()

    Log.d("job_list", jobList.toString())
    LazyColumn(
        contentPadding = PaddingValues(bottom = 18.dp),
        verticalArrangement = Arrangement.spacedBy(22.dp),
        modifier = Modifier.height((jobList.size * 210).dp),
        userScrollEnabled = false
    ) {
        items(jobList) { job ->
            AvailableCompanyView(modifier = Modifier.clickable {
                // show complete details of job
                context.startActivity(Intent(context, JobCompanyDescription::class.java).apply {
                    putExtra("job_id", job.jobId)
                    Log.d("job_id", job.jobId)
                })
            }, job = job, viewModel)
        }
    }

    DisposableEffect(key1 = Unit) {
        viewModel.loadJobPosts()
        onDispose {
        }
    }
}

@Composable
fun AvailableCompanyView(
    modifier: Modifier,
    job: JobPostsApplier,
    viewModel: ApplierViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    Box(
        modifier = modifier
            .background(color = Color.Black, shape = RoundedCornerShape(8.dp))
            .padding(bottom = 1.dp, end = 1.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = TextFieldColor, shape = RoundedCornerShape(8.dp))
                .padding(16.dp)
        ) {
            Text_18_White(textToShow = job.jobPosition)
            Spacer(modifier = Modifier.height(12.dp))

            // getting company name
            var companyName by rememberSaveable {
                mutableStateOf("")
            }
            viewModel.getCompanyName(job.jobId) { name ->
                companyName = name
            }

            // company name
            TextCustom(textToShow = companyName, 400, 16)
            Spacer(modifier = Modifier.height(12.dp))
            // job location
            TextCustom(textToShow = job.jobLocation, weight = 400, fontSize = 14)
            Spacer(modifier = Modifier.height(15.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                OutlinedInfoText(description = job.salary)
                Spacer(modifier = Modifier.width(12.dp))
                OutlinedInfoText(description = job.workplace)
            }
            Spacer(modifier = Modifier.height(12.dp))
            OutlinedInfoText(description = job.jobType)
        }
    }
}

@Composable
fun OutlinedInfoText(description: String) {
    Box(
        modifier = Modifier
            .border(
                1.dp,
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(10.dp)
            )
            .padding(top = 2.dp, bottom = 2.dp, start = 16.dp, end = 16.dp)
    ) {
        TextCustom(textToShow = description, weight = 400, fontSize = 12)
    }
}