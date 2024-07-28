package xyz.sina.clevercapitalist.viewModel.authentication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val auth : FirebaseAuth
) : ViewModel(){

    private val _authState = MutableLiveData<Boolean>()
    val authState : LiveData<Boolean> = _authState

    init {
            checkUser()
    }

    private fun checkUser(){
        val user = auth.currentUser
        _authState.value = user !=null

    }
    fun signOut (){
        auth.signOut()
        _authState.value = false
    }

}