package edu.stanford.cardinalkit.presentation.contacts

import android.widget.ScrollView
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.TopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.stanford.cardinalkit.R
import edu.stanford.cardinalkit.presentation.contacts.components.ContactCard
@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactsScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.contacts_screen_title),
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
                    .padding(top = 60.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                ContactCard(
                    name = "Dr. Oliver Aalami",
                    title = "Director",
                    description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor",
                    email = "aalami@stanford.edu",
                    phone = "123-456-7890",
                    addressLineOne="318 Campus Drive",
                    addressLineTwo="Stanford CA 94305"
                )
                ContactCard(
                    name = "Dr. Vishnu Ravi",
                    title = "Architect",
                    description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor",
                    email = "vishnur@stanford.edu",
                    phone = "123-456-7890",
                    addressLineOne="318 Campus Drive",
                    addressLineTwo="Stanford CA 94305",
                )
                Spacer(modifier = Modifier.padding(50.dp))
            }
        }
    )
}
