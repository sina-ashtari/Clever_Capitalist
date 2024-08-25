package xyz.sina.clevercapitalist.viewModel.registerFormViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import xyz.sina.clevercapitalist.model.RegisterInfo
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val fireStore : FirebaseFirestore
): ViewModel() {

    private val currentUser = FirebaseAuth.getInstance().currentUser
    private val uid = currentUser?.uid


    private val _goalsPair  = MutableLiveData<List<Pair<String , String >>>(emptyList())
    val goalsPair : LiveData<List<Pair<String, String>>> = _goalsPair

    fun addTextField(){
        _goalsPair.value = _goalsPair.value.orEmpty() + ("" to "")
    }

    fun updateTextField(index : Int, goal : String, moneyForGoal : String){
        _goalsPair.value = _goalsPair.value.orEmpty().toMutableList().also {
            it[index] =  goal to moneyForGoal
        }
    }

    fun deleteTextField(index : Int){
        _goalsPair.value = _goalsPair.value.orEmpty().toMutableList().also {
            if ( index in it.indices){
                it.removeAt(index)
            }
        }
    }

    fun saveGoalsData(){
        viewModelScope.launch {
            val goalsData = _goalsPair.value.orEmpty().map {(goal, moneyForGoal) ->
                mapOf(
                    "goal" to goal,
                    "moneyForGoal" to moneyForGoal,
                    "assignedMoney" to 0.0
                )
            }
            val batch = fireStore.batch()
            if(uid != null){
                goalsData.forEach { data ->
                    val docRef = fireStore.collection("users").document(uid).collection("goal").document()
                    batch.set(docRef, data)
                }
                // you could use addOnCompleteListener for better error showing in future
                batch.commit().addOnFailureListener{exception ->
                    Log.e("SAVE_GOALS_TO_FIRESTORE", "ERROR IS : ${exception.message}")
                }
            }
        }
    }

    fun saveRegisterInfo(registerInfo: RegisterInfo){
        if(uid != null){
            viewModelScope.launch {
                try {
                    fireStore.collection("users").document(uid).collection("register_info").add(registerInfo).addOnFailureListener { exception ->
                        Log.e("CHECK", "Error is : $exception")
                    }
                }catch (e:Exception){
                    Log.e("CHECK","Error is : $e")
                }
            }
        }
    }
}