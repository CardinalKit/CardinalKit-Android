package edu.stanford.cardinalkit.presentation.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview
@Composable
fun LearnMoreCard(){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { },
        backgroundColor = Color(0xFF484965),
        shape= RoundedCornerShape(18)
    ){
        Column(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth()
        ) {
            Text(
                text= "Learn About CardinalKit",
                color=Color.White,
                fontWeight= FontWeight.SemiBold,
                modifier = Modifier.padding(bottom = 5.dp),
                fontSize = 20.sp
            )
            Text(
                text= "Visit Cardinalkit.org",
                color=Color.White
            )
            Spacer(modifier = Modifier.height(40.dp))
            Text(
                text= "See More",
                color=Color.White
            )

        }

    }
}