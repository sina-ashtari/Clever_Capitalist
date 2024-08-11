package xyz.sina.clevercapitalist.viewModel.RealmViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import xyz.sina.clevercapitalist.model.RealmDB.RealmRepository
import xyz.sina.clevercapitalist.model.RealmRegisterInfo
import xyz.sina.clevercapitalist.model.RegisterInfo
import javax.inject.Inject

@HiltViewModel
class RealmViewModel @Inject constructor(
    private val repo : RealmRepository
): ViewModel(){
    private val _realmRegisterInfo = MutableStateFlow<List<RealmRegisterInfo>>(emptyList())
    val realmRegisterInfo : StateFlow<List<RealmRegisterInfo>> =  _realmRegisterInfo


    fun loadRegisterInfo(){
        viewModelScope.launch {
            val result = repo.getRegisterInfoFromDB()
            _realmRegisterInfo.value = result
        }
    }

    fun addRegisterInfo(registerInfo: RealmRegisterInfo){
        viewModelScope.launch {
            repo.addRegisterToDB(registerInfo)
        }
    }


}