package edu.stanford.cardinalkit.presentation.home.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable

fun TextCard() {
    Card(
        onClick = {},
        modifier = Modifier
            .width(165.dp)
            .height(165.dp),
        backgroundColor = MaterialTheme.colorScheme.secondary,
        shape = RoundedCornerShape(18),
    ) {
        Column(
            modifier = Modifier
                .padding(15.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center
        ) {
            Row() {
                Text(
                    text = "Edit Card",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSecondary
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onSecondary
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                "See More",
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onSecondary
            )
        }
    }
}
