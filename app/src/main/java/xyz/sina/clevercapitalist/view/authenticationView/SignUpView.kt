package xyz.sina.clevercapitalist.view.authenticationView

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
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
import xyz.sina.clevercapitalist.viewModel.SignUpViewModel

@Composable
fun SignUpView(navController: NavHostController) {

    val viewModel : SignUpViewModel = hiltViewModel()
    val signUpState by viewModel.signUpState.observeAsState()

    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }

    val snackBarHostState = remember {
        SnackbarHostState()
    }
    val scope = rememberCoroutineScope()

    Scaffold(snackbarHost = { SnackbarHost(hostState = snackBarHostState)}) { innerPadding->
        Column (modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            Button(onClick = { }) {
                Text(text = "Continue with Google")
            }
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)){
                HorizontalDivider(thickness = 1.dp)
                Text(text = "OR")
                HorizontalDivider(thickness = 1.dp)
            }

            OutlinedTextField(value = email.value, onValueChange = {email.value = it}, label= {Text("Email")})
            OutlinedTextField(value = password.value, onValueChange = {password.value = it}, visualTransformation = PasswordVisualTransformation() ,label = {Text("Password")})
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = {viewModel.signUp(email = email.value, password = password.value)}) {
                Text("Sign up")
            }

            signUpState?.let {result->
                when{
                    result.isSuccess -> {
                        scope.launch { snackBarHostState.showSnackbar("worked") }
                    }
                    result.isFailure ->{
                        scope.launch { snackBarHostState.showSnackbar("try again") }
                    }
                }
            }

        }

    }
}