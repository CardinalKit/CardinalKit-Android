package edu.stanford.cardinalkit.presentation.tasks

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.TopAppBar
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import edu.stanford.cardinalkit.R
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.stanford.cardinalkit.presentation.home.components.TaskComponent
import edu.stanford.cardinalkit.presentation.tasks.components.DatePickerTimeline
import edu.stanford.cardinalkit.ui.theme.PrimaryTheme
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
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
        content = {
            Column(modifier = Modifier.padding(top = 55.dp)){
                DatePickerTimeline(
                    modifier = Modifier,
                    onDateSelected = {selectedDate: LocalDate->
                        viewModel.setDate(selectedDate)
                    },
                    selectedBackgroundColor = Color.LightGray,
                    todayLabel = {
                        Text(
                            modifier = Modifier.padding(10.dp),
                            text = "Today",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 15.sp,
                            color = PrimaryTheme
                        )
                    },
                )
                Box(modifier= Modifier
                    .padding(horizontal = 20.dp)
                    .padding(bottom = 10.dp)
                    .padding(top=15.dp)){
                    Text(
                        text= "To Do",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                Box(modifier= Modifier
                    .padding(horizontal = 20.dp)
                    .padding(top=10.dp)) {
                    TaskComponent()
                }
            }
        }
    )
}

