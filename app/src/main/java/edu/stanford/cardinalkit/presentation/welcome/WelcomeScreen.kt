package edu.stanford.cardinalkit.presentation.Onboarding

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.accompanist.pager.ExperimentalPagerApi
import edu.stanford.cardinalkit.R
import edu.stanford.cardinalkit.presentation.navigation.Screens


@ExperimentalAnimationApi
@ExperimentalPagerApi
@Composable
fun WelcomeScreen(
    navController: NavHostController,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigate(Screens.JoinStudyScreen.route)
                    }) {
                        Icon(Icons.Filled.ArrowBack, "back Icon")
                    }
                },
                backgroundColor = Color.White,
                contentColor = Color.Black,
                elevation = 0.dp
            )
        },
        content = { padding ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top=50.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Top
                    ) {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal=40.dp),
                            text = "Welcome to CardinalKit",
                            fontSize = 23.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Left
                        )
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 40.dp)
                                .padding(top = 10.dp),
                            text = "Etiam eleifend lectus vel arcu mollis, eget rutrum est gravida. Suspendisse placerat accumsan justo nec vehicula. Aliquam sit amet odio eu magna placerat commodo. Maecenas mollis eu est sit amet sagittis.",
                            fontSize = 14.sp,
                            textAlign = TextAlign.Left
                        )
                        Image(
                            modifier = Modifier
                                .fillMaxWidth(0.6f)
                                .fillMaxHeight(0.47f),
                            painter = painterResource(R.drawable.welcome),
                            contentDescription = "Pager Image"
                        )
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 40.dp),
                            text = "- Nulla fermentum tincidunt finibus.",
                            fontSize = 14.sp,
                            textAlign = TextAlign.Left
                        )
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 40.dp)
                                .padding(top = 5.dp),
                            text = "- Praesent lacinia eros id leo commodo, at faucibus augue condimentum.",
                            fontSize = 14.sp,
                            textAlign = TextAlign.Left
                        )
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 40.dp)
                                .padding(top = 5.dp),
                            text = "- Sed gravida nibh ultricies sem condimentum ornare. Etiam lacinia congue dolor.",
                            fontSize = 14.sp,
                            textAlign = TextAlign.Left
                        )
                        Box(modifier = Modifier
                                .padding(top = 40.dp)
                        ){
                            ReviewButton(
                                modifier = Modifier
                            ) {
                                navController.navigate(Screens.OnboardingScreen.route)
                            }

                        }

                    }
        }
    )



}











@Composable
fun ReviewButton(
    modifier: Modifier,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Center,
    )  {
            Button(
                onClick = onClick,
                shape= RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.Gray,
                    backgroundColor = Color.LightGray
                )
            ) {
                Text(
                    text = "Get Started",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(vertical=7.dp, horizontal = 90.dp))
                }
         }

}


