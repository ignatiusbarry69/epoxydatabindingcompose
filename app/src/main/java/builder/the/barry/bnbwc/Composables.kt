package builder.the.barry.bnbwc

import androidx.annotation.RawRes
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.compiler.plugins.kotlin.ComposeCallableIds.remember
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale.Companion.Fit
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import androidx.recyclerview.widget.LinearLayoutManager
import app.rive.runtime.kotlin.RiveAnimationView
import app.rive.runtime.kotlin.core.Fit
import com.airbnb.epoxy.EpoxyRecyclerView
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(navController: NavController) {
    MaterialTheme {
        Box(
            modifier = Modifier
                .fillMaxSize(),
//                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
//            Text(
//                text = "Hello Home!",
//                modifier = Modifier.clickable {
//                    navController.navigate(R.id.action_mainFragment_to_secondFragment)
//                }
//            )
            CustomPullRefreshSample(height = 100f,navController)
        }
    }
}

@Composable
fun EpoxyRecyclerView(
    navController: NavController,
    modifier: Modifier,
    flingBehavior: FlingBehavior = ScrollableDefaults.flingBehavior(),
){
    val onItemClick: (String) -> Unit = { name ->
        navController.navigate(R.id.action_mainFragment_to_secondFragment)
    }
    val controller = remember { MyEpoxyController(onItemClick = onItemClick) }
    controller.secondClass= getUiModel()
    AndroidView(
        modifier = modifier,
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

@Composable
fun ComposableRiveAnimationView(
    modifier: Modifier = Modifier,
    @RawRes animation: Int,
    stateMachineName: String? = null,
    alignment: app.rive.runtime.kotlin.core.Alignment = app.rive.runtime.kotlin.core.Alignment.CENTER,
    fit: Fit = app.rive.runtime.kotlin.core.Fit.CONTAIN,
    onInit: (RiveAnimationView) -> Unit
) {
    AndroidView(
        modifier = modifier,
        factory = { context ->
            RiveAnimationView(context).also {
                it.setRiveResource(
                    resId = animation,
                    stateMachineName = stateMachineName,
                    alignment = alignment,
                    fit = fit,

                    )
            }
        },
        update = { view -> onInit(view) }
    )
}

@Composable
@OptIn(ExperimentalMaterialApi::class)
fun CustomPullRefreshSample(height: Float, navController: NavController) {
    // Set system UI color
    val systemUiController = rememberSystemUiController()
    val useDarkIcons = !isSystemInDarkTheme()
    DisposableEffect(systemUiController, useDarkIcons) {
        systemUiController.setSystemBarsColor(
            color = Color(0xFF003C3D),
        )
        onDispose {}
    }


    // Variables
    val refreshScope = rememberCoroutineScope()
    val threshold = with(LocalDensity.current) { height.dp.toPx() }

    var refreshing by remember { mutableStateOf(false) }
    var itemCount by remember { mutableStateOf(15) }
    var currentDistance by remember { mutableStateOf(0f) }
    var animation: RiveAnimationView? = null

    val progress = currentDistance / threshold

    val scrollValue by animateFloatAsState(
        targetValue = height * progress,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioNoBouncy,
            stiffness = Spring.StiffnessHigh,
        ),
        finishedListener = { value ->
            if (value == 0f) {
                // Only reset the animation when not visible
                animation?.reset()
                animation?.pause()
            }
        }
    )

    // Methods
    fun refresh() = refreshScope.launch {
        refreshing = true
        // This simulates loading data with delays. The delays are added to ensure the different
        // states of the animation has time to play
        animation?.fireState("numberSimulation", "advance")
        delay(1200) // Some future to complete - loading data for example
        animation?.fireState("numberSimulation", "advance")
        delay(1200)

        // Once complete set the target value back to 0, and refreshing false.
        animate(initialValue = currentDistance, targetValue = 0f) { value, _ ->
            currentDistance = value
        }
        refreshing = false
    }

    fun onPull(pullDelta: Float): Float = when {
        refreshing -> 0f
        else -> {
            val newOffset = (currentDistance + pullDelta).coerceAtLeast(0f)
            val dragConsumed = newOffset - currentDistance

            currentDistance = newOffset
            animation?.setNumberState("numberSimulation", "pull", progress * 100)
            dragConsumed
        }
    }

    fun onRelease(velocity: Float): Float {
        if (refreshing) return 0f // Already refreshing - don't call refresh again.
        var targetValue = 0f;
        if (currentDistance > threshold) {
            targetValue = threshold
            refresh()
        }

        refreshScope.launch {
            animate(initialValue = currentDistance, targetValue = targetValue) { value, _ ->
                currentDistance = value
            }
        }

        // Only consume if the fling is downwards and the indicator is visible
        return if (velocity > 0f && currentDistance > 0f) {
            velocity
        } else {
            0f
        }
    }

    Box(Modifier.pullRefresh(::onPull, ::onRelease)) {
        if (scrollValue > 0) {
            Box(
                Modifier
                    .height(height.dp)
                    .offset(
                        0.dp,
                        (-(height / 2 - scrollValue / 2).coerceIn(
                            maximumValue = height,
                            minimumValue = 0f
                        )).dp
                    )
                    .fillMaxWidth()
                    .align(Alignment.TopCenter)
            ) {
                ComposableRiveAnimationView(
                    animation = R.raw.pull_to_refresh_use_case,
                    stateMachineName = "numberSimulation",
                    fit = app.rive.runtime.kotlin.core.Fit.COVER,
                ) { view ->
                    animation = view
                }
            }
        }

        EpoxyRecyclerView(navController, modifier = Modifier.offset(y = (scrollValue).dp).fillMaxHeight())

    }
//        LazyColumn(
//            modifier = Modifier
//                .offset(y = (scrollValue).dp)
//                .fillMaxHeight()
//                .background(Color(0xFF001C1C))
//        ) {
//            items(itemCount) {
//                ListItemUI()
//            }
//
//        }
}

@Composable
fun ListItemUI() {
    Box(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .height(100.dp)
            .background(color = Color(0xFF003C3D), shape = RoundedCornerShape(8.dp))
    ) {
        Box(
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.CenterStart)
                .size(64.dp)
                .background(color = Color.DarkGray, shape = RoundedCornerShape(4.dp))
        )

        Box(
            modifier = Modifier
                .padding(start = 88.dp, top = 0.dp, end = 16.dp, bottom = 16.dp)
                .align(alignment = Alignment.Center)
                .fillMaxWidth()
                .height(16.dp)
                .background(color = Color.DarkGray, shape = RoundedCornerShape(4.dp))
        )

        Box(
            modifier = Modifier
                .padding(start = 88.dp, top = 40.dp, end = 16.dp)
                .align(Alignment.Center)
                .fillMaxWidth()
                .height(16.dp)
                .background(color = Color.DarkGray, shape = RoundedCornerShape(4.dp))
        )
    }
}

