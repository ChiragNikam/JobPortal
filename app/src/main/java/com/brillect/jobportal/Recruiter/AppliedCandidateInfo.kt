package com.brillect.jobportal.Recruiter

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.brillect.jobportal.UIComponents.RecruiterUI.CandidateProfile
import com.brillect.jobportal.UIComponents.RecruiterUI.CandidateResume
import com.brillect.jobportal.ui.theme.JobPortalTheme
import com.brillect.jobportal.ui.theme.PrimaryColor
import com.brillect.jobportal.ui.theme.TextFieldColor

class AppliedCandidateInfo : ComponentActivity() {
    private var applierId = ""

    // list of items in tab row
    private val tabItems = listOf("Profile", "Resume")

    val viewModel: AppliedCandidateViewModel by lazy { ViewModelProvider(this)[AppliedCandidateViewModel::class.java] }

    override fun onStart() {
        super.onStart()

        // set details of applied candidate
        viewModel.setApplierDetails(applierId)

        // set resume link
        viewModel.getResumeLink(applierId)
    }

    @OptIn(ExperimentalFoundationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        applierId = intent.getStringExtra("emp_id").toString()

        setContent {
            JobPortalTheme {
                // selected state for tab row
                var selectedTabIndex by rememberSaveable {
                    mutableIntStateOf(0)
                }
                // horizontal pager state
                val pagerState = rememberPagerState {
                    tabItems.size
                }
                // update the horizontal pager state if item clicked in tab row
                LaunchedEffect(selectedTabIndex) {
                    pagerState.animateScrollToPage(selectedTabIndex)
                }
                // update selected tab index on scrolling of horizontal pager
                LaunchedEffect(pagerState.currentPage) {
                    selectedTabIndex = pagerState.currentPage
                }

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(modifier = Modifier.fillMaxSize()) {
                        TabRow(
                            selectedTabIndex = selectedTabIndex,
                            modifier = Modifier.fillMaxWidth(),
                            containerColor = TextFieldColor
                        ) {
                            tabItems.forEachIndexed { index, item ->
                                Tab(
                                    selected = index == selectedTabIndex,
                                    onClick = {
                                        selectedTabIndex = index
                                    },
                                    text = {
                                        Text(text = item)
                                    },
                                    selectedContentColor = MaterialTheme.colorScheme.primary,
                                    unselectedContentColor = PrimaryColor
                                )
                            }
                        }
                        HorizontalPager(
                            state = pagerState,
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                        ) { index ->
                            if (index == 0) {
                                CandidateProfile(viewModel = viewModel)
                            } else {
                                CandidateResume(viewModel = viewModel, applierId)
                            }
                        }
                    }
                }
            }
        }
    }
}