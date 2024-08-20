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
import xyz.sina.clevercapitalist.view.FinancialGoals
import xyz.sina.clevercapitalist.view.toMap
import javax.inject.Inject

fun FinancialGoals.toMap(): Map<String, Any> {
    return mapOf(
        "goal" to goal,
        "moneyForGoal" to moneyForGoal
    )
}

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val fireStore : FirebaseFirestore
): ViewModel() {

    private val currentUser = FirebaseAuth.getInstance().currentUser
    private val uid = currentUser?.uid


    fun saveRegisterInfo(registerInfo: RegisterInfo,financialGoalsList: List<FinancialGoals>){
        if(uid != null){
            viewModelScope.launch {
                try {
                    financialGoalsList.forEach {
                        val financialGoalsMap = it.toMap()
                        fireStore.collection("users").document(uid).collection("goals").add(financialGoalsMap).addOnFailureListener {exception->
                            Log.e("CHECK","Error is : $exception")
                        }
                    }
                }catch (e:Exception){
                    Log.e("CHECK","Error is : $e")
                }
            }
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