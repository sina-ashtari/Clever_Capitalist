package xyz.sina.clevercapitalist.model

data class RegisterInfo(
    val userName : String = "",
    // These data are monthly
    val houseRent : Double  = 0.0,
    val transport : Double  = 0.0,
    val regularlySpends : Double = 0.0,
    val otherExpenses : Double = 0.0
)