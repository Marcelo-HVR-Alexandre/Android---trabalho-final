package pt.tpsi_5423.contactagenda.model

data class Contact(
    val id: Int,
    var name: String,
    var phone: String,
    var email: String,
    var address: String
)
