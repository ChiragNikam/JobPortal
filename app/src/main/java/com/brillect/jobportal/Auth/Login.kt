package com.brillect.jobportal.Auth

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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.brillect.jobportal.ApplierHome
import com.brillect.jobportal.R
import com.brillect.jobportal.UIComponents.SingleLineTextField
import com.brillect.jobportal.UIComponents.customTextFieldForPassword
import com.brillect.jobportal.UIComponents.passwordTextField
import com.brillect.jobportal.Welcome
import com.brillect.jobportal.ui.theme.BackgroundColor
import com.brillect.jobportal.ui.theme.JobPortalTheme
import com.brillect.jobportal.ui.theme.PrimaryColor
import com.brillect.jobportal.ui.theme.TextFieldColor
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

val textFontFamily = FontFamily(Font(R.font.product_sans))

class Login : ComponentActivity() {
    var email = ""
    var password = ""
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
                    Column(modifier = Modifier.padding(start = 24.dp, top = 75.dp, end = 24.dp)) {
                        BackLogin {
                            startActivity(Intent(this@Login, Welcome::class.java))
                            finish()
                        }
                        Spacer(modifier = Modifier.height(26.dp))
                        email = SingleLineTextField(description = "Login")
                        Spacer(modifier = Modifier.height(24.dp))
                        password = passwordTextField("Password")
                        Spacer(modifier = Modifier.height(40.dp))
                        BtnLogin {
                            Log.i("login_details", "email: $email, pass: $password")
                            login(email, password)
                        }
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

    fun login(email: String, password: String) {
        val auth: FirebaseAuth
        // Initialize Firebase Auth
        auth = Firebase.auth
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    startActivity(Intent(this@Login, ApplierHome::class.java))
                    finish()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.e("login_error", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }

    }
}

@Composable
fun BackLogin(onBackPressed: () -> Unit) {
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
                text = "Back", color = PrimaryColor, fontSize = 18.sp, fontFamily = textFontFamily
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
fun BtnLogin(onLogin: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = { onLogin() }, modifier = Modifier
                .width(160.dp)
                .height(56.dp)
                .background(
                    color = PrimaryColor, shape = RoundedCornerShape(size = 8.dp)
                )
                .shadow(
                    elevation = 0.dp,
                    spotColor = Color(0x80000000),
                    ambientColor = Color(0x80000000)
                )

        ) {
            Text(
                text = "Login",
                textAlign = TextAlign.Center,
                color = Color.Black,
                fontSize = 16.sp,
                fontFamily = textFontFamily,
                fontWeight = FontWeight(700)
            )
        }
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