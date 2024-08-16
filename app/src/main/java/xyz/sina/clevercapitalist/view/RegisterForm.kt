package xyz.sina.clevercapitalist.view

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.firebase.auth.UserInfo
import kotlinx.coroutines.launch
import xyz.sina.clevercapitalist.model.RealmRegisterInfo
import xyz.sina.clevercapitalist.model.RegisterInfo
import xyz.sina.clevercapitalist.viewModel.RealmViewModel.RealmViewModel
import xyz.sina.clevercapitalist.viewModel.registerFormViewModel.RegisterViewModel


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun RegisterForm(navController: NavHostController){

    val viewModel : RegisterViewModel = hiltViewModel()
    val realmViewModel : RealmViewModel = hiltViewModel()

    val snackBarHostState = remember {SnackbarHostState()}
    val scope = rememberCoroutineScope()

    var userName by remember { mutableStateOf("") }
    var salary by remember { mutableStateOf("") }
    var rent by remember { mutableStateOf("") }
    var transport by remember { mutableStateOf("") }
    var debts by remember { mutableStateOf("") }
    var otherExpenses by remember { mutableStateOf("") }

    Scaffold(snackbarHost = {
        SnackbarHost(hostState = snackBarHostState)
    },
        backgroundColor = MaterialTheme.colorScheme.background
    ){ innerPadding ->

        Column(modifier = Modifier.padding(16.dp).fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier=  Modifier.weight(1f))
            Text(textAlign = TextAlign.Center ,color = MaterialTheme.colorScheme.onBackground ,text="Please enter your name")
            OutlinedTextField(modifier = Modifier.fillMaxWidth(), maxLines = 1 ,value = userName, onValueChange = {userName = it }, label = {Text(color = MaterialTheme.colorScheme.onBackground ,text="Name")})
            Spacer(modifier=  Modifier.weight(1f))
            Text(textAlign = TextAlign.Center ,color = MaterialTheme.colorScheme.onBackground ,text="Please enter your monthly salary")
            OutlinedTextField(modifier = Modifier.fillMaxWidth() ,value = salary, onValueChange = {salary = it }, label = {Text(color = MaterialTheme.colorScheme.onBackground ,text="Monthly Salary")})
            Spacer(modifier=  Modifier.weight(1f))
            // IDK now how to implement these in country which everything changes in seconds
            Text(textAlign = TextAlign.Center ,color = MaterialTheme.colorScheme.onBackground ,text="How about your house ?")
            OutlinedTextField(modifier = Modifier.fillMaxWidth() ,value = rent, onValueChange = {rent = it }, label = {Text(color = MaterialTheme.colorScheme.onBackground ,text="Monthly Rent")})
            Spacer(modifier=  Modifier.weight(1f))
            Text(textAlign = TextAlign.Center ,color = MaterialTheme.colorScheme.onBackground ,text="How much money do you spend to get around?")
            OutlinedTextField(modifier = Modifier.fillMaxWidth() ,value = transport, onValueChange = {transport = it }, label = {Text(color = MaterialTheme.colorScheme.onBackground ,text="Monthly transport")})
            Spacer(modifier=  Modifier.weight(1f))
            Text(textAlign = TextAlign.Center ,color = MaterialTheme.colorScheme.onBackground ,text="Do you currently have any debt?")
            OutlinedTextField(modifier = Modifier.fillMaxWidth() ,value = debts, onValueChange = {debts = it }, label = {Text(color = MaterialTheme.colorScheme.onBackground ,text="debts")})
            Spacer(modifier=  Modifier.weight(1f))
            Text(textAlign = TextAlign.Center ,color = MaterialTheme.colorScheme.onBackground ,text="How much money do you spend on other things like Internet, Phone, Groceries , etc.?")
            OutlinedTextField(modifier = Modifier.fillMaxWidth() ,value = otherExpenses, onValueChange = {otherExpenses = it }, label = {Text(color = MaterialTheme.colorScheme.onBackground ,text="Other expenses")})
            Spacer(modifier=  Modifier.weight(1f))
            Button(onClick = {
                val userInfo = RegisterInfo(
                    userName = userName ,
                    salary = salary.toDoubleOrNull() ?: 0.0,
                    houseRent = rent.toDoubleOrNull() ?: 0.0,
                    transport = transport.toDoubleOrNull() ?: 0.0,
                    debts = debts.toDoubleOrNull() ?: 0.0,
                    otherExpenses = otherExpenses.toDoubleOrNull() ?: 0.0
                )

                scope.launch {
                    val realmRegisterInfo = RealmRegisterInfo().apply {
                        dbUserName = userName
                        dbSalary = salary.toDoubleOrNull() ?: 0.0
                        dbHouseRent = rent.toDoubleOrNull() ?: 0.0
                        dbTransport = transport.toDoubleOrNull() ?: 0.0
                        dbDebts = debts.toDoubleOrNull() ?: 0.0
                        dbOtherExpenses = otherExpenses.toDoubleOrNull() ?: 0.0
                    }
                    realmViewModel.addRegisterInfo(realmRegisterInfo)
                }

                viewModel.saveRegisterInfo(userInfo)
                navController.navigate(Routes.DASHBOARD_ROUTE)
            }
            , colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
            ) {
                Text(color = MaterialTheme.colorScheme.onPrimary ,text="Good to go!")
            }
            Spacer(modifier=  Modifier.weight(1f))
        }
    }
}

