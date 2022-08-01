package edu.stanford.cardinalkit.presentation

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import edu.stanford.cardinalkit.R
import edu.stanford.cardinalkit.domain.models.Response
import edu.stanford.cardinalkit.presentation.common.ProgressIndicator
import edu.stanford.cardinalkit.presentation.navigation.Screens
import edu.stanford.cardinalkit.presentation.register.RegisterViewModel


@Composable
fun RegisterScreen(
    navController: NavHostController,
    viewModel: RegisterViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigate(Screens.SignInMethod.route)
                    }) {
                        Icon(Icons.Filled.ArrowBack, "back Icon")
                    }
                },
                backgroundColor = Color.White,
                contentColor = Color.Black,
                elevation = 0.dp
            )
        },
        content = {contentPadding ->
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding),
                horizontalAlignment = Alignment.CenterHorizontally){
                Text(
                    text = stringResource(R.string.app_name),
                    fontSize = 26.sp,
                    color= Color(0xFF790224),
                    fontWeight= FontWeight.SemiBold,
                    textAlign = TextAlign.Center)
                Text(
                    text = stringResource(R.string.biodesign),
                    modifier = Modifier.padding(bottom = 30.dp),
                    fontSize = 13.sp,
                    textAlign = TextAlign.Center)
                var firstName by remember{
                    mutableStateOf("")
                }
                var lastName by remember{
                    mutableStateOf("")
                }
                var email by remember{
                    mutableStateOf("")
                }
                var password by remember{
                    mutableStateOf("")
                }
                var confirm by remember{
                    mutableStateOf("")
                }
                var passwordVisible by rememberSaveable { mutableStateOf(false) }

                OutlinedTextField(
                    value = firstName,
                    onValueChange ={ newText->
                        firstName = newText
                    },
                    label = {
                        Text(text = stringResource(R.string.first_name))
                    },
                    singleLine = true,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.Gray,
                        cursorColor = Color.LightGray),
                )
                OutlinedTextField(
                    value = lastName,
                    onValueChange ={ newText->
                        lastName = newText
                    },
                    label = {
                        Text(text = stringResource(R.string.last_name))
                    },
                    singleLine = true,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.Gray,
                        cursorColor = Color.LightGray),
                )
                OutlinedTextField(
                    value = email,
                    onValueChange ={ newText->
                        email = newText
                    },
                    label = {
                        Text(text = stringResource(R.string.email))
                    },
                    singleLine = true,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.Gray,
                        cursorColor = Color.LightGray),
                )
                OutlinedTextField(
                    value = password,
                    onValueChange ={ newText->
                        password = newText
                    },
                    label = {
                        Text(text = stringResource(R.string.password))
                    },
                    singleLine = true,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.Gray,
                        cursorColor = Color.LightGray),
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    trailingIcon = {
                        val image = if (passwordVisible)
                            Icons.Filled.Visibility
                        else Icons.Filled.VisibilityOff

                        val description = if (passwordVisible) "Hide password" else "Show password"

                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(imageVector = image, description)
                        }
                    }
                )
                OutlinedTextField(
                    value = confirm,
                    onValueChange ={ newText->
                        confirm = newText
                    },
                    label = {
                        Text(text = stringResource(R.string.confirm_password))
                    },
                    singleLine = true,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.Gray,
                        cursorColor = Color.LightGray),
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    trailingIcon = {
                        val image = if (passwordVisible)
                            Icons.Filled.Visibility
                        else Icons.Filled.VisibilityOff

                        val description = if (passwordVisible) "Hide password" else "Show password"

                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(imageVector = image, description)
                        }
                    }
                )

                Row(
                    Modifier.padding(top=25.dp),
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.Center,
                )  {
                    Button(
                        onClick = {
                            if(email.isEmpty() or password.isEmpty() or confirm.isEmpty()){
                                Toast.makeText(context, R.string.required_field_empty, Toast.LENGTH_SHORT).show()
                            } else if(password != confirm) {
                                Toast.makeText(context, R.string.passwords_unmatched, Toast.LENGTH_SHORT).show()
                            } else {
                                viewModel.signUp(email, password)
                            }
                        },
                        shape= RoundedCornerShape(50),
                        colors = ButtonDefaults.buttonColors(
                            contentColor = Color.LightGray,
                            backgroundColor = Color.DarkGray
                        )
                    ) {
                        androidx.compose.material.Text(
                            text = stringResource(R.string.register_button),
                            fontSize = 16.sp,
                            modifier = Modifier.padding(vertical=9.dp, horizontal = 70.dp))
                    }
                }
            }
        })

    when(val signUpResponse = viewModel.signUpState.value) {
        is Response.Loading -> ProgressIndicator()
        is Response.Error -> signUpResponse.e?.let {
            Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
        }
    }

    when(val saveUserResponse = viewModel.saveUserState.value) {
        is Response.Loading -> ProgressIndicator()
        is Response.Error -> saveUserResponse.e?.let {
            Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
        }
    }
}
