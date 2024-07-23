package xyz.sina.clevercapitalist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import view.MainView
import view.SignUp
import xyz.sina.clevercapitalist.ui.theme.CleverCapitalistTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CleverCapitalistTheme {
                SignUp()
            }
        }
    }
}

