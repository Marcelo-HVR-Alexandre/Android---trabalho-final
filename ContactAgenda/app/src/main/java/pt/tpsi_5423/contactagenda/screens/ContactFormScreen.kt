package pt.tpsi_5423.contactagenda.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import pt.tpsi_5423.contactagenda.viewmodel.ContactViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactFormScreen(
    viewModel: ContactViewModel,
    contactId: Int?, // nullable → null = add mode, ID = edit mode
    onSave: () -> Unit
) {
    val editingContact = contactId?.let { viewModel.getContactById(it) }

    var name by remember { mutableStateOf(editingContact?.name ?: "") }
    var phone by remember { mutableStateOf(editingContact?.phone ?: "") }
    var email by remember { mutableStateOf(editingContact?.email ?: "") }
    var address by remember { mutableStateOf(editingContact?.address ?: "") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        if (editingContact != null) "Edit Contact" else "Add New Contact",
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(24.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Text("Contact Information", style = MaterialTheme.typography.titleMedium)

            ElevatedCard(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    InputFieldWithIcon("Full Name", name, { name = it }, Icons.Default.Person)
                    InputFieldWithIcon("Phone Number", phone, { phone = it }, Icons.Default.Phone)
                    InputFieldWithIcon("Email Address", email, { email = it }, Icons.Default.Email)
                    InputFieldWithIcon("Address", address, { address = it }, Icons.Default.Home)
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    if (editingContact != null) {
                        viewModel.updateContact(
                            editingContact.copy(
                                name = name,
                                phone = phone,
                                email = email,
                                address = address
                            )
                        )
                    } else {
                        viewModel.addContact(name, phone, email, address)
                    }
                    onSave()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = MaterialTheme.shapes.medium
            ) {
                Text("Save Contact", style = MaterialTheme.typography.labelLarge)
            }
        }
    }
}

@Composable
fun InputFieldWithIcon(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    icon: androidx.compose.ui.graphics.vector.ImageVector
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        leadingIcon = { Icon(imageVector = icon, contentDescription = label) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        singleLine = true
    )
}
