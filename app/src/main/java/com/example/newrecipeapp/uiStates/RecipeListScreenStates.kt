package com.example.newrecipeapp.uiStates

import com.example.domain.models.RecipeEntity

sealed class RecipeListScreenStates{
    object Loading : RecipeListScreenStates()
    data class Success(val recipes: List<RecipeEntity>) : RecipeListScreenStates()
    data class Error(val message: String) : RecipeListScreenStates()
}