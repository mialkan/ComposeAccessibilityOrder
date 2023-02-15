package com.mialkan.composeaccessibilityorder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatRatingBar
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController

class ComposeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                ComposeScreen(navController = findNavController())
            }
        }
    }
}

@Composable
fun ComposeScreen(navController: NavController) {
    MaterialTheme {
        Scaffold(
            topBar = {
                AppTopAppBar(
                    title = "Compose",
                    navController = navController
                )
            },
            bottomBar = {
                BottomAppBar {
                    Button(
                        onClick = { },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = true,
                        content = {
                            Text(text = "Send feedback")
                        }
                    )
                }
            }
        ) { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)) {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 20.dp, vertical = 20.dp)
                        .verticalScroll(
                            rememberScrollState()
                        ),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Lorem ipsum")
                    Text(text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.")
                    AndroidView(
                        factory = { context ->
                            AppCompatRatingBar(context).apply {
                                setOnRatingBarChangeListener { _, _, _ ->
                                }
                            }
                        },
                        update = { _ ->
                        }
                    )
                    TextField(
                        value = "",
                        onValueChange = {},
                        label = {
                            Text(text = "Feedback")
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun AppTopAppBar(
    title: String,
    actions: @Composable RowScope.() -> Unit = {},
    navController: NavController,
    navigationIconVector: ImageVector = Icons.Filled.ArrowBack,
    navigationIcon: @Composable (() -> Unit)? = {
        AppIconButton(onClick = { navController.popBackStack() }) {
            Icon(
                imageVector = navigationIconVector,
                contentDescription = "Navigate Up"
            )
        }
    }
) {
    AppTopAppBar(
        content = {
            Text(
                text = title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.semantics { heading() }
            )
        },
        actions = actions,
        navController = navController,
        navigationIconVector = navigationIconVector,
        navigationIcon = navigationIcon
    )
}

@Composable
fun AppTopAppBar(
    content: @Composable () -> Unit,
    actions: @Composable RowScope.() -> Unit = {},
    navController: NavController,
    navigationIconVector: ImageVector = Icons.Filled.ArrowBack,
    navigationIcon: @Composable (() -> Unit)? = {
        AppIconButton(onClick = { navController.popBackStack() }) {
            Icon(
                imageVector = navigationIconVector,
                contentDescription = "Navigate Up"
            )
        }
    }
) {
    var barNavigationIcon = navigationIcon
    navController.addOnDestinationChangedListener { _, _, _ ->
        if (navController.currentBackStackEntry == null) {
            barNavigationIcon = null
        }
    }

    TopAppBar(
        backgroundColor = MaterialTheme.colors.surface,
        title = content,
        navigationIcon = barNavigationIcon,
        actions = actions
    )
}

@Composable
fun AppIconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    content: @Composable () -> Unit
) {
    IconButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        content = content
    )
}
