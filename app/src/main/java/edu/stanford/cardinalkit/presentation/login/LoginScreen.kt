package edu.stanford.cardinalkit.presentation.login

import android.app.Activity.RESULT_OK
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality.Companion.Medium
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
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
    Scaffold(
        containerColor = Color(0xFFFFFFFF),
        content = { contentPadding ->
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
                    .fillMaxWidth(0.5f)
                    .fillMaxHeight(0.28f),
                    painter = painterResource(R.drawable.login),
                    contentDescription = "branding" )
                Text(
                    text = "CardinalKit Study",
                    fontSize = 26.sp,
                    color= Color(0xFF790224),
                    fontWeight=FontWeight.SemiBold,
                    textAlign = TextAlign.Center)
                Text(
                    text = "Stanford Department of Medicine",
                    modifier = Modifier.padding(bottom = 20.dp),
                    fontSize = 13.sp,
                    textAlign = TextAlign.Center)

                var text by remember{
                    mutableStateOf("")
                }
                OutlinedTextField(
                    value = text,
                    onValueChange ={ newText->
                        text = newText
                    },
                    label={Text(text="Email")},
                    singleLine = true,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.Gray)
                )
                OutlinedTextField(
                    value = text,
                    onValueChange ={ newText->
                        text = newText
                    },
                    label={Text(text="Password")},
                    singleLine = true,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.Gray,
                    cursorColor = Color.LightGray)
                )
                Row(
                    Modifier
                        .padding(top = 5.dp)
                        .fillMaxWidth()
                        .padding(horizontal = 65.dp),
                    horizontalArrangement = Arrangement.SpaceBetween

                ){
                    TextButton(
                        onClick = {navController.navigate(Screens.RegisterScreen.route)},
                        colors = ButtonDefaults.buttonColors(
                            contentColor = Color.DarkGray,
                            backgroundColor = Color.White
                        ),

                    ) {
                        Text(
                            text="Forgot Password?",
                            fontSize = 13.sp,
                            color=Color.DarkGray
                        )

                    }
                    TextButton(
                        onClick = {navController.navigate(Screens.MainScreen.route)},
                        colors = ButtonDefaults.buttonColors(
                            contentColor = Color.Gray,
                            backgroundColor = Color.LightGray,
                        )
                    ) {
                        Text(
                            text = "Sign in ",
                            fontSize = 15.sp,
                            modifier = Modifier.padding(horizontal = 5.dp),
                            color=Color.Gray
                        )
                    }


                }


                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(top = 7.dp)
                )  {

                    OutlinedButton(
                        onClick = {viewModel.oneTapSignIn()},
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.White,
                        ),
                        modifier = Modifier.padding(horizontal = 7.dp),
                        shape = RoundedCornerShape(50.dp)


                    ) {
                        Image(
                            modifier= Modifier
                                .fillMaxHeight(0.4f)
                                .fillMaxWidth(0.165f)
                                .padding(start = 7.dp),
                            painter = painterResource(R.drawable.btn_google), contentDescription = "google btn"
                        )
                        Text(
                            text = "Continue with Google",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Gray,
                            modifier=Modifier.padding(end=37.dp)
                        )
                    }
                }
                TextButton(
                    onClick = {navController.navigate(Screens.RegisterScreen.route)},
                    colors = ButtonDefaults.buttonColors(
                        contentColor = Color.Black,
                        backgroundColor = Color.White
                    ),

                    ) {
                    Text(
                        text="Make an Account",
                        fontSize = 13.sp
                    )

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


