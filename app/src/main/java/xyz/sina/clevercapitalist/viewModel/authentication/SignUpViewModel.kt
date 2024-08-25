package xyz.sina.clevercapitalist.viewModel.authentication

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import xyz.sina.clevercapitalist.view.authenticationView.PasswordValidationState
import xyz.sina.clevercapitalist.view.authenticationView.ValidatePassword
import javax.inject.Inject


@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val auth: FirebaseAuth,
) : ViewModel(){

    private val validatePassword: ValidatePassword = ValidatePassword()

    private val _signUpState = MutableLiveData<Result<Boolean>>()
    val signUpState : MutableLiveData<Result<Boolean>> = _signUpState

    var password by mutableStateOf("")
        private set
    var email by  mutableStateOf("")
        private set

    @OptIn(ExperimentalCoroutinesApi::class)
    val passwordError: StateFlow<PasswordValidationState> =
        snapshotFlow { password }
            .mapLatest { validatePassword.check(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = PasswordValidationState()
            )

    fun changePassword(value : String) = run { password = value }
    fun changeEmail(value : String) = run { email = value }

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


