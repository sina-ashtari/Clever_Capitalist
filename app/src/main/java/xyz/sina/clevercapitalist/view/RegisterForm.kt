package xyz.sina.clevercapitalist.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RegisterForm(){
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState {5}

    Scaffold(){ innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            HorizontalPager(state = pagerState) { page: Int ->  
                when(page){
                    0 -> {NameGetter()}
                    1 -> {HomeStatus()}
                    2 -> {}
                    3 -> {}
                    4 -> {}
                }
            }

            if(pagerState.currentPage < pagerState.currentPage -1){
                Button(onClick = { scope.launch { pagerState.animateScrollToPage(pagerState.currentPage+1) } }) {
                    Text(text = "Next")
                }
            }else{
                Button(onClick = {}) {
                    Text("Good to go!")
                }
            }
        }
    }
}


@Composable
fun NameGetter() {
    val name = remember { mutableStateOf("") }
    Column {

        Text("What should we call you, clever capitalist ?")
        OutlinedTextField(value = name.value, onValueChange = {name.value = it} )

    }
}
@Composable
fun HomeStatus(){
    // i should do this with repeat of course ..
    val checkState = remember { mutableStateOf(false) }
    val checkState1 = remember { mutableStateOf(false) }
    val checkState2 = remember { mutableStateOf(false) }

    Column {
        Text("Tell us about your home")

        Button(onClick = {
            checkState.value = true
            checkState1.value = false
            checkState2.value = false
        }, border = BorderStroke(1.dp , if (checkState.value) Color.Red else Color.Black)) {
            Text(text = "I rent")
            Checkbox(checked = checkState.value, onCheckedChange = {} )
        }
        Button(onClick = {
            checkState.value = false
            checkState1.value = true
            checkState2.value = false
        }, border = BorderStroke(1.dp , if (checkState1.value) Color.Red else Color.Black)) {
            Text(text = "I own")
            Checkbox(checked = checkState1.value, onCheckedChange = {} )
        }
        Button(onClick = {
            checkState.value = false
            checkState1.value = false
            checkState2.value = true
        }, border = BorderStroke(1.dp , if (checkState2.value) Color.Red else Color.Black)) {
            Text(text = "Other")
            Checkbox(checked = checkState2.value, onCheckedChange = {} )
        }

    }
}
@Composable
fun TravelStatus(){
    Column {

    }
}