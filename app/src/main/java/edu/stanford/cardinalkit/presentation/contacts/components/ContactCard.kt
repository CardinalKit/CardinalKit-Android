package edu.stanford.cardinalkit.presentation.contacts.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.material.icons.filled.PersonPinCircle
import androidx.compose.material3.*
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.stanford.cardinalkit.presentation.contacts.ContactsScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactCard(
    name: String,
    title: String,
    description: String,
    phone: String,
    email: String,
    addressLineOne: String,
    addressLineTwo: String,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
            .clickable { },
        colors = cardColors(Color.White)
    ) {
        Column(
            modifier = Modifier
                .padding(15.dp)
                .fillMaxWidth()
        ) {
            Row(){
                Icon(imageVector = Icons.Filled.AccountCircle , modifier = Modifier
                    .padding(end = 10.dp)
                    .height(50.dp)
                    .width(45.dp), contentDescription = "profile", tint = Color(0xFF484965))
                Column() {
                    Text(
                        text = name,
                        fontSize = 22.sp
                    )
                    Text(
                        text = title,
                        fontSize = 15.sp
                    )

                }

            }
            Text(
                text=description,
                modifier = Modifier.padding(vertical = 20.dp).padding(horizontal = 10.dp)
            )
            Box(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                ContactSection()

            }

            OutlinedCard(
                Modifier
                    .clickable { }
                    .fillMaxWidth()
                    .padding(10.dp),
                colors = cardColors(Color.White)) {
                Column(
                    modifier = Modifier
                        .padding(15.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Location",
                        fontSize = 18.sp
                    )
                    Text(
                        text = addressLineOne,
                        fontSize = 15.sp
                    )
                    Text(
                        text = addressLineTwo,
                        fontSize = 15.sp
                    )

                }

            }
        }
    }
}

@Composable
fun ContactSection(){
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center){
        OutlinedButton(
            onClick = { /*TODO*/ },
            modifier= Modifier.padding(5.dp)
        ) {
            Text(
                text="Call",
                fontSize = 15.sp,
                color = Color(0xFF484965)
            )

        }
        OutlinedButton(
            onClick = { /*TODO*/ },
            modifier= Modifier.padding(5.dp)
        ) {
            Text(
                text="Message",
                fontSize = 15.sp,
                color = Color(0xFF484965)
            )

        }
        OutlinedButton(onClick = { /*TODO*/ },
            modifier= Modifier.padding(5.dp)
        ) {
            Text(
                text="Email",
                fontSize = 15.sp,
                color = Color(0xFF484965)
            )

        }

    }
}