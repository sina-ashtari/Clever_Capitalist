package xyz.sina.clevercapitalist.viewModel.registerFormViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

    private var _fireStoreIsSuccess = false
    private var _fireStoreException = ""
    val fireStoreException : String = _fireStoreException
    val fireStoreIsSuccess : Boolean = _fireStoreIsSuccess

    fun saveRegisterInfo(registerInfo: RegisterInfo){
        viewModelScope.launch {
            try {
                fireStore.collection("register_info").add(registerInfo).addOnSuccessListener {
                    _fireStoreIsSuccess = true
                }.addOnFailureListener { exception ->
                    _fireStoreIsSuccess = true
                    _fireStoreException = exception.toString()

                    Log.e("CHECK", "Error is : $exception")
                }
            }catch (e:Exception){
                Log.e("CHECK","Error is : $e")
            }
        }
    }
}