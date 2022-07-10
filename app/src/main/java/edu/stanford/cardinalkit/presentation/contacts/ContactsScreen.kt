package edu.stanford.cardinalkit.presentation.contacts

import android.util.Log
import android.widget.ScrollView
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.hilt.navigation.compose.hiltViewModel
import edu.stanford.cardinalkit.R
import edu.stanford.cardinalkit.domain.models.Response
import edu.stanford.cardinalkit.presentation.common.ProgressIndicator
import edu.stanford.cardinalkit.presentation.contacts.components.ContactCard

@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactsScreen(
    viewModel: ContactsViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.contacts_screen_title),
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
            when(val contactsResponse = viewModel.contactsState.value) {
                is Response.Error -> Log.d("ContactsScreen", contactsResponse.e?.message.toString())
                is Response.Loading -> ProgressIndicator()
                is Response.Success -> LazyColumn(
                modifier = Modifier
                    .padding(top = 60.dp)
            ) { if(contactsResponse.data != null) {
                    items(
                        items = contactsResponse.data
                    ) { contact ->
                        ContactCard(
                            name = contact.name,
                            title = contact.title,
                            description = contact.description,
                            email = contact.email,
                            phone = contact.phone,
                            addressLineOne = contact.addressLineOne,
                            addressLineTwo = contact.addressLineTwo
                        )
                    }
                }
            }
            }
                Spacer(modifier = Modifier.padding(50.dp))
        }
    )
}
