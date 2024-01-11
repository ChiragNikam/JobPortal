package com.brillect.jobportal.Recruiter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Shapes
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.brillect.jobportal.Auth.AuthViewModel
import com.brillect.jobportal.Data.RegisterData
import com.brillect.jobportal.FirebaseWrite
import com.brillect.jobportal.UIComponents.HelloUserNameProfilePhoto
import com.brillect.jobportal.UIComponents.RecruiterUI.LogoutDialog
import com.brillect.jobportal.UIComponents.RecruiterUI.ProfileAndHistoryUI
import com.brillect.jobportal.UIComponents.RecruiterUI.RecruiterUI
import com.brillect.jobportal.UIComponents.textFontFamily
import com.brillect.jobportal.ui.theme.BackgroundColor
import com.brillect.jobportal.ui.theme.JobPortalTheme
import com.brillect.jobportal.ui.theme.TextFieldColor

class RecruiterHome : ComponentActivity() {
    // view model instance
    private val viewModel: RecruiterViewModel by lazy { ViewModelProvider(this)[RecruiterViewModel::class.java] }
    private val viewModelProfile: RecruiterProfileViewModel by lazy { ViewModelProvider(this)[RecruiterProfileViewModel::class.java] }

    // register recruiter details
    private var register = false
    private var email = ""
    private var pass = ""
    private var uName = ""
    private var profile = "recruiter"

    override fun onStart() {
        super.onStart()

        if (register) { // if user just created account save it to db on coming to this activity
            AuthViewModel().writeUserToDb(RegisterData(uName, email, pass), profile)
        }

        // set data for profile card view
        viewModelProfile.setDataForProfileCard()

        viewModelProfile.setListOfAllJobPosts()
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
                ) {
                    val navController = rememberNavController()

                    // list of items to show for bottom navigation
                    val items = listOf(
                        BottomNavigationItem(
                            title = "Home",
                            route = "home",
                            selectedIcon = Icons.Filled.Home,
                            unselectedIcon = Icons.Outlined.Home
                        ), BottomNavigationItem(
                            title = "Profile",
                            route = "profile",
                            selectedIcon = Icons.Filled.AccountCircle,
                            unselectedIcon = Icons.Outlined.AccountCircle
                        )
                    )

                    val showLogoutDialog =
                        remember { mutableStateOf(false) }   // to show sign-out dialog

                    Scaffold(
                        topBar = {
                            // observe user name
                            val userName by viewModel.firstName.collectAsState()
                            Column(
                                Modifier.height(60.dp),
                            ) {
                                HelloUserNameProfilePhoto(userName) {   // Top Bar with User name
                                    showLogoutDialog.value = !showLogoutDialog.value
                                }
                            }
                        },
                        bottomBar = {
                            BottomNav(
                                items = items,
                                navController = navController,
                                onItemClicked = {
                                    navController.navigate(it.route)
                                })
                        },
                        containerColor = BackgroundColor
                    ) {
                        val pad = it
                        Navigation(navController = navController, viewModel, viewModelProfile)
                    }

                    if (showLogoutDialog.value) {   // if user clicked on profile pic logout dialog will apire
                        LogoutDialog(showLogoutDialog)
                    }
                }
            }
        }
    }
}

@Composable
fun BottomNav(
    items: List<BottomNavigationItem>,
    navController: NavController,
    modifier: Modifier = Modifier,
    onItemClicked: (BottomNavigationItem) -> Unit
) {
    NavigationBar(containerColor = TextFieldColor) {
        val backStackEntry = navController.currentBackStackEntryAsState()
        items.forEachIndexed { index, bottomNavigationItem ->
            NavigationBarItem(
                selected = bottomNavigationItem.route == backStackEntry.value?.destination?.route,
                onClick = {
                    onItemClicked(bottomNavigationItem)
                },
                label = {
                    Text(
                        text = bottomNavigationItem.title,
                        fontFamily = textFontFamily,
                        color = Color.White
                    )
                },
                alwaysShowLabel = false,
                icon = {
                    if (bottomNavigationItem.route == backStackEntry.value?.destination?.route) {
                        Icon(
                            bottomNavigationItem.selectedIcon,
                            contentDescription = bottomNavigationItem.title
                        )
                    } else {
                        Icon(
                            bottomNavigationItem.unselectedIcon,
                            contentDescription = bottomNavigationItem.title
                        )
                    }
                })
        }
    }
}

@Composable
fun Navigation(navController: NavHostController, viewModel: RecruiterViewModel, viewModelProfile: RecruiterProfileViewModel) {
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            Box(Modifier.fillMaxSize()) {
                RecruiterUI(viewModel)
            }
        }
        composable("profile") {
            ProfileAndHistoryUI(viewModelProfile)
        }
    }
}

// data class for bottom navigation
data class BottomNavigationItem(
    val title: String,
    val route: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)