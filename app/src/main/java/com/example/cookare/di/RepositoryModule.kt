package com.example.cookare.di

import com.example.cookare.mapper.RecipeNetworkMapper
import com.example.cookare.network.RecipeService
import com.example.cookare.repository.RecipeRepository
import com.example.cookare.repository.RecipeRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun provideRecipeRepository(
        recipeService: RecipeService,
        recipeNetworkMapper: RecipeNetworkMapper
    ): RecipeRepository{
        return RecipeRepositoryImpl(recipeService, recipeNetworkMapper)
    }
}