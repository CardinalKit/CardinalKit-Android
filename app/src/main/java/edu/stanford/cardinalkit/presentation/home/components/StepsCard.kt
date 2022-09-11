package edu.stanford.cardinalkit.presentation.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import edu.stanford.cardinalkit.presentation.health.HealthViewModel

@Composable
fun StepsCard(
    viewModel: HealthViewModel = hiltViewModel()
) {
    viewModel.getTotalStepsToday()
    Card(
        modifier = Modifier
            .width(165.dp)
            .height(165.dp)
            .clickable { },
        backgroundColor = MaterialTheme.colorScheme.secondary,
        shape = RoundedCornerShape(18),
    ) {
        Column(
            modifier = Modifier
                .padding(15.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly

        ) {
            Text(
                text = "Steps",
                fontSize = 25.sp,
                color = MaterialTheme.colorScheme.onSecondary
            )
            Text(
                text = viewModel.totalStepsToday.value.toString(),
                fontSize = 40.sp,
                color = MaterialTheme.colorScheme.onSecondary
            )
            Text(
                text = "Total Today",
                fontSize = 15.sp,
                color = MaterialTheme.colorScheme.onSecondary
            )
        }
    }
}
