package xyz.sina.clevercapitalist.view.authenticationView

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import xyz.sina.clevercapitalist.view.Routes
import xyz.sina.clevercapitalist.viewModel.authentication.SignInViewModel

@Composable
fun SignInView(navController: NavHostController) {

    val viewModel : SignInViewModel = hiltViewModel()

    val signInState by viewModel.signInState.observeAsState()

    val scope = rememberCoroutineScope()
    val snackBarHostState = remember {
        SnackbarHostState()
    }

    androidx.compose.material.Scaffold(snackbarHost = { SnackbarHost(hostState = snackBarHostState)},
        backgroundColor = MaterialTheme.colorScheme.background
    ){ innerPadding ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally){

            OutlinedTextField(modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp), singleLine = true, value = viewModel.email, onValueChange = viewModel::changeEmail , label = {
                Text(text = "Email", color = MaterialTheme.colorScheme.onBackground)})
            OutlinedTextField(modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp),  singleLine = true, value = viewModel.password , onValueChange = viewModel::changePassword , visualTransformation = PasswordVisualTransformation() , label = {
                Text(text = "Password", color = MaterialTheme.colorScheme.onBackground)})
            Spacer(modifier = Modifier.height(8.dp))
            Button(modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp),colors = androidx.compose.material.ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),onClick = { viewModel.signIn(email = viewModel.email , password = viewModel.password) }) {
                Text(text ="Sign in", color = MaterialTheme.colorScheme.onPrimary)
            }
        }

        signInState?.let {result ->
            when {
                result.isSuccess ->{
                    navController.navigate(Routes.DASHBOARD_ROUTE)
                }
                result.isFailure ->{
                    scope.launch { snackBarHostState.showSnackbar("try again") }
                }
            }
        }
    }

}