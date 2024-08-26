package xyz.sina.clevercapitalist.view.authenticationView

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import xyz.sina.clevercapitalist.R
import xyz.sina.clevercapitalist.view.Routes
import xyz.sina.clevercapitalist.viewModel.authentication.GISViewModel
import xyz.sina.clevercapitalist.viewModel.authentication.SignUpViewModel

@Composable
fun SignUpView(navController: NavHostController) {

    val viewModel : SignUpViewModel = hiltViewModel()
    val gisViewModel : GISViewModel = hiltViewModel()

    val launcher  = rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) {result ->
        gisViewModel.handleOneTapResult(result.data)
    }

    val signUpState by viewModel.signUpState.observeAsState()
    val gisState by gisViewModel.gisState



    val passwordError by viewModel.passwordError.collectAsStateWithLifecycle()

    val snackBarHostState = remember {
        SnackbarHostState()
    }
    val scope = rememberCoroutineScope()

    Scaffold(snackbarHost = { SnackbarHost(hostState = snackBarHostState)},
        backgroundColor = MaterialTheme.colorScheme.background
    ) { innerPadding->
        Column (modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            Button(modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(MaterialTheme.colorScheme.primary),onClick = {gisViewModel.oneTapSignIn(launcher)}) {
                Row(modifier = Modifier.fillMaxWidth()){
                    Image(painter = painterResource(id = R.drawable.ic_google), contentDescription = "Google")
                    Text(text = "Continue with Google", color = MaterialTheme.colorScheme.onPrimary)
                }
            }
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically){
                HorizontalDivider(modifier = Modifier.weight(1f),thickness = 1.dp, color = MaterialTheme.colorScheme.onBackground)
                Text(modifier = Modifier.weight(1f),style =  TextStyle(textAlign = TextAlign.Center),color = MaterialTheme.colorScheme.onBackground ,text = "OR")
                HorizontalDivider(modifier = Modifier.weight(1f),thickness = 1.dp,color = MaterialTheme.colorScheme.onBackground)
            }

            OutlinedTextField(modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp), singleLine = true ,value = viewModel.email , onValueChange = viewModel::changeEmail, label= {Text(text = "Email", color = MaterialTheme.colorScheme.onBackground)})
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp), singleLine = true,value = viewModel.password , onValueChange = viewModel::changePassword, visualTransformation = PasswordVisualTransformation() ,label = {Text("Password", color = MaterialTheme.colorScheme.onBackground)}, isError = !passwordError.successful)
            Spacer(modifier = Modifier.height(16.dp))
            PasswordConditionRow(
                condition = "Minimum 8 characters",
                checked = passwordError.hasMinimum
            )
            PasswordConditionRow(
                condition = "Minimum 1 special character",
                checked = passwordError.hasSpecialChar
            )
            PasswordConditionRow(
                condition = "Minimum 1 capitalized letter",
                checked = passwordError.hasCapitalizedLetter
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp),colors = androidx.compose.material.ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),onClick = {if(!passwordError.successful){viewModel.signUp(email = viewModel.email, password = viewModel.password)}else{scope.launch { snackBarHostState.showSnackbar("Invalid password type") }} }) {
                Text(text = "Sign up", color = MaterialTheme.colorScheme.onPrimary)
            }

            gisState?.let{
                navController.navigate(Routes.REGISTER_FORM)
            }

            signUpState?.let {result->
                when{
                    result.isSuccess -> {
                        scope.launch { snackBarHostState.showSnackbar("worked") }
                        navController.navigate(Routes.REGISTER_FORM)
                    }
                    result.isFailure ->{
                        scope.launch { snackBarHostState.showSnackbar("try again") }
                    }
                }
            }

        }

    }
}

@Composable
fun PasswordConditionRow(
    condition : String ,
    checked : Boolean
){
    val color by animateColorAsState(
        targetValue = if ( checked ) Color.Green else MaterialTheme.colorScheme.error,
        label = "text color"
    )
    val icon = if ( checked ) Icons.Rounded.Check else Icons.Rounded.Close

    Row(modifier = Modifier.fillMaxWidth().padding(8.dp), verticalAlignment = Alignment.CenterVertically){
        Icon(imageVector = icon , contentDescription = null, tint = color)
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = condition, color = color)
    }
}