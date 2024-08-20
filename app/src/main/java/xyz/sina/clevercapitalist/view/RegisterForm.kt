package xyz.sina.clevercapitalist.view

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import xyz.sina.clevercapitalist.model.RealmRegisterInfo
import xyz.sina.clevercapitalist.model.RegisterInfo
import xyz.sina.clevercapitalist.viewModel.RealmViewModel.RealmViewModel
import xyz.sina.clevercapitalist.viewModel.registerFormViewModel.RegisterViewModel

data class FinancialGoals(
    var goal : TextFieldValue = TextFieldValue(""),
    var moneyForGoal : TextFieldValue = TextFieldValue("")

)

fun FinancialGoals.toMap(): Map<String, Any> {
    return mapOf(
        "goal" to goal,
        "moneyForGoal" to moneyForGoal
    )
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun RegisterForm(navController: NavHostController){

    val viewModel : RegisterViewModel = hiltViewModel()
    val realmViewModel : RealmViewModel = hiltViewModel()

    val snackBarHostState = remember {SnackbarHostState()}
    val scope = rememberCoroutineScope()

    val financialGoalsList = remember { mutableStateListOf<FinancialGoals>() }

    var userName by remember { mutableStateOf("") }
    var salary by remember { mutableStateOf("") }
    var rent by remember { mutableStateOf("") }
    var transport by remember { mutableStateOf("") }
    var debts by remember { mutableStateOf("") }
    var otherExpenses by remember { mutableStateOf("") }

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

                    viewModel.saveRegisterInfo(userInfo,financialGoalsList)
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
            OutlinedTextField(modifier = Modifier.fillMaxWidth(), maxLines = 1 ,value = userName, onValueChange = {userName = it }, label = {Text(color = MaterialTheme.colorScheme.onBackground ,text="Name")})
            Text(textAlign = TextAlign.Center ,color = MaterialTheme.colorScheme.onBackground ,text="Please enter your monthly salary")
            OutlinedTextField(modifier = Modifier.fillMaxWidth() ,value = salary, onValueChange = {salary = it }, label = {Text(color = MaterialTheme.colorScheme.onBackground ,text="Monthly Salary")})
            // IDK now how to implement these in country which everything changes in seconds
            Text(textAlign = TextAlign.Center ,color = MaterialTheme.colorScheme.onBackground ,text="How about your house ?")
            OutlinedTextField(modifier = Modifier.fillMaxWidth() ,value = rent, onValueChange = {rent = it }, label = {Text(color = MaterialTheme.colorScheme.onBackground ,text="Monthly Rent")})
            Text(textAlign = TextAlign.Center ,color = MaterialTheme.colorScheme.onBackground ,text="How much money do you spend to get around?")
            OutlinedTextField(modifier = Modifier.fillMaxWidth() ,value = transport, onValueChange = {transport = it }, label = {Text(color = MaterialTheme.colorScheme.onBackground ,text="Monthly transport")})
            Text(textAlign = TextAlign.Center ,color = MaterialTheme.colorScheme.onBackground ,text="Do you currently have any debt?")
            OutlinedTextField(modifier = Modifier.fillMaxWidth() ,value = debts, onValueChange = {debts = it }, label = {Text(color = MaterialTheme.colorScheme.onBackground ,text="debts")})
            Text(textAlign = TextAlign.Center ,color = MaterialTheme.colorScheme.onBackground ,text="How much money do you spend on other things like Internet, Phone, Groceries , etc.?")
            OutlinedTextField(modifier = Modifier.fillMaxWidth() ,value = otherExpenses, onValueChange = {otherExpenses = it }, label = {Text(color = MaterialTheme.colorScheme.onBackground ,text="Other expenses")})
            Button(modifier = Modifier.padding(top = 16.dp, bottom = 16.dp),colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),
                onClick = {
                    financialGoalsList.add(FinancialGoals())
                }){
                Text(color = MaterialTheme.colorScheme.onPrimary,text = "Do you have any financial goal? if do, click me!")
            }
            financialGoalsList.forEachIndexed { index , financialGoals ->
                Row(modifier = Modifier.fillMaxWidth() , verticalAlignment = Alignment.CenterVertically){
                    OutlinedTextField(modifier = Modifier.weight(1f),value = financialGoals.goal, label = {Text(color = MaterialTheme.colorScheme.onBackground, text = "Goal ${index+1}")} ,onValueChange = {newValue -> financialGoalsList[index] = financialGoalsList[index].copy(goal = newValue)})
                    Spacer(modifier = Modifier.width(14.dp))
                    OutlinedTextField(modifier = Modifier.weight(1f),value = financialGoals.moneyForGoal, label = {Text(color = MaterialTheme.colorScheme.onBackground, text = "Money")},onValueChange = {newValue -> financialGoalsList[index] = financialGoalsList[index].copy(moneyForGoal = newValue)})
                    IconButton(onClick = {
                        financialGoalsList.removeAt(index)
                    }) {
                        Icon(imageVector = Icons.Default.Clear,tint  = MaterialTheme.colorScheme.onBackground, contentDescription = null)
                    }
                }
            }
        }
    }
}

