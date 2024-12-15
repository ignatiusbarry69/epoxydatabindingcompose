package builder.the.barry.bnbwc

import androidx.compose.compiler.plugins.kotlin.ComposeCallableIds.remember
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.airbnb.epoxy.EpoxyRecyclerView

@Composable
fun HomeScreen(navController: NavController) {
    MaterialTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
//            Text(
//                text = "Hello Home!",
//                modifier = Modifier.clickable {
//                    navController.navigate(R.id.action_mainFragment_to_secondFragment)
//                }
//            )
            EpoxyRecyclerView(navController)
        }
    }
}

@Composable
fun EpoxyRecyclerView(navController: NavController){
    val onItemClick: (String) -> Unit = { name ->
        navController.navigate(R.id.action_mainFragment_to_secondFragment)
    }
    val controller = remember { MyEpoxyController(onItemClick = onItemClick) }
    controller.secondClass= getUiModel()
    AndroidView(
        modifier = Modifier,
        factory = { context ->
            EpoxyRecyclerView(context).apply {
                setController(controller)
                layoutManager = LinearLayoutManager(context)
            }
        },
        update = { view ->
            controller.requestModelBuild()
        }
    )
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
