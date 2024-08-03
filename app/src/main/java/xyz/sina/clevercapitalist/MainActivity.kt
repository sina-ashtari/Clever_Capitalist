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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import xyz.sina.clevercapitalist.view.MainView
import xyz.sina.clevercapitalist.view.Routes
import xyz.sina.clevercapitalist.view.authenticationView.SignInView
import xyz.sina.clevercapitalist.view.authenticationView.SignUpView
import xyz.sina.clevercapitalist.ui.theme.CleverCapitalistTheme
import xyz.sina.clevercapitalist.view.Dashboard
import xyz.sina.clevercapitalist.view.RegisterForm


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CleverCapitalistTheme {
                auth = Firebase.auth
                val currentUser = auth.currentUser
                val navController = rememberNavController()

                if(currentUser != null){
                    Navigation(navController = navController, Routes.DASHBOARD_ROUTE)
                }else{
                    Navigation(navController = navController,Routes.MAIN_ROUTE )
                }
            }
        }
    }
}

// need a welcome screen that goes for signUp or Sign in option, then to the Dashboard screen
// in signIn Screen we need a Forget password but not for now,

@Composable
fun Navigation(navController: NavHostController, startDestination : String){

    NavHost(navController = navController, startDestination = startDestination){
        composable(Routes.SIGN_IN_ROUTE){ SignInView(navController = navController) }
        composable(Routes.SIGN_UP_ROUTE){ SignUpView(navController = navController) }
        composable(Routes.MAIN_ROUTE){ MainView(navController = navController) }
        composable(Routes.REGISTER_FORM){ RegisterForm(navController = navController)}
        composable(Routes.DASHBOARD_ROUTE){ Dashboard()}
    }
}