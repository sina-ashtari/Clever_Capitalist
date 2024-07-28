package xyz.sina.clevercapitalist.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.Scaffold
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import xyz.sina.clevercapitalist.viewModel.SignInViewModel

@Composable
fun SignInView(navController: NavHostController) {

    val viewModel : SignInViewModel = hiltViewModel()

    val signInState by viewModel.signInState.observeAsState()

    val email = remember{ mutableStateOf("") }
    val password = remember{ mutableStateOf("") }

    val scope = rememberCoroutineScope()
    val snackBarHostState = remember {
        SnackbarHostState()
    }

    Scaffold(snackbarHost = { SnackbarHost(hostState = snackBarHostState)}){ innerPadding ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally){

            OutlinedTextField(value = email.value, onValueChange = {email.value = it} , label = { Text(
                text = "Email"
            )})
            OutlinedTextField(value = password.value, onValueChange = {password.value = it} , visualTransformation = PasswordVisualTransformation() , label = { Text(
                text = "Password"
            )})
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = { viewModel.signIn(email = email.value , password = password.value) }) {
                Text("Sign in")
            }

        }

        signInState?.let {result ->
            when {
                result.isSuccess ->{
                    scope.launch { snackBarHostState.showSnackbar("worked") }
                }
                result.isFailure ->{
                    scope.launch { snackBarHostState.showSnackbar("try again") }
                }

            }

        }
    }


}