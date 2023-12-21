package com.brillect.jobportal.Auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.brillect.jobportal.Applier.ApplierHome
import com.brillect.jobportal.R
import com.brillect.jobportal.Recruiter.RecruiterHome
import com.brillect.jobportal.UIComponents.BtnCustom
import com.brillect.jobportal.UIComponents.SingleLineTextField
import com.brillect.jobportal.UIComponents.passwordTextField
import com.brillect.jobportal.UIComponents.radioButtonRecruiterApplier
import com.brillect.jobportal.UIComponents.textFontFamily
import com.brillect.jobportal.Welcome
import com.brillect.jobportal.ui.theme.BackgroundColor
import com.brillect.jobportal.ui.theme.JobPortalTheme
import com.brillect.jobportal.ui.theme.PrimaryColor
import com.brillect.jobportal.ui.theme.TextFieldColor
import com.google.firebase.auth.FirebaseAuth


class Login : ComponentActivity() {
    private var email = ""
    private var password = ""

    // Initialize Firebase Auth
    val auth = FirebaseAuth.getInstance()
    val currentUser = auth.currentUser  // for current user

    override fun onStart() {
        super.onStart()

        // check if user already exist
        if (currentUser != null) {
//            startActivity(Intent(this, ApplierHome::class.java))
            // Get the shared preferences instance
            val sharedPreferences = getSharedPreferences("UserType", Context.MODE_PRIVATE)
            val userType = sharedPreferences.getString("type", "")
            if(userType == "applier"){
                startActivity(Intent(this, ApplierHome::class.java))
            } else if(userType == "recruiter"){
                startActivity(Intent(this, RecruiterHome::class.java))
            }

        }
        Log.d("auth", "Current user: ${currentUser?.uid}")

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            JobPortalTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()),
                    color = BackgroundColor
                ) {
                    Column(modifier = Modifier.padding(start = 24.dp, top = 75.dp, end = 46.dp)) {
                        BackToPrevious({
                            startActivity(Intent(this@Login, Welcome::class.java))
                            finish()
                        }, "Back")
                        Spacer(modifier = Modifier.height(26.dp))
                        email = SingleLineTextField(description = "Login")
                        Spacer(modifier = Modifier.height(24.dp))
                        password = passwordTextField("Password")
                        val selectedProfile = radioButtonRecruiterApplier()
                        Spacer(modifier = Modifier.height(40.dp))
                        BtnCustom(
                            onClicking = {
                                if (email.isNotEmpty() && password.isNotEmpty()) {
                                    login(email, password, selectedProfile)
                                } else if (email.isEmpty()) {
                                    Toast.makeText(
                                        this@Login,
                                        "Please enter e-mail id",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else if (password.isEmpty()) {
                                    Toast.makeText(
                                        this@Login,
                                        "Please enter you password",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            },
                            text = "Login",
                            padStart = 45,
                            padEnd = 67
                        )

                        Spacer(modifier = Modifier.height(24.dp))
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                                .background(
                                    TextFieldColor
                                )
                        ) {}
                        Spacer(modifier = Modifier.height(36.dp))
                        DontHaveAccount {
                            startActivity(Intent(this@Login, Register::class.java))
                            finish()
                        }
                    }
                }
            }
        }
    }

    private fun login(email: String, password: String, selectedProfile: String) {

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                // write to shared preference
                writeToSharedPreference(selectedProfile.lowercase())

                // Sign in success, update UI with the signed-in user's information
                // move to activity according to selected profile type(among recruiter/applier)
                if (selectedProfile == "Recruiter") {
                    startActivity(Intent(this, RecruiterHome::class.java))
                    finish()
                } else if (selectedProfile == "Applier") {
                    startActivity(Intent(this, ApplierHome::class.java))
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
    }

    private fun writeToSharedPreference(userType: String) {
        // Get the shared preferences instance
        val sharedPreferences = getSharedPreferences("UserType", Context.MODE_PRIVATE)

        // Get an editor to modify shared preferences
        val editor = sharedPreferences.edit()

        // Save data to shared preferences
        editor.putString("type", userType)

        // Apply changes
        editor.apply()
    }
}

@Composable
fun BackToPrevious(onBackPressed: () -> Unit, description: String) {
    Column {
        Row(verticalAlignment = Alignment.CenterVertically,
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
            Text(
                text = description,
                color = PrimaryColor,
                fontSize = 18.sp,
                fontFamily = textFontFamily
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "Welcome Back!!!",
            color = Color.White,
            fontSize = 18.sp,
            fontFamily = textFontFamily
        )
    }
}

@Composable
fun DontHaveAccount(onRegister: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center
    ) {
        Text(text = "Don't have an account yet?", fontFamily = textFontFamily, color = Color.White)
        Text(text = " Register here",
            fontFamily = textFontFamily,
            color = PrimaryColor,
            modifier = Modifier.clickable(enabled = true) { onRegister() })
    }
}