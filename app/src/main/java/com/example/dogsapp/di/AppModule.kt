package com.example.dogsapp.di

import com.example.dogsapp.Constant
import com.example.dogsapp.data.api.DogApi
import com.example.dogsapp.data.repository.DogRepositoryImpl
import com.example.dogsapp.domain.repository.DogRepository
import com.example.dogsapp.domain.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

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
    fun provideDogRepository(dogApi: DogApi): DogRepository {
        return DogRepositoryImpl(dogApi)
    }

    @Provides
    fun provideDogApi(): DogApi {
        return Retrofit.Builder()
            .baseUrl(Constant.Api.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient
                    .Builder()
                    .connectTimeout(Constant.Api.TIMEOUT, TimeUnit.SECONDS)
                    .build()
            )
            .build()
            .create(DogApi::class.java)
    }
}