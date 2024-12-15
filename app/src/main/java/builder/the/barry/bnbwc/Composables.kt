package builder.the.barry.bnbwc

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun HomeScreen(navController: NavController) {
    MaterialTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Hello Home!",
                modifier = Modifier.clickable {
                    navController.navigate(R.id.action_mainFragment_to_secondFragment)
                }
            )
        }
    }
}

@Composable
fun MyTicketScreen() {
    MaterialTheme {
        Text("Hello My Ticket!")
    }
}

@Composable
fun AccountScreen() {
    MaterialTheme {
        Text("Hello Profile!")
    }
}
