package xyz.sina.clevercapitalist.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import xyz.sina.clevercapitalist.viewModel.DashboardViewModel

@Composable
fun Dashboard(){
    val viewModel: DashboardViewModel = hiltViewModel()
    val userData = viewModel.userData.value

    LaunchedEffect(Unit) {
        viewModel.getDataFromFireStore()
    }

}

@Composable
fun UserUI(){
    Scaffold(){innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {

        }
    }
}
