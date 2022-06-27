package edu.stanford.cardinalkit.presentation.profile

import androidx.compose.foundation.layout.*
import androidx.compose.material.TopAppBar
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import edu.stanford.cardinalkit.domain.models.Response
import edu.stanford.cardinalkit.presentation.common.ProgressIndicator
import edu.stanford.cardinalkit.presentation.navigation.Screens
import edu.stanford.cardinalkit.presentation.profile.components.UserCard
import edu.stanford.cardinalkit.R

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
                        style = MaterialTheme.typography.headlineSmall,
                        color = Color.White
                    )
                },
            )
        },
        content = { _ ->
            Box(
                modifier = Modifier.fillMaxSize().padding(top = 50.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center
                ) {
                    UserCard(
                        fullName = viewModel.fullName
                    )
                    Spacer(modifier = Modifier.height(25.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Button(onClick = { viewModel.signOut() }) {
                            Text(
                                text = stringResource(R.string.sign_out_button)
                            )
                        }
                    }
                }
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