package xyz.sina.clevercapitalist.model.Authentication

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import xyz.sina.clevercapitalist.model.FinancialGoals
import xyz.sina.clevercapitalist.model.RegisterInfo


class FirestoreRepository(private val db: FirebaseFirestore){
    suspend fun getDataFromFireStore(uid: String): List<RegisterInfo>{
        return try{
            val snapshot = db.collection("users").document(uid).collection("register_info").get().await()
            snapshot.documents.mapNotNull { document ->
                document.toObject(RegisterInfo::class.java)
            }
        }catch (e:Exception){
            emptyList()
        }
    }
    suspend fun getGoalsFromFireStore(uid : String): List<FinancialGoals>{
        return try{
            val snapshot = db.collection("users").document(uid).collection("goal").get().await()
            snapshot.documents.mapNotNull {document ->
                document.toObject(FinancialGoals::class.java)
            }
        }catch (e:Exception){
            Log.e("FETCH_ERROR", "FETCH_ERROR is $e")
            emptyList()
        }
    }
}