package edu.stanford.cardinalkit.presentation.profile.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserCard(
    fullName: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp)
            .clickable { },
        colors = CardDefaults.cardColors(Color(0xFFF5F5F5))
    ) {
        Column()
        {
            Text(
                text = fullName,
                fontSize = 15.sp
            )
        }
    }
}
@Preview
@Composable
fun PreviewUserCard(){
    UserCard("")
}