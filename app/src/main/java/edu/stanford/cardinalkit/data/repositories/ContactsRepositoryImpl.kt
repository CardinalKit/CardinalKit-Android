package edu.stanford.cardinalkit.data.repositories

import android.content.Context
import com.google.gson.Gson
import com.google.gson.JsonParseException
import com.google.gson.reflect.TypeToken
import edu.stanford.cardinalkit.domain.models.Contact
import edu.stanford.cardinalkit.domain.models.Response
import edu.stanford.cardinalkit.domain.repositories.ContactsRepository
import java.io.IOException
import javax.inject.Inject

class ContactsRepositoryImpl @Inject constructor(
    private val context: Context
    ) : ContactsRepository {

    override fun getContacts(): Response<List<Contact>> {
        lateinit var jsonString: String
        
        // Load contact data from JSON file in assets
        try {
            jsonString = context.assets.open("contacts.json")
                .bufferedReader().use { it.readText() }
        } catch (e: IOException) {
            return Response.Error(e)
        }

        // Deserialize JSON into a list of Contacts
        return try {
            val listContactsType = object : TypeToken<List<Contact>>() {}.type
            val contacts: List<Contact> = Gson().fromJson(jsonString, listContactsType)
            Response.Success(contacts)
        } catch (e: JsonParseException){
            Response.Error(e)
        }
    }
}
