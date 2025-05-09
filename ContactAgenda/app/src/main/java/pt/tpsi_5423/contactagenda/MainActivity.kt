package pt.tpsi_5423.contactagenda

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import pt.tpsi_5423.contactagenda.screens.ContactDetailScreen
import pt.tpsi_5423.contactagenda.screens.ContactFormScreen
import pt.tpsi_5423.contactagenda.screens.ContactListScreen
import pt.tpsi_5423.contactagenda.viewmodel.ContactViewModel
import pt.tpsi_5423.contactagenda.ui.theme.ContactAgendaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ContactAgendaTheme {
                AppNavigation()
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val viewModel: ContactViewModel = viewModel()

    NavHost(navController = navController, startDestination = "contact_list") {

        composable("contact_list") {
            ContactListScreen(
                viewModel = viewModel,
                onAddClick = { navController.navigate("contact_form") },
                onContactClick = { id -> navController.navigate("contact_detail/$id") }
            )
        }

        composable("contact_form") {
            ContactFormScreen(
                viewModel = viewModel,
                contactId = null,
                onSave = { navController.popBackStack() }
            )
        }

        composable(
            "contact_edit/{contactId}",
            arguments = listOf(navArgument("contactId") { type = NavType.IntType })
        ) { backStackEntry ->
            val contactId = backStackEntry.arguments?.getInt("contactId") ?: -1
            ContactFormScreen(
                viewModel = viewModel,
                contactId = contactId,
                onSave = { navController.popBackStack() }
            )
        }

        composable(
            "contact_detail/{contactId}",
            arguments = listOf(navArgument("contactId") { type = NavType.IntType })
        ) { backStackEntry ->
            val contactId = backStackEntry.arguments?.getInt("contactId") ?: -1
            ContactDetailScreen(
                viewModel = viewModel,
                contactId = contactId,
                onBack = { navController.popBackStack() },
                onEdit = { navController.navigate("contact_edit/$it") }
            )
        }
    }
}

