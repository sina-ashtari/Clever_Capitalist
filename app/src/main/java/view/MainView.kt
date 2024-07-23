package view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun MainView(){
    Column(modifier = Modifier.fillMaxSize()){
        Row(modifier = Modifier.fillMaxWidth()){
            Text(text = "")
        }
        Row(modifier = Modifier.fillMaxWidth()){
            Column(modifier = Modifier.fillMaxHeight()){
                Button(onClick = {  }) {
                    Text(text = "Sign up")
                }
                Button(onClick = {}){
                    Text(text = "Sign in")
                }
            }
        }
    }
}