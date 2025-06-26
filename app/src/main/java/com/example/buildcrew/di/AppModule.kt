package com.example.buildcrew.di

import android.app.Application
import com.example.buildcrew.data.repository.AuthRepository
import com.example.buildcrew.data.repository.UserRepository
import com.example.buildcrew.data.repository.ProjectRepository
import com.example.buildcrew.data.repository.ChatRepository
import com.example.buildcrew.data.source.FirebaseAuthRepository
import com.example.buildcrew.data.source.FirebaseUserRepository
import com.example.buildcrew.data.source.FirebaseProjectRepository
import com.example.buildcrew.data.source.FirebaseChatRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
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
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()

    @Provides
    @Singleton
    fun provideAuthRepository(auth: FirebaseAuth): AuthRepository = FirebaseAuthRepository(auth)

    @Provides
    @Singleton
    fun provideUserRepository(firestore: FirebaseFirestore, auth: FirebaseAuth): UserRepository =
        FirebaseUserRepository(firestore, auth)

    @Provides
    @Singleton
    fun provideProjectRepository(firestore: FirebaseFirestore): ProjectRepository =
        FirebaseProjectRepository(firestore)

    @Provides
    @Singleton
    fun provideChatRepository(firestore: FirebaseFirestore): ChatRepository =
        FirebaseChatRepository(firestore)
} 