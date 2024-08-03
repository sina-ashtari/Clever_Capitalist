package xyz.sina.clevercapitalist.model

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirestoreRepository(private val db: FirebaseFirestore){
    suspend fun getDataFromFireStore(): List<RegisterInfo>{
        return try{
            val snapshot =db.collection("register_info").get().await()
            snapshot.documents.mapNotNull { document ->
                document.toObject(RegisterInfo::class.java)
            }
        }catch (e:Exception){
            emptyList()
        }
    }
}