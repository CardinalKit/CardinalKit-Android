package edu.stanford.cardinalkit.presentation.login

import android.app.Activity.RESULT_OK
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality.Companion.Medium
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
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
import edu.stanford.cardinalkit.presentation.welcome.ReturningUser


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInMethod(
    viewModel: LoginViewModel = hiltViewModel(),
    navController: NavHostController
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigate(Screens.JoinStudyScreen.route)
                    }) {
                        Icon(Icons.Filled.ArrowBack, "back Icon")
                    }
                },
                backgroundColor = Color.White,
                contentColor = Color.Black,
                elevation = 0.dp
            )
        },
        containerColor = Color(0xFFFFFFFF),
        content = {contentPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(contentPadding),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(modifier = Modifier
                    .padding(top = 10.dp)
                    .fillMaxWidth(0.5f)
                    .fillMaxHeight(0.16f),
                    painter = painterResource(R.drawable.branding_light),
                    contentDescription = "branding" )
                Image(modifier = Modifier
                    .fillMaxWidth(0.55f)
                    .fillMaxHeight(0.3f),
                    painter = painterResource(R.drawable.login),
                    contentDescription = "branding" )
                Text(
                    text = stringResource(R.string.login_screen_title),
                    fontSize = 26.sp,
                    color= Color(0xFF790224),
                    fontWeight=FontWeight.SemiBold,
                    textAlign = TextAlign.Center)
                Text(
                    text = stringResource(R.string.choose_your_sign_in_method),
                    modifier = Modifier.padding(bottom = 20.dp),
                    fontSize = 13.sp,
                    textAlign = TextAlign.Center)

                Spacer(modifier = Modifier.height(60.dp))

                Column(modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 40.dp)) {
                    SignIn(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {navController.navigate(Screens.LoginScreen.route)})
                    Spacer(modifier = Modifier.height(10.dp))


                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(top = 7.dp)
                    )  {

                        OutlinedButton(
                            onClick = {viewModel.oneTapSignIn()},
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color.White,
                            ),
                            modifier = Modifier
                                .fillMaxWidth(),
                            shape = RoundedCornerShape(50.dp)


                        ) {
                            Image(modifier = Modifier.fillMaxHeight(.2f).fillMaxWidth(.25f),painter = painterResource(R.drawable.btn_google), contentDescription ="google" )
                            Text(
                                text = stringResource(R.string.sign_in_with_google_button),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.Gray,
                                modifier = Modifier.padding(vertical = 8.dp)
                                    .padding(end = 40.dp)
                            )
                        }
                    }

                }

            }
        }

    )

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
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

    when(val oneTapSignInResponse = viewModel.oneTapSignInState.value) {
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
            }
        }
    }

    when(val signInResponse = viewModel.signInState.value) {
        is Response.Loading -> ProgressIndicator()
        is Response.Success -> {
            signInResponse.data?.let { isNewUser ->
                if (isNewUser) {
                    LaunchedEffect(isNewUser) {
                        viewModel.saveUser()
                    }
                } else {
                    navController.navigate(Screens.MainScreen.route)
                }
            }
        }
        is Response.Error -> signInResponse.e?.let {
            LaunchedEffect(Unit) {
                print(it)
            }
        }
    }

    when(val saveUserResponse = viewModel.saveUserState.value) {
        is Response.Loading -> ProgressIndicator()
        is Response.Success -> {
            saveUserResponse.data?.let { isUserCreated ->
                if (isUserCreated) {
                    navController.navigate(Screens.HomeScreen.route)
                }
            }
        }
        is Response.Error -> saveUserResponse.e?.let {
            LaunchedEffect(Unit) {
                print(it)
            }
        }
    }

}

@Composable
fun SignIn(
    modifier: Modifier,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Center,
    )  {
        Button(
            onClick = onClick,
            shape= RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.Gray,
                backgroundColor = Color.LightGray
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            androidx.compose.material.Text(
                text = stringResource(R.string.sign_in_with_email),
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(vertical = 8.dp).padding(horizontal = 20.dp)
            )
        }
    }

}


