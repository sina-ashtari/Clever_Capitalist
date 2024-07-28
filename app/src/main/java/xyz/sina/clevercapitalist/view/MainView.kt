package xyz.sina.clevercapitalist.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController

@Composable
fun MainView(navController: NavHostController) {
    Column(modifier = Modifier.fillMaxSize()){
        Row(modifier = Modifier.fillMaxWidth()){
            Text(text = "")
        }
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center){
            Column(modifier = Modifier.fillMaxHeight()){
                Button(onClick = { navController.navigate(Routes.SIGN_UP_ROUTE) }) {
                    Text(text = "Sign up")
                }
                Button(onClick = {navController.navigate(Routes.SIGN_IN_ROUTE)}){
                    Text(text = "Sign in")
                }
            }
        }
    }
}