package edu.stanford.cardinalkit.presentation

import android.widget.EditText
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.google.android.material.textfield.TextInputEditText
import edu.stanford.cardinalkit.presentation.navigation.Screens


@Composable
fun RegisterScreen(
    navController: NavHostController
) {
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
                    text = "Stanford Department of Medicine",
                    modifier = Modifier.padding(bottom = 30.dp),
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
                    label={Text(text="First Name")},
                    singleLine = true
                )
                OutlinedTextField(
                    value = text,
                    onValueChange ={ newText->
                        text = newText
                    },
                    label={Text(text="Last Name")},
                    singleLine = true
                )
                OutlinedTextField(
                    value = text,
                    onValueChange ={ newText->
                        text = newText
                    },
                    label={Text(text="Email")},
                    singleLine = true
                )
                OutlinedTextField(
                    value = text,
                    onValueChange ={ newText->
                        text = newText
                    },
                    label={Text(text="Password")},
                    singleLine = true
                )
                OutlinedTextField(
                    value = text,
                    onValueChange ={ newText->
                        text = newText
                    },
                    label={Text(text="Confirm Password")},
                    singleLine = true
                )

                Row(
                    Modifier.padding(top=25.dp),
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.Center,
                )  {
                    Button(
                        onClick = {},
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
