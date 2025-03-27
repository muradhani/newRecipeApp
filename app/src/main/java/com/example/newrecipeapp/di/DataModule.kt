package com.example.newrecipeapp.di

import com.example.data.mappers.RecipeMapper
import com.example.data.repositories.RecipeRepositoryImpl
import com.example.data.retrofit.RecipeService
import com.example.domain.repositories.RecipeRepository
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Provides
    fun provideRetrofit():Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://food2fork.ca/api/recipe/")
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
    }

    @Provides
    fun provideRecipeMapper():RecipeMapper{
        return RecipeMapper()
    }

    @Provides
    fun provideRecipeApiService(retrofit: Retrofit):RecipeService{
        return retrofit.create(RecipeService::class.java)
    }

    @Provides
    fun provideRecipeRepository(
        recipeService: RecipeService,
        recipeMapper: RecipeMapper
    ): RecipeRepository {
        return RecipeRepositoryImpl(recipeService, recipeMapper)
    }
}