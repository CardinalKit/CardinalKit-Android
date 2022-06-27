package edu.stanford.cardinalkit.presentation.contacts.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactCard(
    name: String,
    title: String,
    phone: String,
    email: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
            .clickable { }
    ) {
        Column(
            modifier = Modifier.padding(15.dp)
        ) {
            Text(
                text = name,
                fontSize = 34.sp
            )
            Text(
                text = email,
                fontSize = 18.sp
            )
            Text(
                text = title,
                fontSize = 18.sp
            )
            Text(
                text = phone,
                fontSize = 18.sp
            )
        }
    }
}