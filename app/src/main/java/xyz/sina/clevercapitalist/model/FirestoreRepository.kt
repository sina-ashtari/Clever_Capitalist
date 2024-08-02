package xyz.sina.clevercapitalist.model

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirestoreRepository @Inject constructor(
    private val firestore: FirebaseFirestore
){

    fun getUserData(): Flow<Result<RegisterInfo>> = flow {
        try {
            val document = firestore.collection("register_info").document().get().await()
            val data = document.toObject(RegisterInfo::class.java)
            if (data != null) {
                emit(Result.success(data))
            }else{
                emit(Result.failure(Exception("User Not Found")))
            }

        }catch (e: Exception){
            emit(Result.failure(e))
        }

    }

}