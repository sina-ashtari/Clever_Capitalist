package xyz.sina.clevercapitalist.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import xyz.sina.clevercapitalist.model.RegisterInfo
import xyz.sina.clevercapitalist.viewModel.DashboardViewModel


@Composable
fun Dashboard(navController: NavHostController) {

    val viewModel : DashboardViewModel = hiltViewModel()
    val data = viewModel.data.collectAsState()
    UserUI(data,navController)

}

@Composable
fun UserUI(data: State<List<RegisterInfo>>,navController: NavHostController) {
    Scaffold(modifier = Modifier,
        topBar = {
            Row(horizontalArrangement = Arrangement.End){
                TopAppBar(title = { DropDownMenu(navController = navController) })
            }
        }
    ){innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            data.value.forEach { item ->
                Text(text = "hello ${item.userName}!")
                Text(text = "Your previous debts are : ${item.debts}")
                Text(text = "Your monthly transport fee : ${item.transport}")
                Text(text = "Your house rent is : ${item.houseRent}")
                Text(text = "Your other stuff : ${item.otherExpenses}" )
            }
        }
    }
}

@Composable
fun DropDownMenu(navController: NavHostController){
    var expanded by remember {
        mutableStateOf(false)
    }
    val openAlertDialog = remember {
        mutableStateOf(false)
    }

    IconButton(onClick = {expanded = !expanded}) {
        Icon(imageVector = Icons.Default.MoreVert, contentDescription = "more")
    }
    DropdownMenu(expanded = expanded, onDismissRequest = {expanded = false}) {
        DropdownMenuItem(onClick = {openAlertDialog.value = true}) {
            Text("Log out")
        }
    }

    if(openAlertDialog.value){
        ShowAlertDialog(openAlertDialog = openAlertDialog,navController)
    }
}
@Composable
fun ShowAlertDialog(openAlertDialog: MutableState<Boolean>,navController: NavHostController) {
    if(openAlertDialog.value){
        AlertDialog(onDismissRequest = {openAlertDialog.value = false} ,
            confirmButton = {
                    openAlertDialog.value = false
                    FirebaseAuth.getInstance().signOut()
                    navController.navigate(Routes.MAIN_ROUTE)
                            },
            title = {Text("Log out")},
            icon = { Icon(imageVector = Icons.Default.Info, contentDescription = null)},
            text = { Text(text = "Do you really want to log out?")}
        
            )
    }
}