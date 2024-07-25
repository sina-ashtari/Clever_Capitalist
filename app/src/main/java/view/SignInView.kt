package view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import viewModel.SignInViewModel

@Composable
fun SignInView(viewModel: SignInViewModel){

    val signInState by viewModel.signInState.observeAsState()

    val email = remember{ mutableStateOf("") }
    val password = remember{ mutableStateOf("") }
    
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally){

        Row (modifier = Modifier.fillMaxWidth()){
            OutlinedTextField(value = email.value, onValueChange = {email.value = it} , label = { Text(
                text = "Email"
            )})
            OutlinedTextField(value = password.value, onValueChange = {password.value = it} , label = { Text(
                text = "Password"
            )})
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = { viewModel.signIn(email = email.value , password = password.value) }) {

            }
        }


    }
    signInState?.let {result ->
        when {
            result.isSuccess ->{
                // snack bar
            }
            result.isFailure ->{
                //snack bar
            }
        }

    }

}