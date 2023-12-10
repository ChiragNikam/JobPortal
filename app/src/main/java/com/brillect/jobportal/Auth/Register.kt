package com.brillect.jobportal.Auth

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.brillect.jobportal.Home
import com.brillect.jobportal.R
import com.brillect.jobportal.ui.theme.BackgroundColor
import com.brillect.jobportal.ui.theme.JobPortalTheme
import com.brillect.jobportal.ui.theme.PrimaryColor
import com.brillect.jobportal.ui.theme.TextFieldColor

class Register : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JobPortalTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = BackgroundColor
                ) {
                    Column(modifier = Modifier.padding(start = 24.dp, top = 75.dp, end = 24.dp)) {
                        BackRegister {
                            onBackPressed()
                        }
                        Spacer(modifier = Modifier.height(26.dp))
                        RegisterTxt()
                        RegisterDetails()
                        Spacer(modifier = Modifier.height(60.dp))
                        BtnRegister {
                            startActivity(Intent(this@Register, Home::class.java))
                            finish()
                        }
                        Spacer(modifier = Modifier.height(124.dp))
                    }
                }
            }
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
            Text(
                text = "Back",
                color = PrimaryColor,
                fontSize = 18.sp,
                fontFamily = textFontFamily
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "Hello...",
            color = Color.White,
            fontSize = 18.sp,
            fontFamily = textFontFamily,
            fontWeight = FontWeight(700)
        )
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


@Preview(showSystemUi = true)
@Composable
fun RegisterDetails() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = 22.dp)
    ) {
        var fullName by remember { mutableStateOf("") }
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var confirmPassword by remember { mutableStateOf("") }

        Spacer(modifier = Modifier.height(22.dp))
        Text(text = "Full Name", color = Color.White, fontFamily = textFontFamily)
        Spacer(modifier = Modifier.height(22.dp))
        Box(
            modifier = Modifier
                .background(Color.Black, shape = RoundedCornerShape(8.dp))
                .padding(bottom = 1.dp, end = 1.dp)
        ) {
            Box(
                modifier = Modifier
                    .background(TextFieldColor, shape = RoundedCornerShape(8.dp))
                    .fillMaxWidth()
            ) {
                BasicTextField(
                    value = fullName,
                    onValueChange = { fullName = it },
                    modifier = Modifier
                        .height(44.dp)
                        .fillMaxWidth()
                        .padding(top = 11.dp, bottom = 10.dp, start = 16.dp, end = 16.dp),
                    textStyle = TextStyle(
                        color = PrimaryColor, fontFamily = textFontFamily,
                        fontSize = 22.sp
                    ), keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                )
            }
        }

        Spacer(modifier = Modifier.height(22.dp))
        Text(text = "Email", color = Color.White, fontFamily = textFontFamily)
        Spacer(modifier = Modifier.height(22.dp))

        Box(
            modifier = Modifier
                .background(Color.Black, shape = RoundedCornerShape(8.dp))
                .padding(bottom = 1.dp, end = 1.dp)
        ) {
            Box(
                modifier = Modifier
                    .background(TextFieldColor, shape = RoundedCornerShape(8.dp))
                    .fillMaxWidth()
            ) {
                BasicTextField(
                    value = email,
                    onValueChange = { email = it },
                    modifier = Modifier
                        .height(44.dp)
                        .fillMaxWidth()
                        .padding(top = 11.dp, bottom = 10.dp, start = 16.dp, end = 16.dp),
                    textStyle = TextStyle(
                        color = PrimaryColor, fontFamily = textFontFamily,
                        fontSize = 22.sp
                    ), keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                )
            }
        }

        Spacer(modifier = Modifier.height(22.dp))
        Text(text = "Password", color = Color.White, fontFamily = textFontFamily)
        Spacer(modifier = Modifier.height(22.dp))

        Box(
            modifier = Modifier
                .background(Color.Black, shape = RoundedCornerShape(8.dp))
                .padding(bottom = 1.dp, end = 1.dp)
        ) {
            Box(
                modifier = Modifier
                    .background(TextFieldColor, shape = RoundedCornerShape(8.dp))
                    .fillMaxWidth()
            ) {
                BasicTextField(
                    value = password,
                    onValueChange = { password = it },
                    modifier = Modifier
                        .height(44.dp)
                        .fillMaxWidth()
                        .padding(top = 11.dp, bottom = 10.dp, start = 16.dp, end = 16.dp),
                    textStyle = TextStyle(
                        color = PrimaryColor, fontFamily = textFontFamily,
                        fontSize = 22.sp
                    ),
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                )
            }
        }

        Spacer(modifier = Modifier.height(22.dp))
        Text(text = "Confirm Password", color = Color.White, fontFamily = textFontFamily)
        Spacer(modifier = Modifier.height(22.dp))

        Box(
            modifier = Modifier
                .background(Color.Black, shape = RoundedCornerShape(8.dp))
                .padding(bottom = 1.dp, end = 1.dp)
        ) {
            Box(
                modifier = Modifier
                    .background(TextFieldColor, shape = RoundedCornerShape(8.dp))
                    .fillMaxWidth()
            ) {
                BasicTextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    modifier = Modifier
                        .height(44.dp)
                        .fillMaxWidth()
                        .padding(top = 11.dp, bottom = 10.dp, start = 16.dp, end = 16.dp),
                    textStyle = TextStyle(
                        color = PrimaryColor, fontFamily = textFontFamily,
                        fontSize = 22.sp
                    ),
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                )
            }
        }
    }
}

@Composable
fun BtnRegister(onLogin: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = { onLogin() },
            modifier = Modifier
                .width(160.dp)
                .height(56.dp)
                .background(
                    color = PrimaryColor,
                    shape = RoundedCornerShape(size = 8.dp)
                )
                .shadow(
                    elevation = 0.dp,
                    spotColor = Color(0x80000000),
                    ambientColor = Color(0x80000000)
                )

        ) {
            Text(
                text = "Register",
                textAlign = TextAlign.Center,
                color = Color.Black,
                fontSize = 16.sp,
                fontFamily = textFontFamily,
                fontWeight = FontWeight(700)
            )
        }
    }
}