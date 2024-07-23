package view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SignUp(){
    Column (modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Button(onClick = {}) {
            Text(text = "Continue with Google")
        }
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)){
            HorizontalDivider(thickness = 1.dp)
            Text(text = "OR")
            HorizontalDivider(thickness = 1.dp)
        }
        OutlinedTextField(value = "Email", onValueChange = {})
        OutlinedTextField(value = "Password", onValueChange = {})
        OutlinedTextField(value = "Confirm password", onValueChange = {})
    }
}