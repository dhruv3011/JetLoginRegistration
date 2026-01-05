package com.dhruv.jetloginregistration.ui.login

import android.widget.TextView
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dhruv.jetloginregistration.ui.components.HeaderText
import com.dhruv.jetloginregistration.ui.components.LoginTextField
import com.dhruv.jetloginregistration.ui.theme.JetLoginRegistrationTheme

val defaultPadding = 16.dp
val itemSpacing = 8.dp

@Composable
fun LoginScreen(onLoginClick:() -> Unit, onSignUpClicked:() -> Unit) {

    val (userName, setUserName) = rememberSaveable {
        mutableStateOf("")
    }

    val (password, setPassword) = rememberSaveable {
        mutableStateOf("")
    }

    val (checked, onCheckedChanged) = rememberSaveable {
        mutableStateOf(false)
    }

    val isFieldEmpty = userName.isNotEmpty() && password.isNotEmpty()

    Column(
        modifier = Modifier.fillMaxSize()
            .padding(defaultPadding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HeaderText(
            text =  "Login",
            modifier = Modifier.padding(vertical = defaultPadding)
                .align(alignment = Alignment.Start)
        )

        LoginTextField(
            value = userName,
            onValueChange =  setUserName,
            labelText = "Username",
            leadingIcon = Icons.Default.Person,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(itemSpacing))

        LoginTextField(
            value = password,
            onValueChange =  setPassword,
            labelText = "Password",
            leadingIcon = Icons.Default.Lock,
            modifier = Modifier.fillMaxWidth(),
            keyboardType = KeyboardType.Password,
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(Modifier.height(itemSpacing))

        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Row (verticalAlignment = Alignment.CenterVertically) {
                Checkbox(checked = checked, onCheckedChange = onCheckedChanged)
                Text("Remember me")
            }
            TextButton(onClick = {}) {
                Text("Forgot Password?")

            }
        }
        Spacer(Modifier.height(itemSpacing))

        Button(
            onClick = onLoginClick,
            modifier = Modifier.fillMaxWidth(),
            enabled = isFieldEmpty
        ) {
            Text("Login")
        }

        loginBottomPart(onSignUp = onSignUpClicked,
            modifier = Modifier.fillMaxSize().wrapContentSize(align = Alignment.BottomCenter))
    }
}

@Composable
fun loginBottomPart(
    onSignUp:() -> Unit,
    modifier: Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Don't have an Account?")
            Spacer(Modifier.height(itemSpacing))
            TextButton(onClick = onSignUp) {
                Text("Sign Up")
            }
        }
    }
}


@Preview(showSystemUi = true)
@Composable
fun PrevLoginScreen() {
    JetLoginRegistrationTheme {
        LoginScreen({}, {})
    }
}