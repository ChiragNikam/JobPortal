package com.brillect.jobportal.UIComponents

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.brillect.jobportal.Auth.textFontFamily
import com.brillect.jobportal.R
import com.brillect.jobportal.ui.theme.PrimaryColor
import com.brillect.jobportal.ui.theme.TextFieldColor

@Composable
fun SingleLineTextField(description: String): String {
    var textEntered = ""
    Column(modifier = Modifier.fillMaxWidth()) {
        Text_18_White(textToShow = description, 400)
        Spacer(modifier = Modifier.height(22.dp))
        textEntered = customTextFieldSingleLine()
    }
    return textEntered
}

@Composable
fun MultiLineTextField(description: String): String {
    var textEntered = ""
    Column(modifier = Modifier.fillMaxWidth()) {
        Text_18_White(textToShow = description, 400)
        Spacer(modifier = Modifier.height(22.dp))
        textEntered = customTextFieldMultiLine()
    }
    return textEntered
}

@Composable
fun passwordTextField(description: String): String {
    var password = ""
    Column(modifier = Modifier.fillMaxWidth()) {
        Text_18_White(textToShow = description, 400)
        Spacer(modifier = Modifier.height(22.dp))
        password = customTextFieldForPassword()
    }
    return password
}

@Composable
fun Text_18_White(textToShow: String) {
    Text(
        text = textToShow,
        color = Color.White,
        fontSize = 18.sp,
        fontFamily = textFontFamily,
        fontWeight = FontWeight(700)
    )
}

@Composable
fun Text_18_White(textToShow: String, weight: Int) {
    Text(
        text = textToShow,
        color = Color.White,
        fontSize = 18.sp,
        fontFamily = textFontFamily,
        fontWeight = FontWeight(weight)
    )
}

@Composable
fun TextCustom(textToShow: String, weight: Int, fontSize: Int) {
    Text(
        text = textToShow,
        color = Color.White,
        fontSize = fontSize.sp,
        fontFamily = textFontFamily,
        fontWeight = FontWeight(weight)
    )
}

@Composable
fun Text_18_PrimaryColor(textToShow: String) {
    Text(
        text = textToShow,
        color = PrimaryColor,
        fontSize = 18.sp,
        fontFamily = textFontFamily
    )
}

@Composable
fun TextUserName(name: String) {
    Text(
        text = "Hello $name 👋",
        color = Color.White,
        fontSize = 18.sp,
        fontWeight = FontWeight(700),
        fontFamily = textFontFamily
    )
}

@Composable
fun customTextFieldSingleLine(): String {
    var fullName by remember {
        mutableStateOf("")
    }
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
                    fontSize = 14.sp
                ), keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text), singleLine = true
            )
        }
    }
    return fullName
}

@Composable
fun customTextFieldMultiLine(): String {
    var fullName by remember {
        mutableStateOf("")
    }
    Box(
        modifier = Modifier
            .background(Color.Black, shape = RoundedCornerShape(8.dp))
            .padding(bottom = 1.dp, end = 1.dp)
    ) {
        Box(
            modifier = Modifier
                .background(TextFieldColor, shape = RoundedCornerShape(8.dp))
                .fillMaxWidth()
                .height(88.dp)
        ) {
            BasicTextField(
                value = fullName,
                onValueChange = { fullName = it },
                modifier = Modifier
                    .height(44.dp)
                    .fillMaxSize()
                    .padding(top = 11.dp, bottom = 10.dp, start = 16.dp, end = 16.dp),
                textStyle = TextStyle(
                    color = PrimaryColor, fontFamily = textFontFamily,
                    fontSize = 22.sp
                ), keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                maxLines = 4
            )
        }
    }
    return fullName
}

@Composable
fun customTextFieldForPassword(): String {
    var password by remember {
        mutableStateOf("")
    }
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
                    fontSize = 14.sp
                ),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )
        }
    }
    return password
}

@Preview
@Composable
fun radioButtonRecruiterApplier(): String {
    val selectedOption = remember { mutableStateOf("Recruiter") }
    Row {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
            RadioButton(
                selected = selectedOption.value == "Recruiter",
                onClick = { selectedOption.value = "Recruiter" },

                )
            Text("Recruiter", color = Color.White, fontSize = 18.sp)
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End,
            modifier = Modifier
                .weight(1f)
                .padding(end = 24.dp)
        ) {
            RadioButton(
                selected = selectedOption.value == "Applier",
                onClick = { selectedOption.value = "Applier" },
            )
            Text("Applier", color = Color.White, fontSize = 18.sp)
        }
    }
    return selectedOption.value
}

@Preview(showSystemUi = true)
@Composable
fun HelloUserNameProfilePhoto() {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Column(modifier = Modifier.weight(1f)) {
            TextUserName(name = "Kabira")
        }
        Column {
            Image(
                painter = painterResource(id = R.drawable.icon_account),
                contentDescription = "Companies",
                modifier = Modifier
                    .height(32.dp)
                    .width(32.dp)
                    .clip(CircleShape)
            )
        }

    }
}

@Composable
fun InfoBlock(label: String, description: String){
        Box(
            modifier = Modifier
                .background(Color.Black, shape = RoundedCornerShape(8.dp))
                .padding(bottom = 1.dp, end = 1.dp)
        ) {
            Box(
                modifier = Modifier
                    .background(TextFieldColor, shape = RoundedCornerShape(8.dp))
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Column {
                    TextCustom(textToShow = label, weight = 400, fontSize = 14)
                    Spacer(modifier = Modifier.height(12.dp))
                    TextCustom(textToShow = description, weight = 400, fontSize = 12)
                }
            }
        }
}