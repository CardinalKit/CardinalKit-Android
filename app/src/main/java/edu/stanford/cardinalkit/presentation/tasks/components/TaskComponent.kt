package edu.stanford.cardinalkit.presentation.home.components

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import edu.stanford.cardinalkit.R
import edu.stanford.cardinalkit.common.Constants
import edu.stanford.cardinalkit.domain.models.Response
import edu.stanford.cardinalkit.presentation.common.ProgressIndicator
import edu.stanford.cardinalkit.presentation.tasks.TasksViewModel
import edu.stanford.cardinalkit.presentation.tasks.components.TaskCard
import java.time.LocalDate
import java.util.*

@Preview
@Composable
fun TaskComponent(
    viewModel: TasksViewModel = hiltViewModel()
)
{
    val context = LocalContext.current

    when(val tasksResponse = viewModel.tasksState.value) {
        is Response.Error -> {
            Log.d(Constants.TAG, tasksResponse.e?.message.toString())
            Toast.makeText(context, tasksResponse.e?.message, Toast.LENGTH_SHORT).show()
        }
        is Response.Loading -> ProgressIndicator()
        is Response.Success ->
            LazyColumn(
            ) {
                if (tasksResponse.data != null) {
                    items(
                        items = tasksResponse.data
                    ) { task ->
                        if (task.schedule.isScheduledOn(viewModel.currentDate.value)) {
                            TaskCard(
                                id = task.id,
                                title = task.title,
                                description = task.description,
                                category = task.context.category,
                                uri = task.context.uri
                            )
                        }
                    }
                }
            }
    }
}