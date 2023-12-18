package com.brillect.jobportal.UIComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.brillect.jobportal.ui.theme.BackgroundColor
import com.brillect.jobportal.ui.theme.PrimaryColor


@Composable
fun BtnCustom(onClicking: () -> Unit, text: String, padStart: Int, padEnd: Int) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = { onClicking() },
            modifier = Modifier
                .height(56.dp).fillMaxWidth()
                .padding(start = padStart.dp, end = padEnd.dp)
                .shadow(
                    elevation = 0.dp,
                    spotColor = Color(0x80000000),
                    ambientColor = Color(0x80000000)
                ), shape = RoundedCornerShape(size = 8.dp)
        ) {
            Text(
                text = text,
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
fun BtnWithClickableState(onClicked: () -> Unit, btnText: String, clickedState: Boolean) {
    var buttonColor by remember { mutableStateOf(Color(0xFFfcc636)) }
    var textColor by remember { mutableStateOf(Color.Black) }
    val isButtonClicked by remember { mutableStateOf(clickedState) }

    Button(
        onClick = {
            buttonColor = if (isButtonClicked) Color(0xFF2B2B2B) else Color(0xFFfcc636)
            textColor = if (isButtonClicked) Color.White else Color.Black
            onClicked()
        },
        modifier = Modifier
            .width(160.dp) // Set a fixed width
            .height(60.dp) // Set a fixed height
            .background(buttonColor, RoundedCornerShape(8.dp)),
        colors = ButtonDefaults.buttonColors(if (clickedState) Color(0xFF2B2B2B) else Color(0xFFfcc636)),
    ) {
        Text(
            text = btnText,
            color = if (clickedState) Color.White else Color.Black,
            fontSize = 16.sp,
            fontFamily = textFontFamily,
            fontWeight = FontWeight(700)
        )
    }
}