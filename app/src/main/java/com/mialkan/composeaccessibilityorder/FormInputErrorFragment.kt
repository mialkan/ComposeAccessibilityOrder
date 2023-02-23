package com.mialkan.composeaccessibilityorder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.delay

class FormInputErrorFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                FormInputErrorScreen(navController = findNavController())
            }
        }
    }
}

@Composable
fun FormInputErrorScreen(
    navController: NavController,
    scaffoldState: ScaffoldState = rememberScaffoldState()
) {
    var showLoadingDuration by remember { mutableStateOf(500) }
    var showLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    LaunchedEffect(key1 = showLoading) {
        if (showLoading) {
            delay(showLoadingDuration.toLong())
            showLoading = false
            errorMessage = "Is this message read?"
        }
    }
    MaterialTheme {
        Scaffold(
            scaffoldState = scaffoldState,
            topBar = {
                AppTopAppBar(
                    title = "Compose",
                    navController = navController
                )
            },
            bottomBar = {
                BottomAppBar(backgroundColor = Color.White) {
                    TextButton(
                        onClick = {
                            errorMessage = ""
                            showLoadingDuration = 500
                            showLoading = true
                        },
                        modifier = Modifier.weight(1f),
                        enabled = true,
                        content = {
                            Text(text = "Test 500MS")
                        }
                    )

                    TextButton(
                        onClick = {
                            errorMessage = ""
                            showLoadingDuration = 1500
                            showLoading = true
                        },
                        modifier = Modifier.weight(1f),
                        enabled = true,
                        content = {
                            Text(text = "Test 1500MS")
                        }
                    )
                }
            }
        ) { paddingValues ->

            LoadingDialog(showLoadingDialog = showLoading)
            Box(modifier = Modifier.padding(paddingValues)) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    Text(
                        text = "Check error message",
                        style = MaterialTheme.typography.body2,
                        modifier = Modifier.padding(bottom = 20.dp)
                    )
                    AppDropdownMenu(
                        defaultValue = "",
                        hint = "Check Error Button",
                        items = listOf("1", "2", "3", "4"),
                        modifier = Modifier.fillMaxWidth().padding(10.dp),
                        errorMessage = errorMessage.ifEmpty { null },
                        onSelect = { _, _ ->
                        }
                    )
                }
            }
        }
    }
}
