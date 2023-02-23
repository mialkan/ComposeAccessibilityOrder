package com.mialkan.composeaccessibilityorder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.delay

class SnackBarErrorFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                SnackBarErrorScreen(navController = findNavController())
            }
        }
    }
}

@Composable
fun SnackBarErrorScreen(navController: NavController, scaffoldState: ScaffoldState = rememberScaffoldState()) {
    var showLoadingDuration by remember{ mutableStateOf(500) }
    var showLoading by remember { mutableStateOf(false) }
    var showSnackbar by remember { mutableStateOf(false) }
    LaunchedEffect(key1 = showLoading) {
        if (showLoading) {
            delay(showLoadingDuration.toLong())
            showLoading = false
            showSnackbar = true
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
                /*BottomAppBar(backgroundColor = Color.White) {
                    Button(
                        onClick = { showLoading = true },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = true,
                        content = {
                            Text(text = "Test Snackbar")
                        }
                    )
                }*/
            }
        ) { paddingValues ->

            LaunchedEffect(key1 = showSnackbar) {
                if (showSnackbar) {
                    scaffoldState.snackbarHostState.showSnackbar("This is snack bar for accessibility")
                    showSnackbar = false
                }
            }
            LoadingDialog(showLoadingDialog = showLoading)
            Box(modifier = Modifier.padding(paddingValues)) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_email_verificiation),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 20.dp, top = 20.dp)
                            .align(Alignment.CenterHorizontally)
                            .size(80.dp, 80.dp)
                    )
                    Text(
                        text = "Check your email",
                        style = MaterialTheme.typography.h5,
                        modifier = Modifier.padding(bottom = 20.dp)
                    )
                    Text(
                        text = "We sent a verification link to:",
                        style = MaterialTheme.typography.body2,
                        modifier = Modifier.padding(bottom = 24.dp)
                    )
                    Row(
                        modifier = Modifier.wrapContentHeight()
                            .padding(bottom = 24.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_checkmark),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp, 24.dp)
                                .padding(end = 8.dp)
                        )
                        Text(
                            text = "mock@gmail.com",
                            style = MaterialTheme.typography.h5
                        )
                    }
                    Text(
                        text = "please_be_sure_to_open_from_mobile",
                        style = MaterialTheme.typography.body2
                    )
                    TextButton(
                        onClick = {
                            showLoadingDuration = 500
                            showLoading = true
                        },
                        modifier = Modifier
                            .padding(
                                horizontal = 5.dp,
                                vertical = 5.dp
                            )
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = "Send New Link 500ms",
                            style = MaterialTheme.typography.h4
                        )
                    }
                    TextButton(
                        onClick = {
                            showLoadingDuration = 1500
                            showLoading = true
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                horizontal = 5.dp,
                                vertical = 5.dp
                            )
                    ) {
                        Text(
                            text = "Send New Link 1500ms",
                            style = MaterialTheme.typography.h4
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun LoadingDialog(showLoadingDialog: Boolean) {
    if (showLoadingDialog) {
        Dialog(
            onDismissRequest = { },
            DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(54.dp)
            ) {
                CircularProgressIndicator(
                    modifier =
                    Modifier
                        .background(
                            Color.White,
                            shape = RoundedCornerShape(48.dp)
                        )
                        .padding(12.dp)
                        .fillMaxSize(),
                    strokeWidth = 3.dp
                )
            }
        }
    }
}
