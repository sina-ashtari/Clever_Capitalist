package xyz.sina.clevercapitalist.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import xyz.sina.clevercapitalist.model.RegisterInfo
import xyz.sina.clevercapitalist.viewModel.DashboardViewModel


@Composable
fun Dashboard(){

    val viewModel : DashboardViewModel = hiltViewModel()
    val data = viewModel.data.collectAsState()
    UserUI(data)

}

@Composable
fun UserUI(data: State<List<RegisterInfo>>) {
    Scaffold(){innerPadding ->
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
