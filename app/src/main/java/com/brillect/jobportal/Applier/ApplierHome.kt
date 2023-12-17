package com.brillect.jobportal.Applier

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.ui.Modifier
import com.brillect.jobportal.Auth.AuthViewModel
import com.brillect.jobportal.UIComponents.ApplierUI.AvailableJobs

class ApplierHome : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AvailableJobs(
                onClickSearch = {
                    AuthViewModel().testWriteToRealTimeDb()
                },
                onImageClick = { startActivity(Intent(this, ApplierProfile::class.java)) },
                Modifier.clickable {
                    startActivity(
                        Intent(
                            this,
                            JobCompanyDescription::class.java
                        )
                    )
                })
        }
    }
}