package edu.stanford.cardinalkit.presentation.home.components

import android.graphics.Paint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap.Companion.Square
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat

@Preview
@Composable

fun CalendarCard(){
    val day= SimpleDateFormat("dd")
    val month=SimpleDateFormat("MMMM")
    val currentDay: String = day.format(java.util.Date())
    val currentMonth: String = month.format(java.util.Date())
    Card(
        modifier = Modifier
            .width(166.dp)
            .height(166.dp)
            .clickable { },
        backgroundColor = Color.LightGray,
        shape= RoundedCornerShape(18),
    ){
        Column(
            modifier = Modifier
                .padding(15.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center

        ) {
            Text(
                text= currentMonth,
                fontSize = 18.sp
            )
            Text(
                text= currentDay,
                fontSize = 65.sp
            )

        }

    }
}