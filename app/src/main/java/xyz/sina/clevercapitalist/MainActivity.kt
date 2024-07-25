package xyz.sina.clevercapitalist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import viewModel.authentication.AuthViewModel
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import xyz.sina.clevercapitalist.ui.theme.CleverCapitalistTheme


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CleverCapitalistTheme {
                val navController = rememberNavController()
                val authViewModel: AuthViewModel = hiltViewModel()
                val isLoggedIn = authViewModel.authState.value

                NavHost(navController = navController, startDestination = if (isLoggedIn == true) "Main" else "login" ){
                    composable("login") {  }
                    composable("dashboard") {  }
                }
            }
        }
    }
}

