package xyz.sina.clevercapitalist.model.RealmDB

import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import xyz.sina.clevercapitalist.model.RealmRegisterInfo
import javax.inject.Inject


class RealmRepository @Inject constructor(
    private val realm: Realm
) {
    suspend fun addRegisterToDB(
        registerInfo: RealmRegisterInfo
    ){
        withContext(Dispatchers.IO){
            realm.writeBlocking {
                copyToRealm(registerInfo)
            }
        }
    }

    suspend fun getRegisterInfoFromDB(): List<RealmRegisterInfo>{
        return withContext(Dispatchers.IO){
            realm.query<RealmRegisterInfo>().find()
        }
    }
}