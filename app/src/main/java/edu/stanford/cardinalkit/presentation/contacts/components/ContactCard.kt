package edu.stanford.cardinalkit.presentation.contacts.components

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.core.os.bundleOf
import edu.stanford.cardinalkit.R
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
        colors = cardColors(MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(
            modifier = Modifier
                .padding(15.dp)
                .fillMaxWidth()
        ) {
            Row {
                Icon(
                    imageVector = Icons.Filled.AccountCircle,
                    modifier = Modifier
                        .padding(end = 10.dp)
                        .height(50.dp)
                        .width(45.dp),
                    contentDescription = "profile",
                    tint = MaterialTheme.colorScheme.primary
                )
                Column {
                    Text(
                        text = "${contact.title} ${contact.firstName} ${contact.lastName}",
                        fontSize = 22.sp
                    )
                    Text(
                        text = contact.role,
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
                    smsNumber = contact.smsNumber,
                    email = contact.email
                )
            }
            val context = LocalContext.current
            OutlinedCard(
                Modifier
                    .clickable {
                        openMaps(
                            context = context,
                            contact = contact
                        )
                    }
                    .fillMaxWidth()
                    .padding(10.dp),
                colors = cardColors(MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(
                    modifier = Modifier
                        .padding(15.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(R.string.location),
                        fontSize = 18.sp
                    )
                    Text(
                        text = contact.streetAddress,
                        fontSize = 15.sp
                    )
                    Text(
                        text = "${contact.city}, ${contact.state}, ${contact.country}, ${contact.postalCode} ",
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
    smsNumber: String,
    email: String
) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        val context = LocalContext.current
        OutlinedButton(
            onClick = { makeACall(context = context, phoneNumber = phoneNumber) },
            modifier = Modifier.padding(5.dp)
        ) {
            Text(
                text = stringResource(R.string.call),
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .padding(vertical = 5.dp)
            )
        }
        OutlinedButton(
            onClick = {
                sendAText(context = context, smsNumber = smsNumber)
            },
            modifier = Modifier.padding(5.dp)
        ) {
            Text(
                text = stringResource(R.string.sms),
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .padding(vertical = 5.dp)
            )
        }
        OutlinedButton(
            onClick = { sendEmail(context = context, recipientMail = email) },
            modifier = Modifier.padding(5.dp)
        ) {
            Text(
                text = stringResource(R.string.email),
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .padding(vertical = 5.dp)
            )
        }
    }
}

fun makeACall(context: Context, phoneNumber: String) {
    if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.CALL_PHONE) ==
        PackageManager.PERMISSION_GRANTED
    ) {
        val intent = Intent(Intent.ACTION_CALL)
        intent.data = Uri.parse("tel: $phoneNumber")
        startActivity(context, intent, bundleOf())
    } else {
        ActivityCompat.requestPermissions(
            context as Activity,
            arrayOf(android.Manifest.permission.CALL_PHONE),
            777
        )
    }
}

fun sendAText(context: Context, smsNumber: String) {
    if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.SEND_SMS) ==
        PackageManager.PERMISSION_GRANTED
    ) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse("sms: $smsNumber")
        startActivity(context, intent, bundleOf())
    } else {
        ActivityCompat.requestPermissions(
            context as Activity,
            arrayOf(android.Manifest.permission.SEND_SMS),
            777
        )
    }
}

fun sendEmail(context: Context, recipientMail: String) {
    val emailIntent = Intent(
        Intent.ACTION_SENDTO,
        Uri.fromParts(
            "mailto",
            recipientMail,
            null
        )
    )
    startActivity(context, Intent.createChooser(emailIntent, "Choose an email client:"), bundleOf())
}

fun openMaps(context: Context, contact: Contact) {
    val gmmIntentUri =
        Uri.parse("geo:0,0?q=${contact.streetAddress}, ${contact.city}, ${contact.state}, ${contact.country}")
    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
    mapIntent.setPackage("com.google.android.apps.maps")
    startActivity(context, mapIntent, bundleOf())
}
