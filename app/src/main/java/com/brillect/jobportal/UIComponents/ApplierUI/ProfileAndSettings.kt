package com.brillect.jobportal.UIComponents.ApplierUI

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.brillect.jobportal.Applier.ApplierProfileViewModel
import com.brillect.jobportal.R
import com.brillect.jobportal.UIComponents.BtnCustom
import com.brillect.jobportal.UIComponents.RecruiterUI.LogoutDialog
import com.brillect.jobportal.UIComponents.TextCustom
import com.brillect.jobportal.ui.theme.BackgroundColor
import com.brillect.jobportal.ui.theme.JobPortalTheme
import com.brillect.jobportal.ui.theme.PrimaryColor
import com.brillect.jobportal.ui.theme.TextFieldColor
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@Composable
fun ProfileAndSettings(viewModel: ApplierProfileViewModel) {
    JobPortalTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            color = BackgroundColor
        ) {
            var clickedState by remember { mutableIntStateOf(1) }
            Column(modifier = Modifier.padding(start = 24.dp, top = 75.dp, end = 24.dp)) {
                SimpleBack {

                }
                Spacer(modifier = Modifier.height(24.dp))

                ApplierNameAndHeadingView()

                Spacer(modifier = Modifier.height(24.dp))

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // button your profile
                    BtnCustom(
                        onClicking = { clickedState = 1 },
                        text = "Your Profile",
                        padStart = 45,
                        padEnd = 67
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    // button edit profiel
                    BtnCustom(
                        onClicking = { clickedState = 2 },
                        text = "Edit Profile",
                        padStart = 45,
                        padEnd = 67
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    // button logout
                    BtnCustom(
                        onClicking = {
                            clickedState = 3
                        },
                        text = "Logout",
                        padStart = 45,
                        padEnd = 67
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(TextFieldColor)
                ) {}
                Spacer(modifier = Modifier.height(24.dp))
                Surface(modifier = Modifier.fillMaxSize(), shape = RoundedCornerShape(8.dp)) {
                    if (clickedState == 1) {
                        YourProfileView(viewModel)
                    } else if (clickedState == 2) {
                        EditProfileView(viewModel)
                    } else if (clickedState == 3) {
                        val dialogShowState = remember {
                            mutableStateOf(true)
                        }
                        if (dialogShowState.value)
                            LogoutDialog(dialogShowState)
                    }
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun ApplierNameAndHeadingView() {
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
                TextCustom(textToShow = "User Name", weight = 700, fontSize = 18)
                Spacer(modifier = Modifier.height(10.dp))
                TextCustom(
                    textToShow = "TypeScript Expert | Mern Developer",
                    weight = 400,
                    fontSize = 14
                )
            }
        }
    }
}