package edu.stanford.cardinalkit.presentation.tasks

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.TopAppBar
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import edu.stanford.cardinalkit.R
import edu.stanford.cardinalkit.common.Constants
import edu.stanford.cardinalkit.presentation.surveys.SurveyActivity
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.stanford.cardinalkit.domain.models.Response
import edu.stanford.cardinalkit.presentation.common.ProgressIndicator
import edu.stanford.cardinalkit.presentation.tasks.components.TaskCard
import java.text.SimpleDateFormat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TasksScreen(
    viewModel: TasksViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.tasks_screen_title),
                        modifier = Modifier.padding(5.dp),
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Light
                    )
                },
                backgroundColor = Color(0xFFF1F1F1),
                contentColor = Color.Black)

        },
        containerColor =  Color(0xFFF5F5F5),
        content = { contentPadding ->
            val simpleDateFormat= SimpleDateFormat("MMMM dd, yyyy")
            val currentDateAndTime: String = simpleDateFormat.format(java.util.Date())

            Column(modifier = Modifier
                .padding(horizontal = 20.dp)
                .padding(top = 70.dp)){
                Box(modifier= Modifier
                    .padding(horizontal = 5.dp)
                    .padding(bottom = 10.dp)){
                    Text(
                        text= "Today, $currentDateAndTime",
                        fontSize = 15.sp
                    )
                }
                Box(modifier= Modifier
                    .padding(horizontal = 7.dp)
                    .padding(bottom = 10.dp)){
                    Text(
                        text= "To Do",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                Box {
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

            }
        }
    )
    fun launchSurvey(surveyName: String, context: Context){
        val intent = Intent(context, SurveyActivity::class.java).apply {
            putExtra(Constants.SURVEY_NAME, surveyName)
        }
        context.startActivity(intent)
    }
}

