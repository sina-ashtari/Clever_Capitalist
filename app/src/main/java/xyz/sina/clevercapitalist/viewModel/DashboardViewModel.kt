package xyz.sina.clevercapitalist.viewModel

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
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
            viewModelScope.launch {
                val result = repository.getGoalsFromFireStore(uid)
                _goalsData.value = result

            }

        }else{
            _data.value = emptyList()
        }
    }

}