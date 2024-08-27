package xyz.sina.clevercapitalist.viewModel.dashboard

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import xyz.sina.clevercapitalist.model.Authentication.FirestoreRepository
import xyz.sina.clevercapitalist.model.FinancialGoals
import xyz.sina.clevercapitalist.model.RegisterInfo
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val repository: FirestoreRepository
): ViewModel(){

    private val _data = MutableStateFlow<List<RegisterInfo>>(emptyList())
    val data : StateFlow<List<RegisterInfo>> = _data

    private val _goalsData = MutableLiveData<List<FinancialGoals>>()
    val goalsData : LiveData<List<FinancialGoals>> = _goalsData

    private var _error by mutableStateOf("")
    var error = _error

    init {
        fetchData()
    }

    private fun fetchData(){
        val currentUser = FirebaseAuth.getInstance().currentUser
        val uid = currentUser?.uid

        if(uid != null){
            try {
                viewModelScope.launch {
                    val result = repository.getDataFromFireStore(uid)
                    _data.value = result
                }
                viewModelScope.launch {
                    val result = repository.getGoalsFromFireStore(uid)
                    _goalsData.value = result
                }
            }catch (e:Exception){
                _error = e.message.toString()
                error = _error
            }
        }else{
            _data.value = emptyList()
            error = "Please Re-login again."
        }
    }

}