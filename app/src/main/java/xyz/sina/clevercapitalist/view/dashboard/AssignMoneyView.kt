package xyz.sina.clevercapitalist.view.dashboard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController

@Composable
fun AssignView(navController: NavHostController) {

    val snackBarHostState = remember {
        SnackbarHostState()
    }
    val snackBarScope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackBarHostState)}
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {

        }
    }

}