package edu.stanford.cardinalkit.presentation.contacts

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.TopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import edu.stanford.cardinalkit.R
import edu.stanford.cardinalkit.presentation.contacts.components.ContactCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactsScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.contacts_screen_title),
                        style = MaterialTheme.typography.headlineSmall,
                        color = Color.White
                    )
                }
            )
        },
        content = {
            Column(
                modifier = Modifier.padding(top = 50.dp)
            ) {
                ContactCard(
                    name = "Dr. Oliver Aalami",
                    title = "Director",
                    email = "aalami@stanford.edu",
                    phone = "123-456-7890"
                )
                Spacer(modifier = Modifier.height(10.dp))
                ContactCard(
                    name = "Dr. Vishnu Ravi",
                    title = "Architect",
                    email = "vishnur@stanford.edu",
                    phone = "123-456-7890"
                )
            }
        }
    )
}
