package edu.stanford.cardinalkit.presentation.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import edu.stanford.cardinalkit.R
import edu.stanford.cardinalkit.domain.models.Response
import edu.stanford.cardinalkit.presentation.common.ProgressIndicator
import edu.stanford.cardinalkit.presentation.navigation.Screens
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForgotPassword(
    viewModel: LoginViewModel = hiltViewModel(),
    navController: NavController
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope: CoroutineScope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            SmallTopAppBar(
                title = {
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigate(Screens.LoginScreen.route)
                    }) {
                        Icon(Icons.Filled.ArrowBack, "back Icon")
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors()
            )
        },
        containerColor = Color(0xFFFFFFFF),
        content = { padding ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(padding)
                    .padding(horizontal = 60.dp)
                    .fillMaxHeight()
            ) {
                Spacer(modifier = Modifier.height(150.dp))
                Image(
                    modifier = Modifier
                        .fillMaxWidth(0.4f)
                        .fillMaxHeight(0.2f),
                    painter = painterResource(id = R.drawable.second),
                    contentDescription = "lock"
                )
                Text(
                    text = stringResource(R.string.forgot_password),
                    fontSize = 30.sp,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.SemiBold,
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = stringResource(R.string.reset_password_instructions)
                )
                Spacer(modifier = Modifier.height(10.dp))
                var emailPassword by remember {
                    mutableStateOf("")
                }
                OutlinedTextField(
                    value = emailPassword,
                    onValueChange = { newText ->
                        emailPassword = newText
                    },
                    label = {
                        Text(text = stringResource(R.string.email_address))
                    },
                    singleLine = true,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        cursorColor = MaterialTheme.colorScheme.primary
                    ),
                )
                Row(
                    Modifier
                        .padding(top = 10.dp)
                        .fillMaxWidth(1f),
                    horizontalArrangement = Arrangement.End

                ) {
                    TextButton(
                        onClick = {
                            viewModel.resetPassword(emailPassword)
                        },
                        colors = ButtonDefaults.buttonColors(
                            contentColor = MaterialTheme.colorScheme.onPrimary,
                            containerColor = MaterialTheme.colorScheme.primary,
                        )
                    ) {
                        Text(
                            text = stringResource(R.string.submit),
                            fontSize = 15.sp,
                            modifier = Modifier.padding(horizontal = 5.dp),
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            }

        }

    )
    val context = LocalContext.current
    when (val resetPassword = viewModel.signInState.value) {
        is Response.Loading -> ProgressIndicator()
        is Response.Error -> resetPassword.e?.let {
            coroutineScope.launch {
                snackbarHostState.showSnackbar(context.getString(R.string.email_invalid_message))
            }
        }
        is Response.Success -> {
            coroutineScope.launch {
                snackbarHostState.showSnackbar(context.getString(R.string.reset_password_email_message))
            }
        }
    }
}