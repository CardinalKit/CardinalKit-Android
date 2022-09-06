package edu.stanford.cardinalkit.presentation.home.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.bundleOf


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun LearnMoreCard(){
    val context = LocalContext.current
    Card(
        onClick = {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("https://cardinalkit.sites.stanford.edu/")
            }
            context.startActivity(intent, bundleOf())
        },
        modifier = Modifier
            .fillMaxWidth()
            .clickable { },
        backgroundColor = MaterialTheme.colorScheme.primaryContainer,
        shape = RoundedCornerShape(18)
    ){
        Column(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth()
        ) {
            Text(
                text= "Customize Your App",
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                fontWeight= FontWeight.SemiBold,
                modifier = Modifier.padding(bottom = 5.dp),
                fontSize = 20.sp
            )
            Text(
                text= "This is a template app that you can customize to create your own digital health experience.",
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Spacer(modifier = Modifier.height(40.dp))
            Text(
                text= "Learn how at cardinalkit.org",
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}