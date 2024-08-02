package xyz.sina.clevercapitalist.viewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import xyz.sina.clevercapitalist.model.FirestoreRepository
import xyz.sina.clevercapitalist.model.RegisterInfo
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val repository: FirestoreRepository
): ViewModel(){

    private val _userData = mutableStateOf<Result<RegisterInfo>?>(null)
    val userData: State<Result<RegisterInfo>?> = _userData

    fun getDataFromFireStore(){
       viewModelScope.launch {
           repository.getUserData().collect{ result ->
               _userData.value = result

           }
       }
    }

}