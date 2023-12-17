package com.brillect.jobportal.UIComponents.ApplierUI

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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.brillect.jobportal.Auth.BackToPrevious
import com.brillect.jobportal.R
import com.brillect.jobportal.UIComponents.BtnCustom
import com.brillect.jobportal.UIComponents.InfoBlock
import com.brillect.jobportal.UIComponents.RecruiterUI.CompanyDetails
import com.brillect.jobportal.UIComponents.textFontFamily
import com.brillect.jobportal.ui.theme.BackgroundColor
import com.brillect.jobportal.ui.theme.JobPortalTheme
import com.brillect.jobportal.ui.theme.PrimaryColor
import com.brillect.jobportal.ui.theme.TextFieldColor

@Preview(showSystemUi = true)
@Composable
fun JobCompanyDesc() {
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
                // to go back
                Column {
                    //
                    Row(verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable {  }) {
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
                Spacer(modifier = Modifier.height(26.dp))

                // show details of clicked job in this view
                AvailableCompaniesViews(modifier = Modifier)
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
                    modifier = Modifier
                        .fillMaxSize(),
                    color = BackgroundColor
                ) {
                    if (clickedState.intValue == 1) {   // if clicked Job Description
                        JobDescription()
                    } else if (clickedState.intValue == 2) {    // if clicked Company Details
                        CompanyDetails()
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
                BtnCustom(onClicking = { /*TODO*/ }, text = "Apply", padStart = 70, padEnd = 70)
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
fun JobDescription(){
    val description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. "
    Column(horizontalAlignment = Alignment.Start) {
        Spacer(modifier = Modifier.height(22.dp))
        InfoBlock(label = "About Company", description = description)
        Spacer(modifier = Modifier.height(24.dp))
    }
}