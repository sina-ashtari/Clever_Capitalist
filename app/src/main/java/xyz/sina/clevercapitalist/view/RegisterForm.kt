package xyz.sina.clevercapitalist.view

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Button
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.ButtonDefaults
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Scaffold
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.SnackbarHost
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.SnackbarHostState
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
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

    val textFields by viewModel.goalsPair.observeAsState(emptyList())

    val snackBarHostState = remember {SnackbarHostState()}
    val scope = rememberCoroutineScope()



    Scaffold(snackbarHost = {
        SnackbarHost(hostState = snackBarHostState)
    },
        backgroundColor = MaterialTheme.colorScheme.background,
        bottomBar = {
            Row(modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
                .padding(16.dp), horizontalArrangement = Arrangement.Center){

                Button(onClick = {
                    val userInfo = RegisterInfo(
                        userName = viewModel.userName ,
                        salary = viewModel.salary.toDoubleOrNull() ?: 0.0,
                        houseRent = viewModel.rent.toDoubleOrNull() ?: 0.0,
                        transport = viewModel.transport.toDoubleOrNull() ?: 0.0,
                        debts = viewModel.debts.toDoubleOrNull() ?: 0.0,
                        otherExpenses = viewModel.otherExpenses.toDoubleOrNull() ?: 0.0
                    )

                    scope.launch {
                        val realmRegisterInfo = RealmRegisterInfo().apply {
                            dbUserName = viewModel.userName
                            dbSalary = viewModel.salary.toDoubleOrNull() ?: 0.0
                            dbHouseRent = viewModel.rent.toDoubleOrNull() ?: 0.0
                            dbTransport = viewModel.transport.toDoubleOrNull() ?: 0.0
                            dbDebts = viewModel.debts.toDoubleOrNull() ?: 0.0
                            dbOtherExpenses = viewModel.otherExpenses.toDoubleOrNull() ?: 0.0
                        }
                        realmViewModel.addRegisterInfo(realmRegisterInfo)
                    }

                    viewModel.saveRegisterInfo(userInfo)
                    viewModel.saveGoalsData()
                    navController.navigate(Routes.DASHBOARD_ROUTE)
                }
                    , colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
                ) {
                    Text(color = MaterialTheme.colorScheme.onPrimary ,text="Good to go!")
                }
            }
        }
    ){ _ ->

        Column(modifier = Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
            .fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            Text(textAlign = TextAlign.Center ,color = MaterialTheme.colorScheme.onBackground ,text="Please enter your name")
            OutlinedTextField(modifier = Modifier.fillMaxWidth(), maxLines = 1 ,value = viewModel.userName, onValueChange = viewModel::changeUserName , label = {Text(color = MaterialTheme.colorScheme.onBackground ,text="Name")})
            Text(textAlign = TextAlign.Center ,color = MaterialTheme.colorScheme.onBackground ,text="Please enter your monthly salary")
            OutlinedTextField(modifier = Modifier.fillMaxWidth(), keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number) ,value = viewModel.salary, onValueChange = viewModel::changeSalary , label = {Text(color = MaterialTheme.colorScheme.onBackground ,text="Monthly Salary")})
            // IDK now how to implement these in country which everything changes in seconds
            Text(textAlign = TextAlign.Center ,color = MaterialTheme.colorScheme.onBackground ,text="How about your house ?")
            OutlinedTextField(modifier = Modifier.fillMaxWidth(), keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number) ,value = viewModel.rent, onValueChange = viewModel::changeRent, label = {Text(color = MaterialTheme.colorScheme.onBackground ,text="Monthly Rent")})
            Text(textAlign = TextAlign.Center ,color = MaterialTheme.colorScheme.onBackground ,text="How much money do you spend to get around?")
            OutlinedTextField(modifier = Modifier.fillMaxWidth(), keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number) ,value = viewModel.transport, onValueChange = viewModel::changeTransport , label = {Text(color = MaterialTheme.colorScheme.onBackground ,text="Monthly transport")})
            Text(textAlign = TextAlign.Center ,color = MaterialTheme.colorScheme.onBackground ,text="Do you currently have any debt?")
            OutlinedTextField(modifier = Modifier.fillMaxWidth(), keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number) ,value = viewModel.debts, onValueChange = viewModel::changeDebts , label = {Text(color = MaterialTheme.colorScheme.onBackground ,text="debts")})
            Text(textAlign = TextAlign.Center ,color = MaterialTheme.colorScheme.onBackground ,text="How much money do you spend on other things like Internet, Phone, Groceries , etc.?")
            OutlinedTextField(modifier = Modifier.fillMaxWidth(), keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number) ,value = viewModel.otherExpenses, onValueChange = viewModel::changeOtherExpenses, label = {Text(color = MaterialTheme.colorScheme.onBackground ,text="Other expenses")})
            Box{
                // imma gonna add some more brighter background just for goal tab
                Column {
                    Button(modifier = Modifier.padding(top = 16.dp, bottom = 16.dp),colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),
                        onClick = {
                            viewModel.addTextField()
                        }){
                        Text(color = MaterialTheme.colorScheme.onPrimary,text = "Do you have any financial goal? if do, click me!")
                    }
                    textFields.forEachIndexed { index, pair ->
                        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically){
                            OutlinedTextField(modifier = Modifier.weight(1f),value = pair.first , onValueChange = {newValue -> viewModel.updateTextField(index , newValue, pair.second)},label = {Text(text="Goal ${index+1}", color = MaterialTheme.colorScheme.onBackground)})
                            Spacer(modifier = Modifier.width(8.dp))
                            OutlinedTextField(modifier = Modifier.weight(1f),keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),value = pair.second , onValueChange = {newValue -> viewModel.updateTextField(index , pair.first, newValue)},label = {Text(text="Money ${index+1}", color = MaterialTheme.colorScheme.onBackground)})
                            IconButton(onClick = { viewModel.deleteTextField(index) }) {
                                Icon(imageVector = Icons.Default.Clear, contentDescription = null, tint = MaterialTheme.colorScheme.surfaceTint)
                            }
                        }
                    }
                }
            }
        }
    }
}

