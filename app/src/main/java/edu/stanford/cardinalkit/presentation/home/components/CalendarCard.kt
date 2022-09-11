package edu.stanford.cardinalkit.presentation.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.*

@Preview
@Composable
fun CalendarCard() {
    val day = SimpleDateFormat("dd", Locale.getDefault())
    val month = SimpleDateFormat("MMMM", Locale.getDefault())
    val currentDay: String = day.format(java.util.Date())
    val currentMonth: String = month.format(java.util.Date())
    Card(
        modifier = Modifier
            .width(165.dp)
            .height(165.dp)
            .clickable { },
        backgroundColor = MaterialTheme.colorScheme.secondaryContainer,
        shape = RoundedCornerShape(18)
    ) {
        Column(
            modifier = Modifier
                .padding(15.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center

        ) {
            Text(
                text = currentMonth,
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
            Text(
                text = currentDay,
                fontSize = 65.sp,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
    }
}
