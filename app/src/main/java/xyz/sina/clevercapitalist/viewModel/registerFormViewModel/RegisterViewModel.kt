package xyz.sina.clevercapitalist.viewModel.registerFormViewModel

import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val fireStore : FirebaseFirestore
): ViewModel() {

}