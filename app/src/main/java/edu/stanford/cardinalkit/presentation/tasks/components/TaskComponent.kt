package edu.stanford.cardinalkit.presentation.home.components

import android.util.Log
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import edu.stanford.cardinalkit.domain.models.Response
import edu.stanford.cardinalkit.presentation.common.ProgressIndicator
import edu.stanford.cardinalkit.presentation.tasks.TasksViewModel
import edu.stanford.cardinalkit.presentation.tasks.components.TaskCard

@Preview
@Composable
fun TaskComponent(
    viewModel: TasksViewModel = hiltViewModel()
)
{
    when(val tasksResponse = viewModel.tasksState.value) {
        is Response.Error -> Log.d("Tasks", tasksResponse.e?.message.toString())
        is Response.Loading -> ProgressIndicator()
        is Response.Success ->
            LazyColumn(
            ) {
                if (tasksResponse.data != null) {
                    items(
                        items = tasksResponse.data
                    ) { task ->
                        TaskCard(
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