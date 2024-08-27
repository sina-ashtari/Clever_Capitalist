package xyz.sina.clevercapitalist.view

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import co.yml.charts.common.model.PlotType
import co.yml.charts.ui.piechart.charts.PieChart
import co.yml.charts.ui.piechart.models.PieChartConfig
import co.yml.charts.ui.piechart.models.PieChartData
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import xyz.sina.clevercapitalist.model.FinancialGoals
import xyz.sina.clevercapitalist.model.RegisterInfo
import xyz.sina.clevercapitalist.viewModel.DashboardViewModel
import xyz.sina.clevercapitalist.viewModel.RealmViewModel.RealmViewModel

@Composable
fun Dashboard(navController: NavHostController) {

    val viewModel : DashboardViewModel = hiltViewModel()
    val data = viewModel.data.collectAsState()
    val goalPairs by viewModel.goalsData.observeAsState(emptyList())
    val error = viewModel.error

    UserUI(data,goalPairs,navController,error)
}

@Composable
fun UserUI(
    data: State<List<RegisterInfo>>,
    goalPairs: List<FinancialGoals>,
    navController: NavHostController,
    error: String
) {

    val snackBarHostState = remember {
        SnackbarHostState()
    }
    
    val snackBarScope = rememberCoroutineScope()

    val realmViewModel : RealmViewModel = hiltViewModel()
    realmViewModel.loadRegisterInfo()
    val realmRegisterInfo = realmViewModel.realmRegisterInfo.collectAsState()

    val userName = remember { mutableStateOf("") }
    var leftOver = 0f
    var debts = 0f
    var transport = 0f
    var houseRent = 0f
    var otherExpenses = 0f

    val density = LocalDensity.current

    var monthlyVisibleTab by remember { mutableStateOf(false) }
    var graphVisibleTab by remember { mutableStateOf(false) }
    var goalsVisibleTab by remember { mutableStateOf(false) }

    Scaffold(modifier = Modifier,
        topBar = {
            TopAppBar(backgroundColor = MaterialTheme.colorScheme.surfaceTint) {
                Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End){
                    Spacer(modifier = Modifier.weight(1f))
                    DropDownMenu(navController = navController)
                }
            }
        },
        snackbarHost = { SnackbarHost(hostState = snackBarHostState)}
    ){innerPadding ->

        Column(modifier = Modifier
            .padding(innerPadding)
            .verticalScroll(rememberScrollState())
            .background(
                MaterialTheme.colorScheme.background
            )) {
            if (data.value.isNotEmpty()){
                data.value.forEach { item ->
                    userName.value = item.userName
                    debts = item.debts.toFloat()
                    transport = item.transport.toFloat()
                    houseRent = item.houseRent.toFloat()
                    otherExpenses = item.otherExpenses.toFloat()
                    leftOver = if(item.salary.toFloat() - (debts + transport + houseRent + otherExpenses) < 0 ) 0f else (item.salary.toFloat() - (debts + transport + houseRent + otherExpenses))
                }
            }else{
                if(realmRegisterInfo.value.isNotEmpty()){
                    realmRegisterInfo.value.forEach{ item ->
                        userName.value = item.dbUserName
                        debts = item.dbDebts.toFloat()
                        transport = item.dbTransport.toFloat()
                        houseRent = item.dbHouseRent.toFloat()
                        otherExpenses = item.dbOtherExpenses.toFloat()
                        leftOver = if(item.dbSalary.toFloat() - (debts + transport + houseRent + otherExpenses) < 0 ) 0f else (item.dbSalary.toFloat() - (debts + transport + houseRent + otherExpenses))
                    }
                }else{
                    LaunchedEffect(Unit) {
                        snackBarScope.launch { snackBarHostState.showSnackbar(error) }
                    }
                }

            }

            Column() {
                if(leftOver >= 0.0){
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                        .clip(shape = RoundedCornerShape(8.dp))
                        .background(color = MaterialTheme.colorScheme.secondary)){
                        Row(modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)){
                            Column {
                                Text(color = MaterialTheme.colorScheme.onSecondary, text = "$ $leftOver", fontSize = 20.sp, style = TextStyle(fontWeight = FontWeight.SemiBold))
                                Text(color = MaterialTheme.colorScheme.onSecondary, text = "Ready to assign")
                            }
                            Spacer(modifier = Modifier.weight(1f))
                            Column {
                                Button(colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.secondaryContainer),onClick = {  }) {
                                    Text(color = MaterialTheme.colorScheme.onSecondaryContainer,text = "Assign Money")
                                }
                            }
                        }
                    }
                }

                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clickable { monthlyVisibleTab = !monthlyVisibleTab }){
                    Icon(modifier = Modifier.padding(start = 16.dp),imageVector = if(monthlyVisibleTab) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown, contentDescription = null)
                    Text(color = MaterialTheme.colorScheme.onBackground ,text ="Monthly", fontSize = 20.sp)
                }
                AnimatedVisibility(
                    visible = monthlyVisibleTab,
                    enter = slideInVertically {
                        with(density){ -40.dp.roundToPx() }
                    } + expandVertically(expandFrom = Alignment.Bottom) + fadeIn(initialAlpha = 0.3f),
                    exit = slideOutVertically ()  + shrinkVertically() + fadeOut()
                    ) {

                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .background(color = MaterialTheme.colorScheme.surfaceBright)){
                        Row(modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)){
                            Column {
                                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically){
                                    //Icon(imageVector =  , contentDescription = null ) add house icon
                                    Text(text = "Mortgage")
                                    Spacer(modifier = Modifier.weight(1f))
                                    Text(modifier = Modifier
                                        .background(
                                            color = MaterialTheme.colorScheme.primary,
                                            shape = RoundedCornerShape(8.dp)
                                        ).padding(2.dp),text = "$$houseRent", color = MaterialTheme.colorScheme.onPrimary)
                                }

                            }
                        }
                        Row(modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)){
                            Column {
                                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically){
                                    //Icon(imageVector =  , contentDescription = null ) add transit icon
                                    Text(text = "transport")
                                    Spacer(modifier = Modifier.weight(1f))
                                    Text(modifier = Modifier
                                        .background(
                                            color = MaterialTheme.colorScheme.primary,
                                            shape = RoundedCornerShape(8.dp)
                                        ).padding(2.dp),text = "$$transport", color = MaterialTheme.colorScheme.onPrimary)
                                }

                            }
                        }
                        Row(modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)){
                            Column {
                                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically){
                                    //Icon(imageVector =  , contentDescription = null ) add house icon
                                    Text(text = "debts")
                                    Spacer(modifier = Modifier.weight(1f))
                                    Text(modifier = Modifier
                                        .background(
                                            color = MaterialTheme.colorScheme.primary,
                                            shape = RoundedCornerShape(8.dp)
                                        ).padding(2.dp),text = "$$debts", color = MaterialTheme.colorScheme.onPrimary)
                                }
                            }
                        }
                        Row(modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)){
                            Column {
                                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically){
                                    //Icon(imageVector =  , contentDescription = null ) add house icon
                                    Text(text = "Other Expenses")
                                    Spacer(modifier = Modifier.weight(1f))
                                    Text(modifier = Modifier
                                        .background(
                                            color = MaterialTheme.colorScheme.primary,
                                            shape = RoundedCornerShape(8.dp)
                                        ).padding(2.dp),text = "$$otherExpenses", color = MaterialTheme.colorScheme.onPrimary)
                                }
                            }
                        }
                    }

                }
            }

            Column {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clickable {
                        goalsVisibleTab = !goalsVisibleTab
                    }){
                    Icon(modifier = Modifier.padding(start = 16.dp),imageVector = if(goalsVisibleTab) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown, contentDescription = null)
                    Text(color = MaterialTheme.colorScheme.onBackground ,text ="Goals", fontSize = 20.sp)
                }
                AnimatedVisibility(
                    visible = goalsVisibleTab,
                    enter = slideInVertically {
                        with(density){ -40.dp.roundToPx() }
                    } + expandVertically(expandFrom = Alignment.Bottom) + fadeIn(initialAlpha = 0.3f),
                    exit = slideOutVertically ()  + shrinkVertically() + fadeOut()
                ) {
                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surfaceBright)
                        .padding(8.dp)){
                        goalPairs.forEachIndexed { _, pair ->
                            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically){
                                Text(color = MaterialTheme.colorScheme.onBackground,text = pair.goal)
                                Spacer(modifier = Modifier.weight(1f))
                                Text(modifier = Modifier
                                    .background(
                                        color = MaterialTheme.colorScheme.primary,
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    .padding(2.dp),color = MaterialTheme.colorScheme.onPrimary,text = "$ ${pair.moneyForGoal}")
                            }

                            LinearProgressIndicator(progress = pair.assignedMoney.toFloat() / pair.moneyForGoal.toFloat(), color = MaterialTheme.colorScheme.secondary)
                        }
                    }
                }
            }

            val pieChartData = PieChartData(
                plotType = PlotType.Pie,
                slices = listOf(
                    PieChartData.Slice("Debts", debts, Color(251, 97, 7)),
                    PieChartData.Slice("Transport", transport, Color(243, 222, 44)),
                    PieChartData.Slice("House rent", houseRent, Color(124, 181, 24)),
                    PieChartData.Slice("Other expenses", otherExpenses, Color(92, 128, 1)),
                    PieChartData.Slice("Left over", leftOver, Color(251, 176, 45))
                )
            )
            val pieChartConfig = PieChartConfig(
                isAnimationEnable = true,
                showSliceLabels = true,
                animationDuration = 1500,
                backgroundColor = MaterialTheme.colorScheme.surfaceBright,
                sliceLabelTextColor = Color.Blue
            )

            Column {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clickable { graphVisibleTab = !graphVisibleTab }){
                    Icon(modifier = Modifier.padding(start = 16.dp),imageVector = if(graphVisibleTab) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown, contentDescription = null)
                    Text(color = MaterialTheme.colorScheme.onBackground ,text ="Graph", fontSize = 20.sp)
                }
                AnimatedVisibility(
                    visible = graphVisibleTab,
                    enter = slideInVertically {
                        with(density){ -40.dp.roundToPx() }
                    } + expandVertically(expandFrom = Alignment.Bottom) + fadeIn(initialAlpha = 0.3f),
                    exit = slideOutVertically ()  + shrinkVertically() + fadeOut()
                ) {
                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surfaceBright)
                        .padding(8.dp)){
                        Column(){
                            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically){
                                Box(modifier = Modifier
                                    .width(30.dp)
                                    .height(30.dp)
                                    .background(Color(251, 97, 7)))

                                Text(modifier = Modifier.weight(1f), text = "Debts")
                                Box(modifier = Modifier
                                    .width(30.dp)
                                    .height(30.dp)
                                    .background(Color(243, 222, 44)))

                                Text(modifier = Modifier.weight(1f), text = "Transport")
                                Box(modifier = Modifier
                                    .width(30.dp)
                                    .height(30.dp)
                                    .background(Color(124, 181, 24)))
                                Text(modifier = Modifier.weight(1f), text = "House rent")
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically){
                                Box(modifier = Modifier
                                    .width(30.dp)
                                    .height(30.dp)
                                    .background(Color(92, 128, 1)))

                                Text(modifier = Modifier.weight(1f),text = "Other expenses")
                                Box(modifier = Modifier
                                    .width(30.dp)
                                    .height(30.dp)
                                    .background(Color(251, 176, 45)))

                                Text(modifier = Modifier.weight(1f), text = "Left over")

                            }
                        }
                        PieChart(modifier = Modifier
                            .width(400.dp)
                            .height(400.dp), pieChartData = pieChartData , pieChartConfig = pieChartConfig )
                    }
                }
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
        Icon(imageVector = Icons.Default.MoreVert, contentDescription = "more",tint = MaterialTheme.colorScheme.onSurface)
    }
    DropdownMenu(expanded = expanded, onDismissRequest = {expanded = false}) {
        DropdownMenuItem(onClick = {
            openAlertDialog.value = true
            expanded = false
        }) {
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
                Button(onClick = {
                    openAlertDialog.value = false
                    FirebaseAuth.getInstance().signOut()
                    navController.navigate(Routes.MAIN_ROUTE)
                },
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)) {
                    Text(color = MaterialTheme.colorScheme.onPrimary , text = "Logout")
                }


                            },
            title = {Text("Log out")},
            icon = { Icon(imageVector = Icons.Default.Info, contentDescription = null)},
            text = {
                Row(horizontalArrangement = Arrangement.Center){
                    Text(color = MaterialTheme.colorScheme.onBackground,text = "Do you really want to log out?")
                }
            }
        
            )
    }
}