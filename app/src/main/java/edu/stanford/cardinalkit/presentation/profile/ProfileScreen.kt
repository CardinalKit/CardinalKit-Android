package edu.stanford.cardinalkit.presentation.profile

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import edu.stanford.cardinalkit.domain.models.Response
import edu.stanford.cardinalkit.presentation.common.ProgressIndicator
import edu.stanford.cardinalkit.presentation.navigation.Screens
import edu.stanford.cardinalkit.presentation.profile.components.UserCard
import edu.stanford.cardinalkit.R
import edu.stanford.cardinalkit.presentation.profile.components.ProfileCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavHostController,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.profile_screen_title),
                        modifier = Modifier.padding(10.dp),
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Light
                    )
                },
                backgroundColor = Color(0xFFF1F1F1),
                contentColor = Color.Black)

        },
        containerColor =  Color(0xFFF5F5F5),
        content = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Top
            ) { Box(Modifier.padding(top=70.dp)){
                Column(
                    Modifier
                        .padding(horizontal = 20.dp)
                        .padding(vertical = 20.dp)) {
                    Image(imageVector=Icons.Filled.AccountCircle ,
                        contentDescription = "account image",
                        modifier = Modifier
                            .height(100.dp)
                            .width(100.dp)
                            .align(Alignment.CenterHorizontally),
                        colorFilter = ColorFilter.tint(Color(0xFF484965))
                    )
                    Text(
                        modifier = Modifier.padding(top = 20.dp),
                        text="Profile Name",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    UserCard(
                        fullName = viewModel.fullName
                    )

                }
            }
                ScreenContent()
                Spacer(modifier = Modifier.height(25.dp))
                Row(
                    Modifier
                        .padding(top = 15.dp)
                        .align(Alignment.CenterHorizontally),
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.Center,
                )  {
                    androidx.compose.material.Button(
                        onClick = {viewModel.signOut()},
                        shape= RoundedCornerShape(50),
                        colors = ButtonDefaults.buttonColors(
                            contentColor = Color.Gray,
                            backgroundColor = Color.LightGray
                        )
                    ) {
                        androidx.compose.material.Text(
                            text = "Withdraw from study",
                            fontSize = 16.sp,
                            modifier = Modifier.padding(vertical=9.dp, horizontal = 70.dp))
                    }
                }
                Spacer(modifier = Modifier.height(100.dp))
            }
        }
    )

    when(val signOutResponse = viewModel.signOutState.value) {
        is Response.Loading -> ProgressIndicator()
        is Response.Success -> {
            val signedOut = signOutResponse.data
            signedOut?.let {
                if (signedOut) {
                    navController.navigate(Screens.LoginScreen.route)
                }
            }
        }
        is Response.Error -> signOutResponse.e?.let {
            LaunchedEffect(Unit) {
                print(it)
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenContent(){
    Column() {
        Row(Modifier.background(Color.White)){
            ProfileCard(title = "Update Health Records")
        }
        Spacer(modifier = Modifier.height(3.dp))
        Row(Modifier.background(Color.White)){
            ProfileCard(title = "Change Password")
        }
        Spacer(modifier = Modifier.height(3.dp))
        Row(Modifier.background(Color.White)){
            ProfileCard(title = "Help")
        }
        Spacer(modifier = Modifier.height(15.dp))
        Row(Modifier.background(Color.White)){
            ProfileCard(title = "Report a Problem")
        }
        Spacer(modifier = Modifier.height(3.dp))
        Row(Modifier.background(Color.White)){
            ProfileCard(title = "Support")
        }
        Spacer(modifier = Modifier.height(3.dp))
        Row(Modifier.background(Color.White)){
            ProfileCard(title = "View Consent Form")
        }


    }
}
