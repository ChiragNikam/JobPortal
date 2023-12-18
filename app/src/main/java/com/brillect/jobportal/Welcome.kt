package com.brillect.jobportal

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.brillect.jobportal.Auth.Login
import com.brillect.jobportal.ui.theme.BackgroundColor
import com.brillect.jobportal.ui.theme.JobPortalTheme
import com.brillect.jobportal.ui.theme.PrimaryColor

class Welcome : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JobPortalTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    color = BackgroundColor,
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Greeting()
                        Spacer(modifier = Modifier.height(44.dp))
                        BtnStartSearching {
                            startActivity(Intent(this@Welcome, Login::class.java))
                            finish()
                        }
                        Spacer(modifier = Modifier.height(24.dp))
                        ImgCompanies()
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Greeting() {
    Text(
        modifier = Modifier.padding(start = 24.dp, top = 72.dp, end = 24.dp, bottom = 0.dp),
        text = "Your search for the next dream job is over 🚀",
        style = TextStyle(
            fontSize = 48.sp,
            fontFamily = FontFamily(Font(R.font.product_sans)),
            fontWeight = FontWeight(700),
            color = Color(0xFFFFFFFF),
            textAlign = TextAlign.Center
        )
    )
}

@Composable
fun BtnStartSearching(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .width(160.dp)
            .height(56.dp)
            .shadow(
                elevation = 0.dp,
                spotColor = Color(0x80000000),
                ambientColor = Color(0x80000000)
            ),
        shape = RoundedCornerShape(size = 8.dp)
    ) {
        Text(
            text = "Start Searching",
            textAlign = TextAlign.Center,
            color = Color.Black,
            fontSize = 16.sp,
            fontFamily = FontFamily(Font(R.font.product_sans)),
            fontWeight = FontWeight(700)
        )
    }
}

@Preview
@Composable
fun ImgCompanies() {
    Image(
        painter = painterResource(id = R.drawable.bg_image),
        contentDescription = "Companies",
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.FillBounds
    )
}