package xyz.sina.clevercapitalist.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.firebase.auth.UserInfo
import kotlinx.coroutines.launch
import xyz.sina.clevercapitalist.model.RegisterInfo
import xyz.sina.clevercapitalist.viewModel.registerFormViewModel.RegisterViewModel


@Composable
fun RegisterForm(navController: NavHostController){

    val viewModel : RegisterViewModel = hiltViewModel()

    val snackBarHostState = remember {SnackbarHostState()}
    val scope = rememberCoroutineScope()

    var userName by remember { mutableStateOf("") }
    var rent by remember { mutableStateOf("") }
    var transport by remember { mutableStateOf("") }
    var debts by remember { mutableStateOf("") }
    var otherExpenses by remember { mutableStateOf("") }

    Scaffold(snackbarHost = {
        SnackbarHost(hostState = snackBarHostState)
    }){ innerPadding ->
        Column(modifier = Modifier.padding(innerPadding), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Please enter your name")
            OutlinedTextField(value = userName, onValueChange = {userName = it }, label = {Text("Name")})
            Spacer(modifier=  Modifier.height(8.dp))
            // IDK now how to implement these in country which everything changes in seconds
            Text("How about your house ?")
            OutlinedTextField(value = rent, onValueChange = {rent = it }, label = {Text("Monthly Rent")})
            Spacer(modifier=  Modifier.height(8.dp))
            Text("How much money do you spend to get around?")
            OutlinedTextField(value = transport, onValueChange = {transport = it }, label = {Text("Monthly transport")})
            Spacer(modifier=  Modifier.height(8.dp))
            Text("Do you currently have any debt?")
            OutlinedTextField(value = debts, onValueChange = {debts = it }, label = {Text("debts")})
            Spacer(modifier=  Modifier.height(8.dp))
            Text("How much money do you spend on other things like Internet, Phone, Groceries , etc.?")
            OutlinedTextField(value = otherExpenses, onValueChange = {otherExpenses = it }, label = {Text("Other expenses")})
            Spacer(modifier=  Modifier.height(8.dp))
            Button(onClick = {
                val userInfo = RegisterInfo(
                    userName = userName,
                    houseRent = rent.toDoubleOrNull() ?: 0.0,
                    transport = transport.toDoubleOrNull() ?: 0.0,
                    debts = debts.toDoubleOrNull() ?: 0.0,
                    otherExpenses = otherExpenses.toDoubleOrNull() ?: 0.0
                )
                viewModel.saveRegisterInfo(userInfo)
                if(viewModel.fireStoreIsSuccess){
                    navController.navigate(Routes.DASHBOARD_ROUTE)
                }else{
                    scope.launch {
                        snackBarHostState.showSnackbar("Try again")
                    }
                }
            }) {
                Text("Good to go!")
            }
        }
    }
}

