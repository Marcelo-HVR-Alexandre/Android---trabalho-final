package pt.tpsi_5423.contactagenda.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import pt.tpsi_5423.contactagenda.model.Contact

class ContactViewModel : ViewModel() {
    private var nextId = 1
    var contacts = mutableStateListOf<Contact>()
        private set

    fun addContact(name: String, phone: String, email: String, address: String) {
        val newContact = Contact(nextId++, name, phone, email, address)
        contacts.add(newContact)
    }

    fun deleteContact(contact: Contact) {
        contacts.remove(contact)
    }

    fun getContactById(id: Int): Contact? {
        return contacts.find { it.id == id }
    }
    fun updateContact(updated: Contact) {
        contacts.replaceAll { if (it.id == updated.id) updated else it }
    }

}
