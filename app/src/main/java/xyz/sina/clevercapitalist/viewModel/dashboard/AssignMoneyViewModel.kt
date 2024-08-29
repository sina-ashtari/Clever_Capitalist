package xyz.sina.clevercapitalist.viewModel.dashboard

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import xyz.sina.clevercapitalist.model.Authentication.FirestoreRepository
import xyz.sina.clevercapitalist.model.FinancialGoals
import javax.inject.Inject

@HiltViewModel
class AssignMoneyViewModel @Inject constructor(
    private val repository: FirestoreRepository,
    private val firestore : FirebaseFirestore
):ViewModel()  {

    private val currentUser = FirebaseAuth.getInstance().currentUser
    private val uid = currentUser?.uid

    var moneyValue by mutableFloatStateOf(0f)
       private set

    fun changeMoneyValueBySlider(value : Float) = run { moneyValue = value}
    fun changeMoneyValueByTextField(value: String)  = run { moneyValue = if(value.isNotEmpty()){value.toFloat()}else{0f} }

    private val _goalsData = MutableLiveData<List<FinancialGoals>>()
    val goalsData : LiveData<List<FinancialGoals>> = _goalsData

    private var _error by mutableStateOf("")
    val error = _error

    init {
        if (uid != null){
            fetchGoals(uid)
        }else{
            _error = "Re-login please"
        }
    }

    private fun fetchGoals(uid: String) {
        viewModelScope.launch {
            try {
                val result = repository.getGoalsFromFireStore(uid)
                _goalsData.value = result
            }catch (e:Exception){
                _error = e.message.toString()
            }
        }
    }

    fun saveGoalsData(){
        viewModelScope.launch {
            val goalsData = _goalsData.value.orEmpty().map {(goal, moneyForGoal) ->
                mapOf(
                    "goal" to goal,
                    "moneyForGoal" to moneyForGoal,
                    "assignedMoney" to 0.0
                )
            }
            val batch = firestore.batch()
            if(uid != null){
                goalsData.forEach { data ->
                    val docRef = firestore.collection("users").document(uid).collection("goal").document()
                    batch.set(docRef, data)
                }
                // you could use addOnCompleteListener for better error showing in future
                batch.commit().addOnFailureListener{exception ->
                    Log.e("SAVE_GOALS_TO_FIRESTORE", "ERROR IS : ${exception.message}")
                }
            }
        }
    }


}