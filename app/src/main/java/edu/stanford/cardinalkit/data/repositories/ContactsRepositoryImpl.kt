package edu.stanford.cardinalkit.data.repositories

import com.google.gson.Gson
import edu.stanford.cardinalkit.domain.models.Contact

class ContactsRepositoryImpl {
    override fun getContacts(): List<Contact> {
        lateinit var jsonString: String
        try {
            jsonString = application.assets.open("contacts.json")
                .bufferedReader()
                .use { it.readText() }
        } catch (ioException: IOException) {
            AppLogger.d(ioException)
        }

        val listContactsType = object : TypeToken<List<Contact>>() {}.type
        return Gson().fromJson(jsonString, listContactsType)
    }
}