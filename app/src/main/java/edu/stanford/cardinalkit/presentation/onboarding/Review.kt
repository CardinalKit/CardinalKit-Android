package edu.stanford.cardinalkit.presentation.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import edu.stanford.cardinalkit.R
import edu.stanford.cardinalkit.presentation.navigation.Screens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Review(
    navController: NavHostController
) {
    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigate(Screens.OnboardingScreen.route)
                    }) {
                        Icon(Icons.Filled.ArrowBack, "back arrow")
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors()
            )
        },
        content = { padding ->
            Column {
                Column(
                    modifier = Modifier
                        .fillMaxHeight(0.88f)
                        .padding(horizontal = 20.dp)
                        .padding(padding)
                ) {
                    Text(
                        text = stringResource(R.string.review_screen_title),
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Light
                    )
                    DisplayList(items = data)
                }
                Column(
                    modifier = Modifier
                        .padding(top = 6.dp),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Row(
                        modifier = Modifier.padding(10.dp)
                    ) {
                        RespondButton(
                            label = stringResource(R.string.disagree),
                            modifier = Modifier
                        ) {
                            navController.navigate(Screens.JoinStudyScreen.route)
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        RespondButton(
                            label = stringResource(R.string.agree),
                            modifier = Modifier
                        ) {
                            navController.navigate(Screens.SignatureScreen.route)
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun ReviewScreen(onboardingPage: OnboardingPage) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp)
    ) {
        onboardingPage.title?.let { title ->
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = stringResource(title),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Left
            )
        }
        onboardingPage.description?.let { description ->
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = stringResource(description),
                fontSize = 16.sp,
                textAlign = TextAlign.Left
            )
        }
    }
}

@Composable
fun RespondButton(
    label: String,
    modifier: Modifier,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .padding(horizontal = 5.dp),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = onClick,
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(
                contentColor = MaterialTheme.colorScheme.onPrimary,
                containerColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Text(
                text = label,
                fontSize = 16.sp,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
    }
}

val data = listOf(
    OnboardingPage.First,
    OnboardingPage.Second,
    OnboardingPage.Third,
    OnboardingPage.Fourth
)

@Composable
fun DisplayList(items: List<OnboardingPage>) {
    val listState = rememberLazyListState()
    LazyColumn(
        modifier = Modifier,
        state = listState
    ) {
        items(items) { item ->
            ReviewScreen(onboardingPage = item)
        }
    }
}
