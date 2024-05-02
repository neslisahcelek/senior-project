package com.example.theguide.di

import android.app.Application
import com.example.theguide.data.manager.LocalUserManagerImpl
import com.example.theguide.data.remote.PlacesAPI
import com.example.theguide.data.repository.PlaceRepositoryImpl
import com.example.theguide.domain.manager.LocalUserManager
import com.example.theguide.domain.usecase.appentry.AppEntryUseCases
import com.example.theguide.domain.usecase.appentry.ReadAppEntryUseCase
import com.example.theguide.domain.usecase.appentry.SaveAppEntryUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideLocalUserManager(
        application: Application
    ): LocalUserManager = LocalUserManagerImpl(application)

    @Provides
    @Singleton
    fun provideAppEntryUseCases(
        localUserManager: LocalUserManager
    ) = AppEntryUseCases(
        readAppEntryUseCase = ReadAppEntryUseCase(localUserManager),
        saveAppEntryUseCase = SaveAppEntryUseCase(localUserManager)
    )

    @Provides
    @Singleton
    fun providePlacesRepository(
        placesAPI: PlacesAPI
    ) = PlaceRepositoryImpl(placesAPI)



}