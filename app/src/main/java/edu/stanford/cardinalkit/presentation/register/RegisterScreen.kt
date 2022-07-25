package edu.stanford.cardinalkit.presentation

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
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
                        navController.navigate(Screens.LoginScreen.route)
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
                    text = "CardinalKit Study",
                    fontSize = 26.sp,
                    color= Color(0xFF790224),
                    fontWeight= FontWeight.SemiBold,
                    textAlign = TextAlign.Center)
                Text(
                    text = "Stanford Byers Center for Biodesign",
                    modifier = Modifier.padding(bottom = 30.dp),
                    fontSize = 13.sp,
                    textAlign = TextAlign.Center)

                var firstName by remember {
                    mutableStateOf("")
                }

                OutlinedTextField(
                    value = firstName,
                    onValueChange = { newText->
                        firstName = newText
                    },
                    label={Text(text="First Name")},
                    singleLine = true
                )

                var lastName by remember {
                    mutableStateOf("")
                }

                OutlinedTextField(
                    value = lastName,
                    onValueChange = { newText->
                        lastName = newText
                    },
                    label = {
                        Text(text="Last Name")
                    },
                    singleLine = true
                )

                var email by remember {
                    mutableStateOf("")
                }

                OutlinedTextField(
                    value = email,
                    onValueChange ={ newText ->
                        email = newText
                    },
                    label = {
                        Text(text="Email")
                    },
                    singleLine = true
                )

                var password by remember {
                    mutableStateOf("")
                }

                OutlinedTextField(
                    value = password,
                    onValueChange ={ newText ->
                        password = newText
                    },
                    label = {
                        Text(text="Password")
                    },
                    singleLine = true
                )

                var confirmPassword by remember {
                    mutableStateOf("")
                }

                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange ={ newText ->
                        confirmPassword = newText
                    },
                    label = {
                        Text(text="Confirm Password")
                    },
                    singleLine = true
                )

                Row(
                    Modifier.padding(top=25.dp),
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.Center,
                )  {
                    Button(
                        onClick = {
                            if(email.isEmpty() or password.isEmpty() or confirmPassword.isEmpty()){
                                Toast.makeText(context, "All fields must be completed.", Toast.LENGTH_SHORT).show()
                            } else if(password != confirmPassword) {
                                Toast.makeText(context, "Passwords must match.", Toast.LENGTH_SHORT).show()
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
                            text = "Register",
                            fontSize = 16.sp,
                            modifier = Modifier.padding(vertical=9.dp, horizontal = 70.dp))
                    }
                }
            }
        })
}
