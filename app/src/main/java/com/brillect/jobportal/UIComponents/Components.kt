package com.brillect.jobportal.UIComponents

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.brillect.jobportal.Data.JobType
import com.brillect.jobportal.Data.WorkPlace
import com.brillect.jobportal.R
import com.brillect.jobportal.ui.theme.BackgroundColor
import com.brillect.jobportal.ui.theme.PrimaryColor
import com.brillect.jobportal.ui.theme.TextFieldColor

val textFontFamily = FontFamily(Font(R.font.product_sans))

@Composable
fun SingleLineTextField(
    description: String,
    textSingleLine: String = "",
    modifier: Modifier = Modifier,
    readOnlyMode: Boolean = false
): String {
    var textEntered = ""
    Column(modifier = modifier.fillMaxWidth()) {
        Text_18_White(textToShow = description, 400)
        Spacer(modifier = Modifier.height(22.dp))
        textEntered = customTextFieldSingleLine(textSingleLine, readOnlyMode)
    }
    return textEntered
}

@Composable
fun MultiLineTextField(
    description: String,
    textMultiLine: String = "",
    readOnlyMode: Boolean = false
): String {
    var textEntered = ""
    Column(modifier = Modifier.fillMaxWidth()) {
        Text_18_White(textToShow = description, 400)
        Spacer(modifier = Modifier.height(22.dp))
        textEntered = customTextFieldMultiLine(textMultiLine, readOnlyMode)
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
fun customTextFieldSingleLine(textSingleLine: String = "", readOnlyMode: Boolean): String {
    var fullName by rememberSaveable {
        mutableStateOf(textSingleLine)
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
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                singleLine = true,
                readOnly = readOnlyMode
            )
        }
    }
    return fullName
}

@Composable
fun customTextFieldSingleLineReadOnly(textSingleLine: String = "", modifier: Modifier): String {
    var fullName = textSingleLine
    Box(
        modifier = modifier
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
                ),
                enabled = false,
                readOnly = true
            )
        }
    }
    return fullName
}

@Composable
fun customTextFieldMultiLine(textMultiLine: String = "", readOnlyMode: Boolean = false): String {
    var fullName by rememberSaveable {
        mutableStateOf(textMultiLine)
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
                maxLines = 4,
                modifier = Modifier
                    .height(88.dp)
                    .fillMaxWidth()
                    .padding(top = 11.dp, bottom = 10.dp, start = 16.dp, end = 16.dp),
                textStyle = TextStyle(
                    color = PrimaryColor, fontFamily = textFontFamily,
                    fontSize = 14.sp
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                singleLine = false,
                readOnly = readOnlyMode
            )
        }
    }
    return fullName
}

@Composable
fun customTextFieldForPassword(): String {
    var password by rememberSaveable {
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

@Composable
fun HelloUserNameProfilePhoto(userName: String, onImageClick: () -> Unit) {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Column(modifier = Modifier.weight(1f)) {
            TextUserName(name = userName)
        }
        Column {
            Image(
                painter = painterResource(id = R.drawable.icon_account),
                contentDescription = "Companies",
                modifier = Modifier
                    .height(32.dp)
                    .width(32.dp)
                    .clip(CircleShape)
                    .clickable { onImageClick() }
            )
        }

    }
}

@Composable
fun HelloUserNameProfilePhotoClickable(onClick: () -> Unit) {
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
                    .clickable { onClick() }
            )
        }

    }
}

@Composable
fun InfoBlock(label: String, description: String) {
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
                TextCustom(textToShow = label, weight = 700, fontSize = 18)
                Spacer(modifier = Modifier.height(12.dp))
                TextCustom(textToShow = description, weight = 400, fontSize = 16)
            }
        }
    }
}

// custom text field with drop down
@Composable
fun <T> CustomTextFieldWithDropdownJobType(
    items: List<T>,
    selectedItem: T,
    onItemSelected: (T) -> Unit,
    extractLabel: (T) -> String
) {
    var isDropdownExpanded by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .background(Color.Black, shape = RoundedCornerShape(8.dp))
            .padding(bottom = 1.dp, end = 1.dp)
    ) {
        Box(
            modifier = Modifier
                .background(TextFieldColor, shape = RoundedCornerShape(8.dp))
                .weight(1f)
                .height(44.dp)
                .clickable { isDropdownExpanded = true }
                .padding(start = 16.dp, end = 16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(end = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = extractLabel(selectedItem),
                    color = PrimaryColor,
                    fontSize = 14.sp,
                    fontFamily = textFontFamily
                )
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = null,
                    tint = Color.Black
                )
            }
        }

        DropdownMenu(
            expanded = isDropdownExpanded,
            modifier = Modifier
                .width(300.dp)
                .align(Alignment.CenterVertically),
            onDismissRequest = { isDropdownExpanded = false }
        ) {
            items.forEach { item ->
                DropdownMenuItem(text = {
                    Text(
                        text = extractLabel(item),
                        fontSize = 16.sp,
                        fontFamily = textFontFamily
                    )
                }, onClick = {
                    onItemSelected(item)
                    isDropdownExpanded = false
                })
            }
        }
    }

}

@Composable
fun customTextFieldWithDropdownWorkplace(): String {
    var workPlace by remember { mutableStateOf(WorkPlace.ON_SITE) }
    var isDropdownExpanded by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .background(Color.Black, shape = RoundedCornerShape(8.dp))
            .padding(bottom = 1.dp, end = 1.dp)
    ) {
        Box(
            modifier = Modifier
                .background(TextFieldColor, shape = RoundedCornerShape(8.dp))
                .weight(1f)
                .height(44.dp)
                .clickable { isDropdownExpanded = true }
                .padding(start = 16.dp, end = 16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(end = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = workPlace.label,
                    color = PrimaryColor,
                    fontSize = 14.sp,
                    fontFamily = textFontFamily
                )
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = null,
                    tint = Color.Black
                )
            }
        }

        DropdownMenu(
            expanded = isDropdownExpanded,
            modifier = Modifier
                .width(300.dp)
                .align(Alignment.CenterVertically),
            onDismissRequest = { isDropdownExpanded = false }
        ) {
            WorkPlace.values().forEach { type ->
                DropdownMenuItem(text = {
                    Text(
                        text = type.label,
                        fontSize = 16.sp,
                        fontFamily = textFontFamily
                    )
                }, onClick = {
                    workPlace = type
                    isDropdownExpanded = false
                })
            }
        }
    }

    return workPlace.label
}

@Composable
fun ProgressBar(){
    LinearProgressIndicator(
        modifier = Modifier
            .fillMaxWidth(),
        trackColor = BackgroundColor
    )
}