package xyz.sina.clevercapitalist.viewModel.authentication

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SignInViewModel @Inject constructor(
    private val auth: FirebaseAuth
) : ViewModel(){

    private val _signInState = MutableLiveData<Result<FirebaseUser?>>()
    val signInState : MutableLiveData<Result<FirebaseUser?>> = _signInState

    private var _error by mutableStateOf("")
    var error = _error

    var password by mutableStateOf("")
        private set
    var email by  mutableStateOf("")
        private set

    fun changePassword(value : String) = run { password = value }
    fun changeEmail(value : String) = run { email = value }


    fun signIn(email : String , password : String){

        viewModelScope.launch {
            try {
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    if (task.isSuccessful){
                        _signInState.value = Result.success(auth.currentUser)
                    }else{
                        _signInState.value = Result.failure(task.exception ?: Exception("Failed"))
                        _error = task.exception?.message ?: "unexpected error"
                        error = _error
                    }
                }
            }catch(e:Exception){
                _signInState.value = Result.failure(e)
                _error = e.message.toString()
                error = _error
            }
        }

    }

}