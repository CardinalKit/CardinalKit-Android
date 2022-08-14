package edu.stanford.cardinalkit.presentation.welcome

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import edu.stanford.cardinalkit.R
import edu.stanford.cardinalkit.presentation.navigation.Screens
import edu.stanford.cardinalkit.ui.theme.PrimaryTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JoinStudyScreen(
    navController: NavHostController
){

    Scaffold(
        backgroundColor = PrimaryTheme,
        content = { contentPadding ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(horizontal = 40.dp)
                    .padding(top = 100.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box() {
                    Image(
                        modifier = Modifier.fillMaxWidth(.85f),
                        painter = painterResource(R.drawable.logo),
                        contentDescription = "logo"
                    )

                }
                Spacer(modifier = Modifier.height(30.dp))
                androidx.compose.material3.Text(
                    text = stringResource(R.string.app_name),
                    fontSize = 26.sp,
                    color = White,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center
                )
                androidx.compose.material3.Text(
                    text = stringResource(R.string.biodesign),
                    modifier = Modifier.padding(bottom = 20.dp),
                    fontSize = 13.sp,
                    color = White,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(30.dp))
                JoinStudyButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { navController.navigate(Screens.WelcomeScreen.route)})
                Spacer(modifier = Modifier.height(15.dp))
                ReturningUser(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {navController.navigate(Screens.SignInMethod.route)})
            }
    })
}

@Composable
fun JoinStudyButton(
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
                contentColor = Color.LightGray,
                backgroundColor = Color.DarkGray
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.new_user_button),
                fontSize = 16.sp,
                modifier = Modifier.padding(vertical=8.dp))
        }
    }
}

@Composable
fun ReturningUser(
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
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.existing_user_button),
                fontSize = 16.sp,
                modifier = Modifier.padding(vertical=8.dp))
        }
    }
}
