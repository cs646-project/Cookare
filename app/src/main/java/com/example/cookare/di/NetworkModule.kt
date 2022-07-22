package com.example.cookare.di

import com.example.cookare.mapper.IngredientNetworkMapper
import com.example.cookare.mapper.RecipeNetworkMapper
import com.example.cookare.network.RecipeService
import com.example.cookare.ui.auth
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
    fun provideIngredientMapper():IngredientNetworkMapper {
        return IngredientNetworkMapper()
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
        return auth
    }
}