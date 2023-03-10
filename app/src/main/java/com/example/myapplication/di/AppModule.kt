package com.example.myapplication.di

import android.content.Context
import androidx.room.Room
import com.example.myapplication.db.CharacterDBRepository
import com.example.myapplication.db.CharacterDao
import com.example.myapplication.db.CharacterDatabase
import com.example.myapplication.domain.services.IApiInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun providesCharacterDao(characterDatabase: CharacterDatabase):CharacterDao = characterDatabase.getCharacterDao()

    @Provides
    @Singleton
    fun providesCharacterDatabase(@ApplicationContext context: Context):CharacterDatabase
            = Room.databaseBuilder(context,CharacterDatabase::class.java,"CharacterDatabase").allowMainThreadQueries().build()

    @Provides
    fun providesCharacterRepository(characterDao: CharacterDao) : CharacterDBRepository
            = CharacterDBRepository(characterDao)

    val BASE_URL = "https://rickandmortyapi.com/api/"

    @Singleton
    @Provides
    fun getIApiInterfaceInstance(retrofit: Retrofit): IApiInterface {
        return retrofit.create(IApiInterface::class.java)
    }

    @Singleton
    @Provides
    fun getRetrofitServiceInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }
}