package com.oneclick.blackandoneparent.presentation.di
//
//import android.content.Context
//import androidx.datastore.core.DataStore
//import androidx.datastore.dataStore
//import com.oneclick.androidcoe.core.UserProfileSerializer
//import com.oneclick.blackandoneparent.core.constants.AppConstants
//import com.oneclick.androidcoe.data.db.LanguageDao
//import com.oneclick.androidcoe.data.repository.language.datasource.LanguageLocalDataSource
//import com.oneclick.androidcoe.data.repository.language.datasourceimpl.LanguageLocalDataSourceImpl
//import com.oneclick.androidcoe.data.repository.profile.datasource.ProfileLocalDataSource
//import com.oneclick.androidcoe.data.repository.profile.datasourceimpl.ProfileLocalDataSourceImpl
//import com.oneclick.androidcoe.datastore.UserProfile
//import dagger.Module
//import dagger.Provides
//import dagger.hilt.InstallIn
//import dagger.hilt.android.qualifiers.ApplicationContext
//import dagger.hilt.components.SingletonComponent
//import javax.inject.Singleton
//
//@Module
//@InstallIn(SingletonComponent::class)
//class LocalDataModule {
//
//    // For Proto Data Store of User Profile.
//    private val Context.userProfileDataStore: DataStore<UserProfile> by dataStore(
//        fileName = AppConstants.USER_PROFILE_PROTO_DATA_STORE_FILE_NAME,
//        serializer = UserProfileSerializer
//    )
//
//
//    @Singleton
//    @Provides
//    fun provideLanguageLocalDataSource(languageDao: LanguageDao): LanguageLocalDataSource {
//        return LanguageLocalDataSourceImpl(languageDao)
//    }
//
//    @Singleton
//    @Provides
//    fun provideProfileLocalDataSource(@ApplicationContext context: Context): ProfileLocalDataSource {
//        return ProfileLocalDataSourceImpl(userDataStore = context.userProfileDataStore)
//    }
//}