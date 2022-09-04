package edu.stanford.cardinalkit.presentation.onboarding

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.accompanist.pager.*
import edu.stanford.cardinalkit.R
import edu.stanford.cardinalkit.presentation.navigation.Screens
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@ExperimentalAnimationApi
@ExperimentalPagerApi
@Composable
fun OnboardingScreen(
    navController: NavHostController,
) {
    val pages = listOf(
        OnboardingPage.First,
        OnboardingPage.Second,
        OnboardingPage.Third,
        OnboardingPage.Fourth
    )

    val pagerState = rememberPagerState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigate(Screens.WelcomeScreen.route)
                    }) {
                        Icon(Icons.Filled.ArrowBack, "back Icon")
                    }
                },
                backgroundColor = MaterialTheme.colorScheme.surfaceVariant,
                contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                elevation = 0.dp
            )
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                HorizontalPager(
                    modifier = Modifier.weight(9f),
                    count = 4,
                    state = pagerState,
                    verticalAlignment = Alignment.Top
                ) { position ->
                    PagerScreen(onboardingPage = pages[position])
                }
                HorizontalPagerIndicator(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .weight(2f),
                    pagerState = pagerState
                )
                Box(
                    modifier = Modifier
                        .fillMaxHeight(0.13f)
                ) {
                    BottomSection(
                        pagerState = pagerState
                    )
                    ReviewButton(
                        modifier = Modifier,
                        pagerState = pagerState
                    ) {
                        navController.navigate(Screens.Review.route)
                    }
                }
            }
        }
    )
}


@Composable
fun PagerScreen(onboardingPage: OnboardingPage) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        onboardingPage.image?.let { painterResource(id = it) }?.let {
            Image(
                modifier = Modifier
                    .fillMaxWidth(0.4f)
                    .fillMaxHeight(0.67f),
                painter = it,
                contentDescription = "Pager Image"
            )
        }
        onboardingPage.title?.let { title ->
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(title),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.displaySmall
            )
        }
        onboardingPage.description?.let { description ->
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 40.dp)
                    .padding(top = 10.dp),
                text = stringResource(description),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@OptIn(ExperimentalPagerApi::class, ExperimentalAnimationApi::class)
@Composable
fun BottomSection(
    pagerState: PagerState,
) {
    val scope = rememberCoroutineScope()
    Row(
        modifier = Modifier
            .padding(bottom = 20.dp)
            .padding(horizontal = 20.dp)
            .fillMaxWidth(),
        horizontalArrangement = if (pagerState.currentPage == 3) Arrangement.Center else Arrangement.SpaceBetween
    ) {
        if (pagerState.currentPage != 3) {
            PreviousButton(
                modifier = Modifier.padding(start = 40.dp),
                onClick = {
                    scope.launch {
                        if (pagerState.currentPage - 1 == -1) {
                            pagerState.scrollToPage(pagerState.currentPage)
                        } else {
                            pagerState.scrollToPage(pagerState.currentPage - 1)
                        }
                    }
                })
            NextButton(modifier = Modifier.padding(end = 40.dp),
                onClick = {
                    scope.launch {
                        pagerState.scrollToPage(pagerState.currentPage + 1)
                    }
                })
        }
    }
}


@ExperimentalAnimationApi
@ExperimentalPagerApi
@Composable
fun PreviousButton(
    modifier: Modifier,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Center,
    ) {

        TextButton(
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(
                contentColor = MaterialTheme.colorScheme.onPrimary,
                containerColor = MaterialTheme.colorScheme.primary
            ),

            ) {
            Text(
                text = stringResource(R.string.previous),
                fontSize = 16.sp,
                fontWeight = FontWeight.Light
            )
        }
    }
}

@ExperimentalAnimationApi
@ExperimentalPagerApi
@Composable
fun NextButton(
    modifier: Modifier,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Center,
    ) {

        TextButton(
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(
                contentColor = MaterialTheme.colorScheme.onPrimary,
                containerColor = MaterialTheme.colorScheme.primary
            ),

            ) {
            Text(
                text = stringResource(R.string.next),
                fontSize = 16.sp,
                fontWeight = FontWeight.Light
            )

        }
    }
}

@ExperimentalAnimationApi
@ExperimentalPagerApi
@Composable
fun ReviewButton(
    modifier: Modifier,
    pagerState: PagerState,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier.padding(horizontal = 50.dp),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Center,
    ) {
        AnimatedVisibility(
            modifier = Modifier.fillMaxWidth(),
            visible = pagerState.currentPage == 3
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
                    text = "Review",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(vertical = 6.dp, horizontal = 10.dp)
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun FirstOnBoardingScreenPreview() {
    Column(modifier = Modifier.fillMaxSize()) {
        PagerScreen(onboardingPage = OnboardingPage.First)
    }
}

@Composable
@Preview(showBackground = true)
fun SecondOnBoardingScreenPreview() {
    Column(modifier = Modifier.fillMaxSize()) {
        PagerScreen(onboardingPage = OnboardingPage.Second)
    }
}

@Composable
@Preview(showBackground = true)
fun ThirdOnBoardingScreenPreview() {
    Column(modifier = Modifier.fillMaxSize()) {
        PagerScreen(onboardingPage = OnboardingPage.Third)
    }
}

@Composable
@Preview(showBackground = true)
fun FourthOnBoardingScreenPreview() {
    Column(modifier = Modifier.fillMaxSize()) {
        PagerScreen(onboardingPage = OnboardingPage.Fourth)
    }
}