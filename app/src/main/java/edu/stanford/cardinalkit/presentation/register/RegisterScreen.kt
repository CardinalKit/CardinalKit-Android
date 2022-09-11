package edu.stanford.cardinalkit.presentation

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    navController: NavHostController,
    viewModel: RegisterViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = {
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigate(Screens.RegisterMethod.route)
                    }) {
                        Icon(Icons.Filled.ArrowBack, "back Icon")
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors()
            )
        },
        content = { contentPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(contentPadding),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.app_name),
                    fontSize = 26.sp,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = stringResource(R.string.biodesign),
                    modifier = Modifier.padding(bottom = 30.dp),
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center
                )
                var firstName by remember {
                    mutableStateOf("")
                }
                var lastName by remember {
                    mutableStateOf("")
                }
                var email by remember {
                    mutableStateOf("")
                }
                var password by remember {
                    mutableStateOf("")
                }
                var confirm by remember {
                    mutableStateOf("")
                }
                var passwordVisible by rememberSaveable { mutableStateOf(false) }

                OutlinedTextField(
                    value = firstName,
                    onValueChange = { newText ->
                        firstName = newText
                    },
                    label = {
                        Text(text = stringResource(R.string.first_name))
                    },
                    singleLine = true,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        cursorColor = MaterialTheme.colorScheme.primary
                    )
                )
                OutlinedTextField(
                    value = lastName,
                    onValueChange = { newText ->
                        lastName = newText
                    },
                    label = {
                        Text(text = stringResource(R.string.last_name))
                    },
                    singleLine = true,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        cursorColor = MaterialTheme.colorScheme.primary
                    )
                )
                OutlinedTextField(
                    value = email,
                    onValueChange = { newText ->
                        email = newText
                    },
                    label = {
                        Text(text = stringResource(R.string.email))
                    },
                    singleLine = true,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        cursorColor = MaterialTheme.colorScheme.primary
                    )
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 60.dp)
                        .padding(vertical = 10.dp),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        text = stringResource(R.string.password_complexity_requirements)
                    )
                }
                OutlinedTextField(
                    value = password,
                    onValueChange = { newText ->
                        password = newText
                    },
                    label = {
                        Text(text = stringResource(R.string.password))
                    },
                    singleLine = true,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        cursorColor = MaterialTheme.colorScheme.primary
                    ),
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    trailingIcon = {
                        val image = if (passwordVisible) {
                            Icons.Filled.Visibility
                        } else Icons.Filled.VisibilityOff

                        val description = if (passwordVisible) "Hide password" else "Show password"

                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(imageVector = image, description)
                        }
                    }
                )
                OutlinedTextField(
                    value = confirm,
                    onValueChange = { newText ->
                        confirm = newText
                    },
                    label = {
                        Text(text = stringResource(R.string.confirm_password))
                    },
                    singleLine = true,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        cursorColor = MaterialTheme.colorScheme.primary
                    ),
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    trailingIcon = {
                        val image = if (passwordVisible) {
                            Icons.Filled.Visibility
                        } else Icons.Filled.VisibilityOff

                        val description = if (passwordVisible) "Hide password" else "Show password"

                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(imageVector = image, description)
                        }
                    }
                )

                Row(
                    Modifier.padding(top = 25.dp),
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = {
                            if (email.isEmpty() or password.isEmpty() or confirm.isEmpty()) {
                                Toast.makeText(
                                    context,
                                    R.string.required_field_empty,
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else if (!viewModel.isValidEmail(email)) {
                                Toast.makeText(context, "Enter valid email", Toast.LENGTH_SHORT)
                                    .show()
                            } else if (password != confirm) {
                                Toast.makeText(
                                    context,
                                    R.string.passwords_unmatched,
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else if (!viewModel.isValidPassword(password)) {
                                if (password.length < 8) {
                                    Toast.makeText(
                                        context,
                                        "Password has to be at least 8 characters",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else if (password.firstOrNull { it.isDigit() } == null) {
                                    Toast.makeText(
                                        context,
                                        "Password has to contain 1 digit",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else if (password.filter { it.isLetter() }
                                    .filter { it.isUpperCase() }.firstOrNull() == null
                                ) {
                                    Toast.makeText(
                                        context,
                                        "Password has to contain 1 uppercase",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else if (password.filter { it.isLetter() }
                                    .filter { it.isLowerCase() }.firstOrNull() == null
                                ) {
                                    Toast.makeText(
                                        context,
                                        "Password has to contain 1 lowercase",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else if (password.firstOrNull { !it.isLetterOrDigit() } == null) {
                                    Toast.makeText(
                                        context,
                                        "Password has to contain a special character",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            } else {
                                viewModel.signUp(email, password)
                            }
                        },
                        shape = RoundedCornerShape(50),
                        colors = ButtonDefaults.buttonColors(
                            contentColor = MaterialTheme.colorScheme.onPrimary,
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        androidx.compose.material.Text(
                            text = stringResource(R.string.register_button),
                            fontSize = 16.sp,
                            modifier = Modifier.padding(vertical = 9.dp, horizontal = 70.dp)
                        )
                    }
                }
            }
        }
    )

    when (val signUpResponse = viewModel.signUpState.value) {
        is Response.Loading -> ProgressIndicator()
        is Response.Error -> signUpResponse.e?.let {
            Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
        }
        is Response.Success -> {}
    }

    when (val saveUserResponse = viewModel.saveUserState.value) {
        is Response.Loading -> ProgressIndicator()
        is Response.Error -> saveUserResponse.e?.let {
            Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
        }
        is Response.Success -> {}
    }
}
