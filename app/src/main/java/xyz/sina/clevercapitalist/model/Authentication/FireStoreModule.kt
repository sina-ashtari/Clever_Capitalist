package xyz.sina.clevercapitalist.model.Authentication

import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object FireStoreModule {
    @Provides
    @Singleton
    fun provideFireStore(): FirebaseFirestore{
        return FirebaseFirestore.getInstance()
    }

    @Provides
    @Singleton
    fun provideFireStoreRepository(db  : FirebaseFirestore)  = FirestoreRepository(db)
}