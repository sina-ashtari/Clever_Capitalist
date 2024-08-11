package xyz.sina.clevercapitalist.model.Authentication

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import xyz.sina.clevercapitalist.model.RegisterInfo

class FirestoreRepository(private val db: FirebaseFirestore){
    suspend fun getDataFromFireStore(uid: String): List<RegisterInfo>{
        return try{
            val snapshot =db.collection("users").document(uid).collection("register_info").get().await()
            snapshot.documents.mapNotNull { document ->
                document.toObject(RegisterInfo::class.java)
            }
        }catch (e:Exception){
            emptyList()
        }
    }
}