package xyz.sina.clevercapitalist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import dagger.hilt.android.AndroidEntryPoint
import xyz.sina.clevercapitalist.view.MainView
import xyz.sina.clevercapitalist.view.Routes
import xyz.sina.clevercapitalist.view.SignInView
import xyz.sina.clevercapitalist.view.SignUpView
import xyz.sina.clevercapitalist.ui.theme.CleverCapitalistTheme


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CleverCapitalistTheme {

// probably we should have sharedPreference here as we check we logged in before our not.

                val navController = rememberNavController()
                Navigation(navController = navController)

            }
        }
    }
}

// need a welcome screen that goes for signUp or Sign in option, then to the Dashboard screen
// in signIn Screen we need a Forget password but not for now,
// should change startDestination if user logged in, but not now !

@Composable
fun Navigation(navController: NavHostController){

    NavHost(navController = navController, startDestination = Routes.MAIN_ROUTE){
        composable(Routes.SIGN_IN_ROUTE){ SignInView(navController = navController) }
        composable(Routes.SIGN_UP_ROUTE){ SignUpView(navController = navController) }
        composable(Routes.MAIN_ROUTE){ MainView(navController = navController) }
    }
}