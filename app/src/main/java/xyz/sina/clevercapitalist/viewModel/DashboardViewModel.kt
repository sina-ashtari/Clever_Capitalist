package xyz.sina.clevercapitalist.viewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.currentCompositionErrors
import androidx.compose.runtime.mutableStateOf

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import xyz.sina.clevercapitalist.model.FirestoreRepository
import xyz.sina.clevercapitalist.model.RegisterInfo
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val repository: FirestoreRepository
): ViewModel(){

    private val _data = MutableStateFlow<List<RegisterInfo>>(emptyList())
    val data : StateFlow<List<RegisterInfo>> = _data

    init {
        fetchData()
    }

    private fun fetchData(){
        val currentUser = FirebaseAuth.getInstance().currentUser
        val uid = currentUser?.uid
        if(uid != null){
            viewModelScope.launch {
                val result = repository.getDataFromFireStore(uid)
                _data.value = result
            }
        }else{
            _data.value = emptyList()
        }
    }

}