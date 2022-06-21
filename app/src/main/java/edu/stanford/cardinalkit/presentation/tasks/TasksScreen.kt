package edu.stanford.cardinalkit.presentation.tasks

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.TopAppBar
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import edu.stanford.cardinalkit.R
import edu.stanford.cardinalkit.presentation.SurveyActivity
import edu.stanford.cardinalkit.presentation.tasks.components.TaskItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TasksScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.tasks_screen_title),
                        style = MaterialTheme.typography.headlineSmall,
                        color = Color.White
                    )
                }
            )
        },
        content = { contentPadding ->
            val mContext = LocalContext.current

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(contentPadding),
                verticalArrangement = Arrangement.SpaceAround,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Button(
                    modifier = Modifier.padding(20.dp),
                    onClick = {
                        mContext.startActivity(Intent(mContext, SurveyActivity::class.java))
                }){
                    Text("Open FHIR Survey")
                }
            }
        }
    )
}