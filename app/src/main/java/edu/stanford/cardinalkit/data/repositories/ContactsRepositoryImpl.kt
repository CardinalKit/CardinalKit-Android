package edu.stanford.cardinalkit.data.repositories

import android.content.Context
import com.google.firebase.firestore.CollectionReference
import com.google.gson.Gson
import com.google.gson.JsonParseException
import com.google.gson.reflect.TypeToken
import edu.stanford.cardinalkit.common.Config
import edu.stanford.cardinalkit.common.Constants
import edu.stanford.cardinalkit.domain.models.Contact
import edu.stanford.cardinalkit.domain.models.Response
import edu.stanford.cardinalkit.domain.models.tasks.CKTask
import edu.stanford.cardinalkit.domain.repositories.ContactsRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject
import javax.inject.Named

class ContactsRepositoryImpl @Inject constructor(
    @Named(Constants.CONTACTS_REF)
    private val contactsRef: CollectionReference?,
    private val context: Context
) : ContactsRepository {

    override fun getContacts() =
        if (Config.REMOTE_CONTACTS) {
            callbackFlow {
                contactsRef?.let {
                    val snapshotListener =
                        contactsRef.addSnapshotListener { snapshot, e ->
                            val response = if (snapshot != null) {
                                val contacts = snapshot.toObjects(Contact::class.java)
                                Response.Success(contacts)
                            } else {
                                Response.Error(e)
                            }
                            trySend(response).isSuccess
                        }
                    awaitClose {
                        snapshotListener.remove()
                    }
                }
            }
        } else {
            flow {
                lateinit var jsonString: String

                // Load contact data from JSON file in assets
                emit(Response.Loading)
                try {
                    jsonString = context.assets.open(Config.CONTACTS_FILE)
                        .bufferedReader().use { it.readText() }
                } catch (e: IOException) {
                    emit(Response.Error(e))
                }

                // Deserialize JSON into a list of Contacts
                try {
                    val listContactsType = object : TypeToken<List<Contact>>() {}.type
                    val contacts: List<Contact> = Gson().fromJson(jsonString, listContactsType)
                    emit(Response.Success(contacts))
                } catch (e: JsonParseException) {
                    emit(Response.Error(e))
                }
            }
        }
}
