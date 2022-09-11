package edu.stanford.cardinalkit.presentation.login

import android.app.Activity.RESULT_OK
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
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
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import edu.stanford.cardinalkit.R
import edu.stanford.cardinalkit.domain.models.Response
import edu.stanford.cardinalkit.presentation.common.ProgressIndicator
import edu.stanford.cardinalkit.presentation.navigation.Screens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val context = LocalContext.current
    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = {
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigate(Screens.SignInMethod.route)
                    }) {
                        Icon(Icons.Filled.ArrowBack, "back Icon")
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors()
            )
        },
        containerColor = MaterialTheme.colorScheme.surface,
        content = { contentPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(contentPadding),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    modifier = Modifier
                        .fillMaxWidth(0.55f)
                        .fillMaxHeight(0.3f),
                    painter = painterResource(R.drawable.login),
                    contentDescription = "branding"
                )
                Text(
                    text = stringResource(R.string.login_screen_title),
                    fontSize = 26.sp,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Enter your email and password",
                    modifier = Modifier.padding(bottom = 20.dp),
                    fontSize = 13.sp,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(50.dp))

                var emailAuthCredential by remember {
                    mutableStateOf("")
                }
                var password by remember {
                    mutableStateOf("")
                }
                var passwordVisible by rememberSaveable { mutableStateOf(false) }
                OutlinedTextField(
                    value = emailAuthCredential,
                    onValueChange = { newText ->
                        emailAuthCredential = newText
                    },
                    label = { Text(text = stringResource(R.string.email)) },
                    singleLine = true,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        cursorColor = MaterialTheme.colorScheme.primary
                    )
                )

                OutlinedTextField(
                    value = password,
                    onValueChange = { newText ->
                        password = newText
                    },
                    label = { Text(text = stringResource(R.string.password)) },
                    singleLine = true,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        cursorColor = MaterialTheme.colorScheme.primary
                    ),
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
                    Modifier
                        .padding(top = 5.dp)
                        .fillMaxWidth()
                        .padding(horizontal = 55.dp),
                    horizontalArrangement = Arrangement.SpaceBetween

                ) {
                    TextButton(
                        onClick = { navController.navigate(Screens.ForgotPasswordScreen.route) },
                        colors = ButtonDefaults.buttonColors(
                            contentColor = MaterialTheme.colorScheme.onSurface,
                            containerColor = MaterialTheme.colorScheme.surface
                        ),
                    ) {
                        Text(
                            text = stringResource(R.string.forgot_password_button),
                            fontSize = 13.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    TextButton(
                        onClick = {
                            val email = emailAuthCredential.trim()
                            if (email.isEmpty() and password.isNotEmpty()) {
                                Toast.makeText(context, R.string.email_empty, Toast.LENGTH_SHORT)
                                    .show()
                            } else if (password.isEmpty() and email.isNotEmpty()) {
                                Toast.makeText(context, R.string.password_empty, Toast.LENGTH_SHORT)
                                    .show()
                            } else if (email.isEmpty() and password.isEmpty()) {
                                Toast.makeText(
                                    context,
                                    R.string.email_and_password_empty,
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                viewModel.signIn(email, password)
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            contentColor = MaterialTheme.colorScheme.onPrimary,
                            containerColor = MaterialTheme.colorScheme.primary,
                        )
                    ) {
                        Text(
                            text = stringResource(R.string.sign_in_button),
                            fontSize = 15.sp,
                            modifier = Modifier.padding(horizontal = 5.dp),
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
                TextButton(
                    onClick = { navController.navigate(Screens.RegisterScreen.route) },
                    colors = ButtonDefaults.buttonColors(
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                ) {
                    Text(
                        text = stringResource(R.string.create_account_button),
                        fontSize = 13.sp
                    )
                }
            }
        }
    )

    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                try {
                    val credentials = viewModel.client.getSignInCredentialFromIntent(result.data)
                    val googleIdToken = credentials.googleIdToken
                    val googleCredentials = GoogleAuthProvider.getCredential(googleIdToken, null)
                    viewModel.signInWithGoogle(googleCredentials)
                } catch (it: ApiException) {
                    print(it)
                }
            }
        }

    fun launch(signInResult: BeginSignInResult) {
        val intent = IntentSenderRequest.Builder(signInResult.pendingIntent.intentSender).build()
        launcher.launch(intent)
    }

    when (val oneTapSignInResponse = viewModel.oneTapSignInState.value) {
        is Response.Loading -> ProgressIndicator()
        is Response.Success -> {
            oneTapSignInResponse.data?.let {
                LaunchedEffect(it) {
                    launch(it)
                }
            }
        }
        is Response.Error -> {
            oneTapSignInResponse.e?.let {
                LaunchedEffect(Unit) {
                    print(it)
                    if (it.message == "16: Cannot find a matching credential.") {
                        viewModel.oneTapSignIn()
                    }
                }
                Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    when (val signInResponse = viewModel.signInState.value) {
        is Response.Loading -> ProgressIndicator()
        is Response.Error -> signInResponse.e?.let {
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
