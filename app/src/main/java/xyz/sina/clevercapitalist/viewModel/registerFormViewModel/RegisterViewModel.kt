package xyz.sina.clevercapitalist.viewModel.registerFormViewModel

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import xyz.sina.clevercapitalist.model.RegisterInfo
import xyz.sina.clevercapitalist.view.FinancialGoals
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val fireStore : FirebaseFirestore
): ViewModel() {

    private val currentUser = FirebaseAuth.getInstance().currentUser
    private val uid = currentUser?.uid

    val textFieldPairs = mutableStateListOf<Pair<String,String>>()

    fun addTextFieldPair(){
        textFieldPairs.add("" to "")
    }

    fun updateTextFieldPair(index: Int, goal: String, moneyForGoal : String){
        textFieldPairs[index] = goal to moneyForGoal
    }

    fun deleteTextFieldPair(index: Int){
        if(index in textFieldPairs.indices){
            textFieldPairs.removeAt(index)
        }
    }

    fun saveGoalsData(){
        if(uid!=null){
            viewModelScope.launch {
                val batch = fireStore.batch()

                textFieldPairs.forEach {(goal, moneyForGoal)->
                    val goalsData = hashMapOf("goal" to goal, "moneyForGoal" to moneyForGoal)
                    val docRef = fireStore.collection("users").document(uid).collection("goal").document()

                    batch.set(docRef,goalsData)
                }

                batch.commit().addOnSuccessListener {
                    Log.e("SAVE_GOALS_TO_FIRESTORE","WORKED")
                }.addOnFailureListener {exception ->
                    Log.e("SAVE_GOALS_TO_FIRESTORE","ERROR IS : $exception")
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