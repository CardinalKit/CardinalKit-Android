package edu.stanford.cardinalkit.presentation.contacts.components

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.*
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.core.os.bundleOf
import edu.stanford.cardinalkit.domain.models.Contact


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactCard(
    contact: Contact
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
                        text = contact.name,
                        fontSize = 22.sp
                    )
                    Text(
                        text = contact.title,
                        fontSize = 15.sp
                    )
                }
            }
            Text(
                text = contact.description,
                modifier = Modifier
                    .padding(vertical = 20.dp)
                    .padding(horizontal = 10.dp)
            )
            Box(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                ContactSection(
                    phoneNumber = contact.phoneNumber,
                    textNumber = contact.textNumber,
                    email = contact.email
                )
            }
            val context = LocalContext.current
            OutlinedCard(
                Modifier
                    .clickable { openMaps(context=context)}
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
                        text = contact.addressLineOne,
                        fontSize = 15.sp
                    )
                    Text(
                        text = contact.addressLineTwo,
                        fontSize = 15.sp
                    )
                }
            }
        }
    }
}

@Composable
fun ContactSection(
    phoneNumber: String,
    textNumber: String,
    email: String
){
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){
        val context = LocalContext.current
        OutlinedButton(
            onClick = { makeACall(context = context, phoneNumber = phoneNumber) },
            modifier= Modifier.padding(5.dp)
        ) {
            Text(
                text="Call",
                fontSize = 15.sp,
                color = Color(0xFF484965),
                modifier = Modifier
                    .padding(horizontal = 15.dp)
                    .padding(vertical = 5.dp)
            )
        }
        OutlinedButton(
            onClick = {
                sendAText(context = context, phoneNumber = textNumber)
            },
            modifier= Modifier.padding(5.dp)
        ) {
            Text(
                text="Text",
                fontSize = 15.sp,
                color = Color(0xFF484965),
                modifier = Modifier
                    .padding(horizontal = 13.dp)
                    .padding(vertical = 5.dp)
            )
        }
        OutlinedButton(onClick = { sendEmail(context = context, recipientMail = email) },
            modifier= Modifier.padding(5.dp)
        ) {
            Text(
                text="Email",
                fontSize = 15.sp,
                color = Color(0xFF484965),
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .padding(vertical = 5.dp)
            )
        }
    }
}

fun makeACall(context: Context, phoneNumber: String) {
    if (ContextCompat.checkSelfPermission(context,android.Manifest.permission.CALL_PHONE ) ==
        PackageManager.PERMISSION_GRANTED
    ) {
        val intent = Intent(Intent.ACTION_CALL)
        intent.data = Uri.parse("tel: $phoneNumber")
        startActivity(context, intent, bundleOf())
    } else {
        ActivityCompat.requestPermissions(
            context as Activity, arrayOf(android.Manifest.permission.CALL_PHONE), 777
        )
    }
}

fun sendAText(context: Context, phoneNumber: String) {
    if (ContextCompat.checkSelfPermission(context,android.Manifest.permission.SEND_SMS ) ==
        PackageManager.PERMISSION_GRANTED
    ) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse("sms: $phoneNumber")
        startActivity(context, intent, bundleOf())
    } else {
        ActivityCompat.requestPermissions(
            context as Activity, arrayOf(android.Manifest.permission.SEND_SMS), 777
        )
    }
}

fun sendEmail(context: Context, recipientMail:String){
    val emailIntent = Intent(Intent.ACTION_SEND)
    emailIntent.data= Uri.parse("mailto:$recipientMail")
    emailIntent.type = "text/plain"
    emailIntent.putExtra(Intent.EXTRA_EMAIL, recipientMail)
    startActivity(context,emailIntent, bundleOf())
}

fun openMaps(context: Context){
    val gmmIntentUri = Uri.parse("geo:0,0?q=")
    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
    mapIntent.setPackage("com.google.android.apps.maps")
    startActivity(context, mapIntent,bundleOf())
}
