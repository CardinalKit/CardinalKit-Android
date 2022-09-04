package edu.stanford.cardinalkit.presentation.onboarding

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
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
            SmallTopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigate(Screens.Review.route)
                    }) {
                        Icon(Icons.Filled.ArrowBack, "back Icon")
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors()
            )
        },
        content = { padding ->
            var signaturePadAdapter: SignaturePadAdapter? = null
            var signatureSvg by remember {
                mutableStateOf("")
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
                    .padding(padding),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(R.string.signature_screen_title),
                    fontSize = 25.sp
                )
                Column(
                    modifier = Modifier
                        .padding(top = 10.dp)
                ) {
                    Text(
                        text = stringResource(R.string.signature_instructions),
                        fontSize = 18.sp
                    )
                    Spacer(modifier = Modifier.height(5.dp))
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
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            cursorColor = MaterialTheme.colorScheme.primary
                        ),
                        modifier = Modifier.fillMaxWidth()
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
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Column(
                    modifier = Modifier.padding(top = 10.dp)
                ) {
                    Text(text = stringResource(R.string.signature), fontSize = 18.sp)
                    Spacer(modifier = Modifier.height(5.dp))
                    Box(
                        modifier = Modifier
                            .height(200.dp)
                            .fillMaxWidth()
                            .border(
                                width = 2.dp,
                                color = MaterialTheme.colorScheme.primary,
                            )
                    ) {
                        SignaturePadView(
                            onReady = {
                                signaturePadAdapter = it
                            },
                            onSigned = {
                                signed = true
                            })
                    }

                    Column {
                        Button(
                            enabled = signed,
                            onClick = {
                                // TODO: Generate signed PDF from signature
                                signatureSvg = signaturePadAdapter?.getSignatureSvg() ?: ""
                                navController.navigate(Screens.RegisterMethod.route)
                            },
                            shape = RoundedCornerShape(50),
                            colors = ButtonDefaults.buttonColors(
                                contentColor = MaterialTheme.colorScheme.onPrimary,
                                containerColor = MaterialTheme.colorScheme.primary
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 10.dp)
                        ) {
                            androidx.compose.material.Text(
                                text = stringResource(R.string.next),
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
                                contentColor = MaterialTheme.colorScheme.onSecondary,
                                containerColor = MaterialTheme.colorScheme.secondary
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 10.dp)
                        ) {
                            androidx.compose.material.Text(
                                text = stringResource(R.string.clear),
                                fontSize = 16.sp,
                                color = MaterialTheme.colorScheme.onSecondary,
                                modifier = Modifier.padding(vertical = 8.dp, horizontal = 30.dp)
                            )
                        }
                    }
                }
            }
        }
    )
}