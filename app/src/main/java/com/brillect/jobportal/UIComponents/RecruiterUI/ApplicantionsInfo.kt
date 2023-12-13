package com.brillect.jobportal.UIComponents.RecruiterUI

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.brillect.jobportal.R
import com.brillect.jobportal.UIComponents.BtnCustom
import com.brillect.jobportal.UIComponents.SingleLineTextField
import com.brillect.jobportal.UIComponents.Text_18_White
import com.brillect.jobportal.ui.theme.BackgroundColor
import com.brillect.jobportal.ui.theme.PrimaryColor

@Preview(showSystemUi = true)
@Composable
fun ApplicantsInfo() {
    Column(horizontalAlignment = Alignment.Start) {
        Text_18_White(textToShow = "Total Applicants")
        Spacer(modifier = Modifier.height(30.dp))
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
        Spacer(modifier = Modifier.height(16.dp))
        ApplicantDetails()
        Spacer(modifier = Modifier.height(40.dp))

    }
}

@Composable
fun ApplicantDetails() {
    Row(
        modifier = Modifier
            .background(
                color = Color(0xFF2B2B2B),
                shape = RoundedCornerShape(size = 8.dp)
            )
    ) {
        Column(
            modifier = Modifier
                .padding(start = 14.dp, top = 23.dp, bottom = 23.dp)
                .weight(1f)
        ) {
            Text_18_White(textToShow = "Name", weight = 400)
            Spacer(modifier = Modifier.height(10.dp))
            Text_18_White(textToShow = "Interview", weight = 400)
            Spacer(modifier = Modifier.height(10.dp))
            Text_18_White(textToShow = "Date", weight = 400)
            Spacer(modifier = Modifier.height(10.dp))
            Text_18_White(textToShow = "Designation", weight = 400)
        }
        Row(modifier = Modifier.fillMaxHeight()) {
            Box(
                modifier = Modifier
                    .padding(start = 24.dp, end = 16.dp, top = 52.dp, bottom = 52.dp)
            ) {
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