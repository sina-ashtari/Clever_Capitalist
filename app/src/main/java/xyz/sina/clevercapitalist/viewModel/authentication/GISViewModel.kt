package xyz.sina.clevercapitalist.viewModel.authentication

import android.content.Context
import com.google.android.gms.auth.api.identity.SignInClient
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


@HiltViewModel
class GISViewModel @Inject constructor(
    private val signInClient : SignInClient,
    @ApplicationContext context: Context
) {

    // my mood is not good D:<

}