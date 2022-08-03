package edu.stanford.cardinalkit.presentation.home


import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import edu.stanford.cardinalkit.R
import edu.stanford.cardinalkit.presentation.home.components.*
import edu.stanford.cardinalkit.presentation.navigation.Screens


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.home_screen_title),
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

            Column(modifier= Modifier
                .padding(top = 50.dp)
                .padding(all = 27.dp)
                .verticalScroll(rememberScrollState())){
                Text(
                    text = stringResource(R.string.welcome),
                    fontSize = 22.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(bottom=3.dp)
                )
                Text(
                    text = stringResource(R.string.welcome_message),
                    fontSize = 15.sp,
                    modifier=Modifier.padding(bottom=10.dp)
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement= Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.task_summary),
                        fontWeight= FontWeight.SemiBold,
                        fontSize = 18.sp
                    )
                    IconButton(onClick={
                        navController.navigate(Screens.TasksScreen.route)
                    }){
                        Icon(Icons.Filled.ArrowForward, "forward Icon")
                    }
                }
                TaskCardUI()
                Spacer(modifier = Modifier.height(20.dp))
                Column(){
                    LearnMoreCard()
                    Spacer(modifier = Modifier.height(20.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement= Arrangement.SpaceBetween
                    ) {
                        CKSurveyCard()
                        CalendarCard()
                    }
                    Spacer(modifier = Modifier.height(70.dp))
                }
            }
        }
    )

}