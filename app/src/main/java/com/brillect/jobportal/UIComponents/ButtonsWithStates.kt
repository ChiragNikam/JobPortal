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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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