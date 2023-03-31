package com.example.dogsapp.core

import android.app.Application
import com.example.dogsapp.data.DogApi
import com.example.dogsapp.data.DogRepositoryImpl
import com.example.dogsapp.data.DogService
import com.example.dogsapp.domain.repository.DogRepository
import com.example.dogsapp.domain.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@HiltAndroidApp
class DogsApplication : Application() {

    override fun onCreate() {
        super.onCreate()
    }

    @Module
    @InstallIn(SingletonComponent::class)
    object AppModule {
        @Provides
        fun provideGetRandomBreedsUseCase(dogRepository: DogRepository): GetRandomBreedsUseCase {
            return GetRandomBreedsUseCaseImpl(dogRepository)
        }

        @Provides
        fun provideGetRandomImageByBreedUseCase(
            dogRepository: DogRepository,
        ): GetRandomImageByBreedUseCase {
            return GetRandomImageByBreedUseCaseImpl(dogRepository)
        }

        @Provides
        fun provideGetSubBreedsUseCase(dogRepository: DogRepository): GetSubBreedsUseCase {
            return GetSubBreedsUseCaseImpl(dogRepository)
        }

        @Provides
        fun provideDogRepository(dogService: DogService): DogRepository {
            return DogRepositoryImpl(dogService)
        }

        @Provides
        fun provideDogApi(): DogApi {
            return Retrofit.Builder()
                .baseUrl("https://dog.ceo/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(DogApi::class.java)
        }
    }
}