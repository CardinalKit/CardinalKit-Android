package edu.stanford.cardinalkit.presentation.Onboarding

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import edu.stanford.cardinalkit.common.Config
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import edu.stanford.cardinalkit.R
import edu.stanford.cardinalkit.presentation.navigation.Screens
import se.warting.signaturepad.SignaturePadAdapter
import se.warting.signaturepad.SignaturePadView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Signature(
    navController: NavHostController
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigate(Screens.Review.route)
                    }) {
                        Icon(Icons.Filled.ArrowBack, "back Icon")
                    }
                },
                backgroundColor = Color.White,
                contentColor = Color.Black,
                elevation = 0.dp
            )
        },
        content = {
            var signaturePadAdapter: SignaturePadAdapter? = null
            var signatureSvg by remember {
                mutableStateOf("")
            }
            val penColor by remember {
                mutableStateOf(Color.Black)
            }
            var firstName by remember {
                mutableStateOf("")
            }
            var lastName by remember {
                mutableStateOf("")
            }
            var signed by remember {
                mutableStateOf(false)
            }

            Column(
                modifier = Modifier
                    .fillMaxHeight(0.88f)
                    .padding(horizontal = 20.dp)
                    .padding(top = 10.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Column(
                    modifier = Modifier
                        .padding(top = 10.dp)
                ) {
                    Text(
                        text = "Please enter your first and last name.",
                        fontSize = 18.sp
                    )
                    OutlinedTextField(
                        value = firstName,
                        onValueChange = { newText ->
                            firstName = newText
                        },
                        label = {
                            Text(
                                text = stringResource(R.string.first_name),
                            )
                        },
                        singleLine = true,
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Color.Gray,
                            cursorColor = Color.LightGray
                        ),
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
                            focusedBorderColor = Color.Gray,
                            cursorColor = Color.LightGray
                        ),
                    )
                }

                Column(
                    modifier = Modifier.padding(top = 10.dp)
                ) {
                    Text(
                        text = "Please sign using your finger in the box below.",
                        fontSize = 18.sp
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Box(
                        modifier = Modifier
                            .height(200.dp)
                            .fillMaxWidth()
                            .border(
                                width = 2.dp,
                                color = Color.LightGray,
                            )
                    ) {
                        SignaturePadView(
                            onReady = {
                                signaturePadAdapter = it
                            },
                            penColor = penColor,
                            onSigned = {
                                signed = true
                            })
                    }

                    Column {
                        Button(
                            enabled = signed,
                            onClick = {
                                signatureSvg = signaturePadAdapter?.getSignatureSvg() ?: ""
                            },
                            shape = RoundedCornerShape(50),
                            colors = ButtonDefaults.buttonColors(
                                contentColor = Color.Gray,
                                backgroundColor = Color.LightGray
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 10.dp)
                        ) {
                            androidx.compose.material.Text(
                                text = "Next",
                                fontSize = 16.sp,
                                modifier = Modifier.padding(vertical = 8.dp, horizontal = 30.dp)
                            )
                        }

                        Button(
                            onClick = {
                                signatureSvg = ""
                                signaturePadAdapter?.clear()
                                firstName = ""
                                lastName = ""
                            },
                            shape = RoundedCornerShape(50),
                            colors = ButtonDefaults.buttonColors(
                                contentColor = Color.Black,
                                backgroundColor = Color.White
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 10.dp)
                        ) {
                            androidx.compose.material.Text(
                                text = "Clear",
                                fontSize = 16.sp,
                                modifier = Modifier.padding(vertical = 8.dp, horizontal = 30.dp)
                            )
                        }
                    }
                }
            }
        }
    )
}