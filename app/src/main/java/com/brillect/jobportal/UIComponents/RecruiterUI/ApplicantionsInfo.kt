package com.brillect.jobportal.UIComponents.RecruiterUI

import android.util.Log
import android.widget.ImageButton
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.brillect.jobportal.Data.AppliersByJob
import com.brillect.jobportal.R
import com.brillect.jobportal.Recruiter.RecruiterViewModel
import com.brillect.jobportal.UIComponents.BtnCustom
import com.brillect.jobportal.UIComponents.SingleLineTextField
import com.brillect.jobportal.UIComponents.TextCustom
import com.brillect.jobportal.UIComponents.Text_18_White
import com.brillect.jobportal.ui.theme.BackgroundColor
import com.brillect.jobportal.ui.theme.PrimaryColor

@Preview(showSystemUi = true)
@Composable
fun ApplicantsInfo(viewModel: RecruiterViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    Column(horizontalAlignment = Alignment.Start) {
        Spacer(modifier = Modifier.height(22.dp))
        SingleLineTextField(description = "Search Applicants")
        Spacer(modifier = Modifier.height(16.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 112.dp)
        ) {
            BtnCustom(onClicking = { }, text = "Search", 0, 112)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Spacer(
            modifier = Modifier
                .height(2.dp)
                .background(color = Color.Black, shape = RectangleShape)
        )

        // observe and load all available applications to the job post
        val listOfAppliersByJob by viewModel.appliedCandidatesToJobList.collectAsState()
        Log.d("applier_details", listOfAppliersByJob.toString())
        for (applier in listOfAppliersByJob) {  // bug - view's getting inserted multiple times when data changes, every time in realtime db
            ApplicantDetails(viewModel, applier)
        }
        Spacer(modifier = Modifier.height(40.dp))

    }
}

@Composable
fun ApplicantDetails(viewModel: RecruiterViewModel, appliersByJob: AppliersByJob) {
    Spacer(modifier = Modifier.height(20.dp))
    // Job Position
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        TextCustom(textToShow = appliersByJob.jobName, weight = 700, fontSize = 20)
    }
    Spacer(modifier = Modifier.height(8.dp))
    // Applier's Details
    for (applier in appliersByJob.listOfAppliers) {
        Row(
            modifier = Modifier
                .background(
                    color = Color(0xFF2B2B2B),
                    shape = RoundedCornerShape(size = 8.dp)
                )
                .padding(start = 14.dp, top = 23.dp, bottom = 23.dp, end = 16.dp)
        ) {
            Row(modifier = Modifier.weight(1f)) {
                Column {
                    val applierName = rememberSaveable {
                        mutableStateOf("")
                    }
                    viewModel.getApplierInfoById(applier.applierId) { applierN ->
                        applierName.value = applierN
                    }
                    // applier name
                    Text_18_White(textToShow = applierName.value, weight = 400)
                    Spacer(modifier = Modifier.height(10.dp))
                    // date on which applier applied
                    Text_18_White(
                        textToShow = "${applier.applicationDate.dd}/${applier.applicationDate.mm}/${applier.applicationDate.yyyy}",
                        weight = 400
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text_18_White(textToShow = "Designation", weight = 400)
                }
            }

            Row(modifier = Modifier
                .fillMaxHeight()
                .align(Alignment.CenterVertically)) {
                Button(
                    onClick = { },
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.size(56.dp),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_open),
                        contentDescription = "Button Open",
                    )
                }
            }
        }
    }
}