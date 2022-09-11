package edu.stanford.cardinalkit.presentation.profile.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun UserCard(
    userID: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp)
            .clickable { },
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface)
    ) {
        Column() {
            Text(
                text = userID,
                fontSize = 15.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}
@Preview
@Composable
fun PreviewUserCard() {
    UserCard("")
}
