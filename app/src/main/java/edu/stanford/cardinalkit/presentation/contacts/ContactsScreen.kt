package edu.stanford.cardinalkit.presentation.contacts

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
import timber.log.Timber

@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactsScreen(
    viewModel: ContactsViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.contacts_screen_title),
                        modifier = Modifier.padding(5.dp),
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Light,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                },
                colors = TopAppBarDefaults.smallTopAppBarColors()
            )
        },
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
        content = { padding ->
            Column(modifier = Modifier.padding(bottom = 50.dp)) {
                when (val contactsResponse = viewModel.contactsState.value) {
                    is Response.Error -> Timber.d(contactsResponse.e?.message.toString())
                    is Response.Loading -> ProgressIndicator()
                    is Response.Success -> LazyColumn(
                        modifier = Modifier
                            .padding(top = 60.dp)
                    ) {
                        if (contactsResponse.data != null) {
                            items(
                                items = contactsResponse.data
                            ) { contact ->
                                ContactCard(contact)
                            }
                        }
                    }
                }
            }
        }
    )
}
