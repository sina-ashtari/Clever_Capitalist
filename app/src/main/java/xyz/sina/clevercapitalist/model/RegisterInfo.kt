package xyz.sina.clevercapitalist.model

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

data class RegisterInfo(
    val userName : String = "",
    // These data are monthly
    val salary : Double = 0.0,
    val houseRent : Double  = 0.0,
    val transport : Double  = 0.0,
    val debts : Double = 0.0,
    val otherExpenses : Double = 0.0
)
class RealmRegisterInfo: RealmObject{
    @PrimaryKey
    var id : String = ""
    var dbUserName : String = ""
    var dbSalary : Double = 0.0
    var dbHouseRent : Double  = 0.0
    var dbTransport : Double  = 0.0
    var dbDebts : Double = 0.0
    var dbOtherExpenses : Double = 0.0
}