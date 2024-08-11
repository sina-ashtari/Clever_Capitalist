package xyz.sina.clevercapitalist.model.RealmDB

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import xyz.sina.clevercapitalist.model.RealmRegisterInfo
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RealmModule {

    @Provides
    @Singleton
    fun provideRealm(): Realm {
        val config = RealmConfiguration.Builder(schema = setOf(RealmRegisterInfo::class)).deleteRealmIfMigrationNeeded().build()
        return Realm.open(config)
    }
}