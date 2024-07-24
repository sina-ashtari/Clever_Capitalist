package viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val auth: FirebaseAuth
) : ViewModel(){

    private val _signUpState = MutableLiveData<Result<Boolean>>()
    val signUpState : MutableLiveData<Result<Boolean>> = _signUpState


    fun signUp(email : String , password : String){
        viewModelScope.launch {
            try {
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    if (task.isSuccessful){
                        _signUpState.value = Result.success(true)
                    }else{
                        _signUpState.value = Result.failure(task.exception ?: Exception("Failed"))
                    }
                }
            }catch (e : Exception){
                _signUpState.value = Result.failure(e)
            }
        }

    }




}