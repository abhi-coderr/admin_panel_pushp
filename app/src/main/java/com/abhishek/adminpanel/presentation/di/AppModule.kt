package com.oneclick.blackandoneparent.presentation.di

import android.app.Activity
import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import com.oneclick.blackandoneparent.core.constants.AppConstants
import com.oneclick.blackandoneparent.core.utils.AppDialogUtils
import com.oneclick.blackandoneparent.core.utils.AppPreference
import com.oneclick.blackandoneparent.core.utils.AppProgressUtils
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

/**
 * Description: This module class is used for the purpose of setting application wide hilt dependencies.
 *
 * @author Ankit Mishra
 * @since 26/10/21
 */
@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    /**
     * For Preference Data Store
     */
    private val Context.prefDataStore by preferencesDataStore(
        name = AppConstants.PREFERENCE_DATA_STORE_NAME
    )

    @Provides
    @Reusable
    fun providePreferenceDataStore(@ApplicationContext context: Context) = context.prefDataStore


    @Provides
    @Reusable
    internal fun providesAppPreference(@ApplicationContext context: Context) =
        AppPreference(context.prefDataStore)





    @Provides
    @Reusable
    fun provideAppProgressUtils(@ActivityContext context: Context) =
        AppProgressUtils(context as Activity)


    @Provides
    @Reusable
    fun provideAppDialogUtils(@ActivityContext context: Context) =
        AppDialogUtils(context as Activity)

}