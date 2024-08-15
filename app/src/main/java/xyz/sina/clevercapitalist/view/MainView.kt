package xyz.sina.clevercapitalist.view


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import xyz.sina.clevercapitalist.R

@Composable
fun MainView(navController: NavHostController) {
    Scaffold(
        backgroundColor = androidx.compose.material3.MaterialTheme.colorScheme.surface
    ) {innerPadding ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // image is maybe bad, but for now is okay
            Image(painter = painterResource(id = R.drawable.img_summary), contentDescription = "summary")
            Text(modifier = Modifier.padding(16.dp), style = TextStyle(fontSize = 20.sp), textAlign = TextAlign.Center,color = androidx.compose.material3.MaterialTheme.colorScheme.onBackground,text= "Change your money mindset")
            Text(modifier = Modifier.padding(16.dp), style = TextStyle(fontSize = 16.sp), textAlign = TextAlign.Center,color = androidx.compose.material3.MaterialTheme.colorScheme.onBackground,text= "Enjoy guilt-free spending and effortless saving with a friendly, flexible method for managing your finances.")
            Spacer(modifier = Modifier.height(20.dp))
            Button(modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp),colors = ButtonDefaults.buttonColors(backgroundColor = androidx.compose.material3.MaterialTheme.colorScheme.primary),onClick = {navController.navigate(Routes.SIGN_UP_ROUTE)}) {
                Text(color = androidx.compose.material3.MaterialTheme.colorScheme.onPrimary,text = "Try Clever Capitalist for free")
            }
            Button(modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp),colors = ButtonDefaults.buttonColors(backgroundColor = androidx.compose.material3.MaterialTheme.colorScheme.primary),
                onClick = {navController.navigate(Routes.SIGN_IN_ROUTE)},
                ) {
                Text(color = androidx.compose.material3.MaterialTheme.colorScheme.onPrimary,text = "I Already Have an Account")
            }
        }
    }
}