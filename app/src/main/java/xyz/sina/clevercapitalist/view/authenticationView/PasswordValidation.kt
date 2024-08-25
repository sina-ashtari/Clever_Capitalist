package xyz.sina.clevercapitalist.view.authenticationView

data class PasswordValidationState(
    val hasMinimum: Boolean = false,
    val hasCapitalizedLetter : Boolean = false,
    val hasSpecialChar : Boolean = false,
    val successful : Boolean = false
)
class ValidatePassword{
    private fun validateMinimum(password: String): Boolean = password.matches(Regex(".{6,}"))
    private fun validateCapitalise(password: String):Boolean = password.matches(Regex(".*[A-Z].*"))
    private fun validateSpecialChar(password: String): Boolean = password.matches(Regex("[!@#\$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]"))

    fun check(password: String):PasswordValidationState{
        val hasMinimum = validateMinimum(password)
        val hasCapitalise = validateCapitalise(password)
        val hasSpecialChar = validateSpecialChar(password)

        val hasError = listOf(
            hasMinimum,
            hasCapitalise,
            hasSpecialChar
        ).all{it}

        return PasswordValidationState(
            hasMinimum = hasMinimum,
            hasCapitalizedLetter = hasCapitalise,
            hasSpecialChar = hasSpecialChar,
            successful = hasError
        )
    }
}