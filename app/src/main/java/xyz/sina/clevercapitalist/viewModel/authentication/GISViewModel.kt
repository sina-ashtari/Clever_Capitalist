package xyz.sina.clevercapitalist.viewModel.authentication

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.android.gms.common.api.ApiException
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import xyz.sina.clevercapitalist.R
import javax.inject.Inject


@HiltViewModel
class GISViewModel @Inject constructor(
    private val googleSignInClient: GoogleSignInClient
) : ViewModel(){

    private val _gisState = mutableStateOf<GoogleSignInAccount?>(null)
    val gisState : State<GoogleSignInAccount?> = _gisState

    fun oneTapSignIn(launcher: ActivityResultLauncher<Intent>){
        val signInIntent = googleSignInClient.signInIntent
        launcher.launch(signInIntent)
    }
    fun handleOneTapResult(data : Intent?){
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        try{
            val account = task.getResult(ApiException::class.java)
            _gisState.value = account

        }catch (e:Exception){
            Log.e("ERROR","GIS FAILED : $e")
        }
    }
    fun signOut(){
        googleSignInClient.signOut().addOnSuccessListener {
            _gisState.value = null
        }
    }
}