package com.example.cookare.di

import com.example.cookare.mapper.RecipeNetworkMapper
import com.example.cookare.network.RecipeService
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun provideRecipeMapper():RecipeNetworkMapper {
        return RecipeNetworkMapper()
    }

    @Singleton
    @Provides
    fun provideRecipeService(): RecipeService{
        return Retrofit.Builder()
            .baseUrl("http://101.43.180.143:9090/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient.Builder().build())
            .build()
            .create(RecipeService::class.java)
    }

    @Singleton
    @Provides
    @Named("auth_token")
    fun provideAuthToken(): String{
        return "satoken=bcd4a2ee-5a4b-47e8-a9ef-ddb5f5e6efe1"
    }
}