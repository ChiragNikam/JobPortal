package com.brillect.jobportal.Auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import com.brillect.jobportal.Applier.ApplierHome
import com.brillect.jobportal.Data.RegisterData
import com.brillect.jobportal.Data.RegisterDataWithConfirmPass
import com.brillect.jobportal.R
import com.brillect.jobportal.Recruiter.RecruiterHome
import com.brillect.jobportal.UIComponents.BtnCustom
import com.brillect.jobportal.UIComponents.SingleLineTextField
import com.brillect.jobportal.UIComponents.TextCustom
import com.brillect.jobportal.UIComponents.radioButtonRecruiterApplier
import com.brillect.jobportal.UIComponents.Text_18_PrimaryColor
import com.brillect.jobportal.UIComponents.Text_18_White
import com.brillect.jobportal.UIComponents.passwordTextField
import com.brillect.jobportal.UIComponents.textFontFamily
import com.brillect.jobportal.ui.theme.BackgroundColor
import com.brillect.jobportal.ui.theme.JobPortalTheme
import com.google.firebase.auth.FirebaseAuth

class Register : ComponentActivity() {
    private lateinit var selectedProfile: String    // for choice of profile among Applier or Recruiter
    private lateinit var checkRegisterDetails: RegisterDataWithConfirmPass  // to validate input
    private lateinit var viewModelAuth: AuthViewModel

    // Initialize Firebase Auth
    val auth = FirebaseAuth.getInstance()
    val currentUser = auth.currentUser  // for current user
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModelAuth = ViewModelProvider(this)[AuthViewModel::class.java]
        setContent {
            JobPortalTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()),
                    color = BackgroundColor
                ) {
                    Column(modifier = Modifier.padding(start = 24.dp, top = 75.dp, end = 24.dp)) {
                        BackRegister {
                            startActivity(Intent(this@Register, Login::class.java))
                            finish()
                        }
                        Spacer(modifier = Modifier.height(26.dp))
                        RegisterTxt()
                        checkRegisterDetails = registerDetails()
                        Spacer(modifier = Modifier.height(22.dp))
                        selectedProfile = radioButtonRecruiterApplier()
                        Spacer(modifier = Modifier.height(60.dp))
                        BtnCustom(
                            onClicking = {
                                registerUser(
                                    checkRegisterDetails,
                                    RegisterData(
                                        checkRegisterDetails.fullName,
                                        checkRegisterDetails.email,
                                        checkRegisterDetails.password
                                    ), selectedProfile
                                )
                                // test write
//                                viewModelAuth.testWriteToRealTimeDb()
                            },
                            text = "Register",
                            padStart = 65,
                            padEnd = 87
                        )
                        Spacer(modifier = Modifier.height(60.dp))
                    }
                }
            }
        }
    }

    private fun registerUser(
        validateDetails: RegisterDataWithConfirmPass,
        registerDetails: RegisterData,
        profile: String
    ) {
        // check weather the user input is not empty for eny of the field
        if (viewModelAuth.isReadyToRegister(validateDetails) == "yes") {
            auth.createUserWithEmailAndPassword(registerDetails.email, registerDetails.password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // save user details to realtime database
                        viewModelAuth.registerUser(
                            registerDetails,
                            profile
                        )

                        // move to activity according to selected profile type(among recruiter/applier)
                        if (selectedProfile == "Recruiter") {
                            startActivity(Intent(this@Register, RecruiterHome::class.java))
                            finish()
                        } else if (selectedProfile == "Applier") {
                            startActivity(Intent(this@Register, ApplierHome::class.java))
                        }
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.e("login_error", "createUserWithEmail:failure", task.exception)
                        Toast.makeText(
                            baseContext,
                            "Authentication failed|${task.exception?.message}",
                            Toast.LENGTH_SHORT,
                        ).show()
                    }
                }
        } else {
            Toast.makeText(
                this,
                viewModelAuth.isReadyToRegister(validateDetails),
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}


@Composable
fun BackRegister(onBackPressed: () -> Unit) {
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable { onBackPressed() }) {
            Image(
                painter = painterResource(id = R.drawable.icon_back),
                contentDescription = "Back Button",
                modifier = Modifier
                    .height(24.dp)
                    .width(24.dp),
                contentScale = ContentScale.FillBounds
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text_18_PrimaryColor(textToShow = "Back to login")
        }
        Spacer(modifier = Modifier.height(24.dp))
        Text_18_White(textToShow = "Hello...")
    }
}

@Preview
@Composable
fun RegisterTxt() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Register",
            color = Color.White,
            style = TextStyle(
                fontFamily = textFontFamily,
                fontWeight = FontWeight(700),
                fontSize = 48.sp
            ),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun registerDetails(): RegisterDataWithConfirmPass {
    val registerData = RegisterDataWithConfirmPass()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = 22.dp)
    ) {
        Spacer(modifier = Modifier.height(22.dp))
        // Name
        registerData.fullName = SingleLineTextField(description = "Full Name")

        Spacer(modifier = Modifier.height(22.dp))
        // Email
        registerData.email = SingleLineTextField(description = "Email")

        Spacer(modifier = Modifier.height(22.dp))
        // Password
        registerData.password = passwordTextField(description = "Password")
        TextCustom(
            textToShow = "      * Password should be more then 6 characters",
            weight = 400,
            fontSize = 11
        )
        Spacer(modifier = Modifier.height(22.dp))
        // Confirm Password
        registerData.confirmPassword = passwordTextField(description = "Confirm Password")
    }
    return registerData
}
