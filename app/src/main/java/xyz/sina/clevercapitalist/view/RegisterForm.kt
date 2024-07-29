package xyz.sina.clevercapitalist.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
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
                    2 -> {TravelStatus()}
                    3 -> {RegularlySpend()}
                    4 -> {}
                }
            }

            if(pagerState.currentPage < pagerState.pageCount -1){
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

    val btnTexts = listOf(
        "I rent",
        "I own",
        "Other"
    )
    var selectedIndex by remember { mutableStateOf(-1) }


    Column {
        Text("Tell us about your home")

        repeat(btnTexts.size){index ->
            Row(){
                Button(onClick = {selectedIndex = index}, border = BorderStroke(1.dp ,  if(selectedIndex == index) Color.Red else Color.Black)) {
                    Text(text = btnTexts[index])
                    Icon(imageVector = if(selectedIndex == index) Icons.Default.Check else Icons.Outlined.Check , contentDescription = null )
                }
            }

        }

    }
}
@Composable
fun TravelStatus(){

    val checkState = remember {
        mutableStateListOf(false,false,false,false,false,false)
    }
    val btnTexts = listOf(
        "Car",
        "Public transit",
        "Rideshare (Uber/Lyft/etc.)",
        "Motorcycle",
        "Bike",
        "Walk"
    )

    Column {
        Text("How do you get around?")
        repeat(btnTexts.size){index ->
            Row{
                Button(onClick = {}, border = BorderStroke(1.dp , if (checkState[index]) Color.Red else Color.Black)) {
                    Text(text = btnTexts[index])
                    Icon(imageVector = if(checkState[index]) Icons.Default.Check else Icons.Outlined.Check , contentDescription = null )
                }
            }
        }
    }
}
@Composable
fun RegularlySpend(){
    val checkState = remember {
        mutableStateListOf(false,false,false,false,false)
    }
    val btnTexts = listOf(
        "Groceries",
        "Phone",
        "Internet",
        "Personal Care",
        "Clothing"
    )
    Column {
        Text("Which of these do you regularly spend money on?")
        repeat(btnTexts.size){index ->
            Row{
                Button(onClick = {}, border = BorderStroke(1.dp , if (checkState[index]) Color.Red else Color.Black)) {
                    Text(text = btnTexts[index])
                    Icon(imageVector = if(checkState[index]) Icons.Default.Check else Icons.Outlined.Check , contentDescription = null )
                }
            }
        }
    }
}
@Composable
fun OverView(){
    Column {

    }
}