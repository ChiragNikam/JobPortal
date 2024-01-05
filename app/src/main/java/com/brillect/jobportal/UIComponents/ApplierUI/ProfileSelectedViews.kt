package com.brillect.jobportal.UIComponents.ApplierUI

import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.brillect.jobportal.Applier.ApplierProfileViewModel
import com.brillect.jobportal.BtnStartSearching
import com.brillect.jobportal.Data.ApplierProfile
import com.brillect.jobportal.UIComponents.BtnCustom
import com.brillect.jobportal.UIComponents.InfoBlock
import com.brillect.jobportal.UIComponents.MultiLineTextField
import com.brillect.jobportal.UIComponents.SingleLineTextField
import com.brillect.jobportal.UIComponents.TextCustom
import com.brillect.jobportal.UIComponents.Text_18_White
import com.brillect.jobportal.ui.theme.BackgroundColor
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@Composable
fun YourProfileView(viewModel: ApplierProfileViewModel) {
    Column(
        modifier = Modifier.background(
            color = BackgroundColor,
            shape = RoundedCornerShape(8.dp)
        )
    ) {
        // About User
        InfoBlock(
            label = "About Me",
            description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. "
        )
        Spacer(modifier = Modifier.height(24.dp))
        // Wrok Experience
        InfoBlock(
            label = "Work Experience", description = "Manager \nAmazon Inc\n" +
                    "16/05/2033 to 22/07/2044"
        )
        Spacer(modifier = Modifier.height(24.dp))
        // Education
        InfoBlock(
            label = "Education",
            description = "Computer Science & Engineering \nIndian Institute of Technology\n" +
                    "16/05/2033 to 22/07/2044"
        )
        Spacer(modifier = Modifier.height(24.dp))
        // Skills
        InfoBlock(
            label = "Skills",
            description = "C++ Data Science , C , Python , Devops , C++ Data Science , C , Python , Devops , C++ Data Science , C , Python , Devops , C++ Data Science , C , Python , Devops , "
        )
        Spacer(modifier = Modifier.height(24.dp))
        // Language
        InfoBlock(label = "Language", description = "English Hindi")
        Spacer(modifier = Modifier.height(24.dp))
        // Resume
        InfoBlock(label = "Resume", description = "")
    }
}

@Composable
fun EditProfileView(viewModel: ApplierProfileViewModel) {
    Column(
        modifier = Modifier.background(
            color = BackgroundColor,
            shape = RoundedCornerShape(8.dp)
        )
    ) {
        val context = LocalContext.current

        TextCustom(textToShow = "Wants to edit your profile?", weight = 700, fontSize = 18)
        Spacer(modifier = Modifier.height(22.dp))

        // User Name
        val u_name = SingleLineTextField(description = "Full Name", "")
        Spacer(modifier = Modifier.height(24.dp))
        // Email
        val email = SingleLineTextField(description = "Email")
        Spacer(modifier = Modifier.height(24.dp))
        // Phone No
        val phoneNo = SingleLineTextField(description = "Phone No.")
        Spacer(modifier = Modifier.height(24.dp))
        // Location
        val location = SingleLineTextField(description = "Location")
        Spacer(modifier = Modifier.height(24.dp))
        // Profile heading
        val profileHeading = SingleLineTextField(description = "Profile Heading")
        Spacer(modifier = Modifier.height(24.dp))
        // About Me
        val aboutMe = MultiLineTextField(description = "About Me")
        Spacer(modifier = Modifier.height(24.dp))
        // Work Experience
        val workExperience = MultiLineTextField(description = "Work Experience")
        Spacer(modifier = Modifier.height(24.dp))
        // Education
        val education = MultiLineTextField(description = "Education")
        Spacer(modifier = Modifier.height(24.dp))
        // Skills
        val skills = MultiLineTextField(description = "Skills")
        Spacer(modifier = Modifier.height(24.dp))
        // Resume
        InfoBlock(label = "Resume", description = "")

        Spacer(modifier = Modifier.height(24.dp))

        BtnCustom(onClicking = {
            val profile = ApplierProfile(
                u_name = u_name,
                email = email,
                phone = phoneNo,
                location = location,
                profileHeading = profileHeading,
                aboutMe = aboutMe,
                workExperience = workExperience,
                education = education,
                skills = skills
            )

            if(u_name.isEmpty()){   // validate if any important field is empty
                Toast.makeText(context, "Please enter user name.", Toast.LENGTH_SHORT).show()
            } else if(email.isEmpty()){
                Toast.makeText(context, "Please enter user name.", Toast.LENGTH_SHORT).show()
            } else if(phoneNo.isEmpty()){
                Toast.makeText(context, "Please enter your valid Phone No.", Toast.LENGTH_SHORT).show()
            } else if(profileHeading.isEmpty()){
                Toast.makeText(context, "Please write a profile heading", Toast.LENGTH_SHORT).show()
            } else {    // write profile to db
                // set or update used profile data to view model
                viewModel.updateUserProfileObj(profile)

                // upload data to the db
                viewModel.uploadProfileDetails()
            }
        }, text = "Update", padStart = 0, padEnd = 200)

        Spacer(modifier = Modifier.height(24.dp))

    }
}

@Preview(showSystemUi = true)
@Composable
fun ConfirmLogoutView() {
    val activity = (LocalContext.current as Activity)
    Column(
        modifier = Modifier.background(
            color = BackgroundColor,
            shape = RoundedCornerShape(8.dp)
        )
    ) {
        TextCustom(textToShow = "Are you sure? Do you want to logout?", weight = 700, fontSize = 18)
        Spacer(modifier = Modifier.height(22.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp, end = 24.dp)
        ) {
            // Cancel Button, Don't want to logout
            Button(
                onClick = { },
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.weight(1f)
            ) {
                Text_18_White(textToShow = "Cancel", weight = 700)
            }
            Spacer(modifier = Modifier.width(24.dp))
            // Confirm logout button
            Button(onClick = {
                Firebase.auth.signOut()
            }, shape = RoundedCornerShape(8.dp), modifier = Modifier.weight(1f)) {
                Text_18_White(textToShow = "Ok", weight = 700)
            }
        }
    }
}