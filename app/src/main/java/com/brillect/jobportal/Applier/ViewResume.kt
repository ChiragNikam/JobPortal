package com.brillect.jobportal.Applier

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import com.brillect.jobportal.ui.theme.BackgroundColor
import com.brillect.jobportal.ui.theme.JobPortalTheme
import com.rizzi.bouquet.HorizontalPDFReader
import com.rizzi.bouquet.HorizontalPdfReaderState
import com.rizzi.bouquet.ResourceType

class ViewResume : ComponentActivity() {

    val viewModel : ApplierViewModel by lazy{ViewModelProvider(this)[ApplierViewModel::class.java]}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val resumeUrl = intent.getStringExtra("resume_url")
        viewModel.setResumeUrl(resumeUrl.toString())

        setContent {
            JobPortalTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val url by viewModel.resumeUrl.collectAsState()
                    // pdf reader state
                    val pdfHorizontalReaderState = remember {
                        mutableStateOf(
                            HorizontalPdfReaderState(
                                resource = ResourceType.Remote(url),
                                isZoomEnable = true,
                                isAccessibleEnable = true
                            )
                        )
                    }

                    Column(
                        modifier = Modifier
                            .background(
                                color = BackgroundColor
                            )
                            .fillMaxSize()
                    ) {
                        HorizontalPDFReader(
                            state = pdfHorizontalReaderState.value,
                            modifier = Modifier
                                .fillMaxSize()
                                .background(color = MaterialTheme.colorScheme.surfaceVariant)
                        )
                    }

                }
            }
        }
    }
}

@Composable
fun Greeting2(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    JobPortalTheme {
        Greeting2("Android")
    }
}