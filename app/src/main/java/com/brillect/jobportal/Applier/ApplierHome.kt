package com.brillect.jobportal.Applier

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.brillect.jobportal.Auth.AuthViewModel
import com.brillect.jobportal.Data.RegisterData
import com.brillect.jobportal.Recruiter.RecruiterViewModel
import com.brillect.jobportal.UIComponents.ApplierUI.AvailableJobs
import com.brillect.jobportal.ui.theme.BackgroundColor
import com.brillect.jobportal.ui.theme.JobPortalTheme

class ApplierHome : ComponentActivity() {
    // view model instance
    val viewModel: ApplierViewModel by lazy { ViewModelProvider(this)[ApplierViewModel::class.java] }

    // register applier details
    private var register = false
    private var email = ""
    private var pass = ""
    private var uName = ""
    private var profile = "applier"

    override fun onStart() {
        super.onStart()

        if (register) { // if user just created account save it to db on coming to this activity
            AuthViewModel().writeUserToDb(RegisterData(uName, email, pass), profile)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // get intent
        register = intent.getBooleanExtra("register", false)
        email = intent.getStringExtra("email").toString()
        pass = intent.getStringExtra("pass").toString()
        uName = intent.getStringExtra("u_name").toString()

        setContent {
            JobPortalTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()),
                    color = BackgroundColor
                ) {
                    AvailableJobs(
                        viewModel,
                        onClickSearch = {
                            startActivity(
                                Intent(
                                    this,
                                    JobCompanyDescription::class.java
                                )
                            )
                        },
                        onImageClick = { startActivity(Intent(this, ApplierProfile::class.java)) },
                    )
                }
            }
        }
    }
}