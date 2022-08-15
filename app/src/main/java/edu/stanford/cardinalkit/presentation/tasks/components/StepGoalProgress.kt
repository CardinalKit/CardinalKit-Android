package edu.stanford.cardinalkit.presentation.tasks.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import edu.stanford.cardinalkit.presentation.health.HealthViewModel
import edu.stanford.cardinalkit.ui.theme.PrimaryTheme

@Composable
fun StepGoalProgress(
    healthViewModel: HealthViewModel = hiltViewModel()
){
    val totalStepsToday = healthViewModel.totalStepsToday.value.toString()
    val stepsGoal = 10000
    val progress = healthViewModel.totalStepsToday.value.toFloat() / stepsGoal
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
    ) {
        Text(
            text = "$totalStepsToday",
            fontSize = 30.sp,
            color = PrimaryTheme
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = "/ $stepsGoal",
            fontSize = 30.sp,
            color = Color.DarkGray
        )
    }
    LinearProgressIndicator(
        progress = progress,
        modifier = Modifier.fillMaxWidth(),
        color = PrimaryTheme
    )
}