package xyz.sina.clevercapitalist.viewModel.authentication

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



    fun signIn(email : String , password : String){

        viewModelScope.launch {
            try {
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    if (task.isSuccessful){
                        _signInState.value = Result.success(auth.currentUser)
                    }else{
                        _signInState.value = Result.failure(task.exception ?: Exception("Failed"))
                    }
                }
            }catch(e:Exception){
                _signInState.value = Result.failure(e)
            }
        }

    }

}