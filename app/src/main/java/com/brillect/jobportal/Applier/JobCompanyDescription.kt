package com.brillect.jobportal.Applier

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import com.brillect.jobportal.UIComponents.ApplierUI.JobCompanyDesc
import com.brillect.jobportal.ui.theme.JobPortalTheme

class JobCompanyDescription : ComponentActivity() {
    private lateinit var jobId: String

    val viewModel: ApplierViewModel by lazy { ViewModelProvider(this)[ApplierViewModel::class.java] }

    override fun onStart() {
        super.onStart()
        viewModel.loadJobCompanyDetails(jobId)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        jobId = intent.getStringExtra("job_id").toString()
        super.onCreate(savedInstanceState)
        setContent {
            JobCompanyDesc(viewModel, jobId){
                onBackPressed()
            }
        }
    }
}
