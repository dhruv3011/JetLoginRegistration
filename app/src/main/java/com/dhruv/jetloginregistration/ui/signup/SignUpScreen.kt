package com.dhruv.jetloginregistration.ui.signup

import android.R
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.LinkInteractionListener
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dhruv.jetloginregistration.ui.components.HeaderText
import com.dhruv.jetloginregistration.ui.components.LoginTextField
import com.dhruv.jetloginregistration.ui.login.defaultPadding
import com.dhruv.jetloginregistration.ui.login.itemSpacing
import com.dhruv.jetloginregistration.ui.theme.JetLoginRegistrationTheme


@Composable
fun SignUpScreen(
    onSignUpClicked:() -> Unit,
    onLoginClicked:() -> Unit,
    onPrivacyClicked:() -> Unit,
    onPolicyClicked:() -> Unit,
) {

    val context = LocalContext.current

    val (firstName, setFirstName) = rememberSaveable {
        mutableStateOf("")
    }

    val (lastName, setLastName) = rememberSaveable {
        mutableStateOf("")
    }

    val (email, setEmail) = rememberSaveable {
        mutableStateOf("")
    }

    val (password, setPassword) = rememberSaveable {
        mutableStateOf("")
    }

    val (confirmPassword, setConfirmPassword) = rememberSaveable {
        mutableStateOf("")
    }

    val (agreeChecked, onAgreeChecked) = rememberSaveable {
        mutableStateOf(false)
    }

    var isPasswordSame by remember { mutableStateOf(false) }
    val isFieldEmpty = firstName.isNotEmpty()
            && lastName.isNotEmpty()
            && email.isNotEmpty()
            && password.isNotEmpty()
            && confirmPassword.isNotEmpty() && agreeChecked

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(defaultPadding)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AnimatedVisibility(isPasswordSame) {
            Text(
                text = "Password is not matching",
                color = Color.Red
            )
        }
        HeaderText(
            text =  "Sign Up",
            modifier = Modifier
                .padding(vertical = defaultPadding)
                .align(alignment = Alignment.Start)
        )

        LoginTextField(
            value = firstName,
            onValueChange =  setFirstName,
            labelText = "First Name",
            leadingIcon = Icons.Default.Person,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(itemSpacing))

        LoginTextField(
            value = lastName,
            onValueChange =  setLastName,
            labelText = "Last Name",
            leadingIcon = Icons.Default.Person,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(itemSpacing))

        LoginTextField(
            value = email,
            onValueChange =  setEmail,
            labelText = "Email",
            leadingIcon = Icons.Default.Email,
            modifier = Modifier.fillMaxWidth(),
            keyboardType = KeyboardType.Email
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


        LoginTextField(
            value = confirmPassword,
            onValueChange =  setConfirmPassword,
            labelText = "Confirm Password",
            leadingIcon = Icons.Default.Lock,
            modifier = Modifier.fillMaxWidth(),
            keyboardType = KeyboardType.Password,
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(Modifier.height(itemSpacing))

        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ){
            Row (verticalAlignment = Alignment.CenterVertically) {
                Checkbox(checked = agreeChecked, onCheckedChange = onAgreeChecked)
                val privacyText = "Privacy"
                val policyText = "Policy"
                val annottedString = buildAnnotatedString {
                    withStyle(SpanStyle(
                        color = MaterialTheme.colorScheme.onBackground
                    )) {
                        append("I Agree with ")
                    }
                    append("")
                    withStyle(SpanStyle(
                        color = MaterialTheme.colorScheme.primary
                    )) {
                        pushStringAnnotation(tag = privacyText, privacyText)
                        append(privacyText)
                    }
                    append(" And ")
                    withStyle(SpanStyle(
                        color = MaterialTheme.colorScheme.primary
                    )) {
                        pushStringAnnotation(tag = policyText, policyText)
                        append(policyText)
                    }
                }

                ClickableText(
                    annottedString,
                ) { offset ->
                    annottedString.getStringAnnotations(offset, offset).forEach {
                        when (it.tag) {
                            privacyText -> onPrivacyClicked()
                            policyText -> onPolicyClicked()
                        }
                    }
                }
            }
        }
        Spacer(Modifier.height(itemSpacing + 8.dp))

        Button(
            onClick = {
                isPasswordSame = password != confirmPassword
                if (!isPasswordSame) {
                    onSignUpClicked()
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = isFieldEmpty
        ) {
            Text("Sign Up")
        }

        val signInText = "Sign In"
        val signInAnnotation = buildAnnotatedString {
            append("Already have account? ")
            pushLink(LinkAnnotation.Clickable(
                tag = signInText,
                linkInteractionListener = LinkInteractionListener {
                    onLoginClicked()
                }
            ))
            withStyle(SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                pushStringAnnotation(signInText, signInText)
                append(signInText)
            }
            pop()
        }

        Spacer(Modifier.height(itemSpacing + 8.dp))

        Text(
            text = signInAnnotation,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun PrevLoginScreen() {
    JetLoginRegistrationTheme {
        SignUpScreen({}, {}, {}, {})
    }
}