package edu.stanford.cardinalkit.presentation.tasks.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskItem() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(25.dp)
            .clickable { }
    ){
        Text("Daily Survey")
    }
}