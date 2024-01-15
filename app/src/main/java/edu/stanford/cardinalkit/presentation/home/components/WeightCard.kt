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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import edu.stanford.cardinalkit.R
import edu.stanford.cardinalkit.presentation.health.HealthViewModel
import kotlin.math.roundToInt

@Composable
fun WeightCard(
    viewModel: HealthViewModel = hiltViewModel(),
    useMetricUnits: Boolean = true
) {
    viewModel.getWeeklyAverageWeight()

    var avgWeightString = "-"
    val avgWeight = viewModel.weeklyAverageWeight.value

    val weightInSelectedUnit = if (useMetricUnits) {
        avgWeight?.inKilograms?.roundToInt()
    } else {
        avgWeight?.inPounds?.roundToInt()
    }

    avgWeightString = weightInSelectedUnit?.let {
        "$it ${if (useMetricUnits) "kg" else "lbs"}"
    } ?: "-"

    Card(
        modifier = Modifier
            .width(165.dp)
            .height(165.dp)
            .clickable { },
        backgroundColor = MaterialTheme.colorScheme.secondary,
        shape = RoundedCornerShape(18)
    ) {
        Column(
            modifier = Modifier
                .padding(15.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly

        ) {
            Text(
                text = stringResource(R.string.weight_card_header),
                fontSize = 25.sp,
                color = MaterialTheme.colorScheme.onSecondary
            )
            Text(
                text = avgWeightString,
                fontSize = 40.sp,
                color = MaterialTheme.colorScheme.onSecondary
            )
            Text(
                text = stringResource(R.string.weight_card_7_day_average),
                fontSize = 15.sp,
                color = MaterialTheme.colorScheme.onSecondary
            )
        }
    }
}
