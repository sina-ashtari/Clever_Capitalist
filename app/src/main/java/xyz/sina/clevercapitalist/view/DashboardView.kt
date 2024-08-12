package xyz.sina.clevercapitalist.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import co.yml.charts.common.model.PlotType
import co.yml.charts.ui.piechart.charts.PieChart
import co.yml.charts.ui.piechart.models.PieChartConfig
import co.yml.charts.ui.piechart.models.PieChartData
import com.google.firebase.auth.FirebaseAuth
import xyz.sina.clevercapitalist.model.RegisterInfo
import xyz.sina.clevercapitalist.viewModel.DashboardViewModel
import xyz.sina.clevercapitalist.viewModel.RealmViewModel.RealmViewModel


@Composable
fun Dashboard(navController: NavHostController) {

    val viewModel : DashboardViewModel = hiltViewModel()

    val data = viewModel.data.collectAsState()


    UserUI(data,navController)

}

@Composable
fun UserUI(data: State<List<RegisterInfo>>, navController: NavHostController) {

    val realmViewModel : RealmViewModel = hiltViewModel()
    val realmRegisterInfo = realmViewModel.realmRegisterInfo.collectAsState()

    val userName = remember {
        mutableStateOf("")
    }
    var leftOver = 0f
    var debts = 0f
    var trasport = 0f
    var houseRent = 0f
    var otherExpenses = 0f

    Scaffold(modifier = Modifier,
        topBar = {
            TopAppBar( title = {DropDownMenu(navController = navController)})
        }
    ){innerPadding ->

        Column(modifier = Modifier
            .padding(innerPadding)
            .verticalScroll(rememberScrollState())) {
            if (data.value.isEmpty()){
                data.value.forEach { item ->
                    userName.value = item.userName
                    debts = item.debts.toFloat()
                    trasport = item.transport.toFloat()
                    houseRent = item.houseRent.toFloat()
                    otherExpenses = item.otherExpenses.toFloat()
                    leftOver = if(item.salary.toFloat() - (debts + trasport + houseRent + otherExpenses) < 0 ) 0f else (item.salary.toFloat() - (debts + trasport + houseRent + otherExpenses))
                }
            }else{
                realmRegisterInfo.value.forEach{ item ->
                    userName.value = item.dbUserName
                    debts = item.dbDebts.toFloat()
                    trasport = item.dbTransport.toFloat()
                    houseRent = item.dbHouseRent.toFloat()
                    otherExpenses = item.dbOtherExpenses.toFloat()
                    leftOver = if(item.dbSalary.toFloat() - (debts + trasport + houseRent + otherExpenses) < 0 ) 0f else (item.dbSalary.toFloat() - (debts + trasport + houseRent + otherExpenses))
                }
            }


            val pieChartData = PieChartData(
                plotType = PlotType.Pie,
                slices = listOf(
                    PieChartData.Slice("Debts", debts, Color(0xFF333333)),
                    PieChartData.Slice("Transport", trasport, Color(0xFF666a86)),
                    PieChartData.Slice("House rent", houseRent, Color(0xFF95B8D1)),
                    PieChartData.Slice("Other expenses", otherExpenses, Color(0xFFF53844)),
                    PieChartData.Slice("Left over", leftOver, Color.Yellow)
                )
            )
            val pieChartConfig = PieChartConfig(
                isAnimationEnable = true,
                showSliceLabels = true,
                animationDuration = 1500,
                backgroundColor = Color.Transparent
            )
            // delete this shit

            Box(modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding) ,contentAlignment = Alignment.Center){
                Column{
                    Row(modifier = Modifier.fillMaxWidth()){
                        Box(modifier = Modifier
                            .width(30.dp)
                            .height(30.dp)
                            .background(Color(0xFF333333)))

                        Text(text = "Debts")
                        Box(modifier = Modifier
                            .width(30.dp)
                            .height(30.dp)
                            .background(Color(0xFF666a86)))

                        Text(text = "Transport")
                        Box(modifier = Modifier
                            .width(30.dp)
                            .height(30.dp)
                            .background(Color(0xFF95B8D1)))
                        Text(text = "House rent")
                    }
                    Row(modifier = Modifier.fillMaxWidth()){
                        Box(modifier = Modifier
                            .width(30.dp)
                            .height(30.dp)
                            .background(Color(0xFFF53844)))

                        Text(text = "Other expenses")
                        Box(modifier = Modifier
                            .width(30.dp)
                            .height(30.dp)
                            .background(Color.Yellow))

                        Text(text = "Left over")

                    }
                }

            }

            PieChart(modifier = Modifier
                .width(400.dp)
                .height(400.dp), pieChartData = pieChartData , pieChartConfig = pieChartConfig )
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