package edu.stanford.cardinalkit.presentation.tasks

import androidx.compose.foundation.layout.*
import androidx.compose.material.TopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import edu.stanford.cardinalkit.R
import edu.stanford.cardinalkit.presentation.home.components.TaskComponent
import edu.stanford.cardinalkit.presentation.tasks.components.DatePickerTimeline
import edu.stanford.cardinalkit.presentation.tasks.components.rememberDatePickerState
import edu.stanford.cardinalkit.ui.theme.PrimaryTheme
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun TasksScreen(
    viewModel: TasksViewModel = hiltViewModel()
) {
    val datePickerState = rememberDatePickerState(initialDate = LocalDate.now())

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row {
                        Text(
                            text = stringResource(R.string.tasks_screen_title),
                            modifier = Modifier.padding(5.dp),
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Light
                        )

                    }
                },
                backgroundColor = Color(0xFFF1F1F1),
                contentColor = Color.Black)

        },
        containerColor =  Color(0xFFF5F5F5),
        content = { padding ->
            Column(modifier = Modifier.padding(padding)){
                DatePickerTimeline(
                    modifier = Modifier,
                    onDateSelected = {selectedDate: LocalDate->
                        viewModel.setDate(selectedDate)
                    },
                    selectedBackgroundColor = Color.LightGray,
                    state = datePickerState
                )
                Row(modifier= Modifier
                    .padding(horizontal = 20.dp)
                    .padding(top = 15.dp),
                verticalAlignment = Alignment.CenterVertically){
                    Text(
                        text= stringResource(R.string.todo),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(Modifier.weight(1f))
                    OutlinedButton(
                        onClick = {
                            val today = LocalDate.now()
                            datePickerState.smoothScrollToDate(today)
                            viewModel.setDate(today)
                        },
                    ) {
                        Text("Today", color = PrimaryTheme)
                    }
                }
                Box(modifier= Modifier
                    .padding(horizontal = 20.dp)
                    .padding(top = 5.dp)) {
                    TaskComponent()
                }
            }
        }
    )
}

