package xyz.sina.clevercapitalist.viewModel.registerFormViewModel

import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import xyz.sina.clevercapitalist.model.RegisterInfo
import javax.inject.Inject


@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val fireStore : FirebaseFirestore
): ViewModel() {
    fun saveRegisterInfo(registerInfo: RegisterInfo){
        viewModelScope.launch {
            try {
                fireStore.collection("register_info").add(registerInfo)
            }catch (e:Exception){

            }
        }
    }
}