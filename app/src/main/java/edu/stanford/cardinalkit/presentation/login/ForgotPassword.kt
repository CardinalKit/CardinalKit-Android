package edu.stanford.cardinalkit.presentation.login

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import edu.stanford.cardinalkit.R
import edu.stanford.cardinalkit.presentation.navigation.Screens
import edu.stanford.cardinalkit.ui.theme.PrimaryTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForgotPassword(
    navController : NavController
){
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
        containerColor = Color(0xFFFFFFFF),
        content ={
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(horizontal = 60.dp)
                    .fillMaxHeight()){
                Spacer(modifier = Modifier.height(150.dp))
                Image(
                    modifier = Modifier
                        .fillMaxWidth(0.4f)
                        .fillMaxHeight(0.2f),
                    painter=painterResource(id =R.drawable.second),
                    contentDescription = "lock")
                Text(
                    text="Forgot Password",
                    fontSize = 30.sp,
                    color = Color.Gray,
                    fontWeight = FontWeight.SemiBold,
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text="Enter your email address and a link will be sent to reset your password"
                )
                Spacer(modifier = Modifier.height(10.dp))
                var email by remember{
                    mutableStateOf("")
                }
                OutlinedTextField(
                    value = email,
                    onValueChange ={ newText->
                        email = newText
                    },
                    label = {
                        androidx.compose.material3.Text(text = "Email Address")
                    },
                    singleLine = true,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.Gray,
                        cursorColor = Color.LightGray),
                )
                Row(
                    Modifier
                        .padding(top = 10.dp)
                        .fillMaxWidth(1f),
                    horizontalArrangement = Arrangement.End

                ) {
                    TextButton(
                        onClick = {
                        },
                        colors = ButtonDefaults.buttonColors(
                            contentColor = Color.Gray,
                            backgroundColor = Color.LightGray,
                        )
                    ) {
                        androidx.compose.material3.Text(
                            text = "Submit",
                            fontSize = 15.sp,
                            modifier = Modifier.padding(horizontal = 5.dp),
                            color = Color.Gray
                        )
                    }
                }
            }

        })
}