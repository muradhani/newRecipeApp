package com.example.newrecipeapp.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.RecipeEntity
import com.example.domain.repositories.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


@HiltViewModel
class RecipeListViewModel @Inject constructor(
    private val repository : RecipeRepository
): ViewModel() {
    private val _recipes = MutableStateFlow<List<RecipeEntity>>(emptyList())
    val recipes: StateFlow<List<RecipeEntity>> = _recipes.asStateFlow()

    val query = MutableStateFlow<String>("")

    init {
        viewModelScope.launch{
            val result = repository.search(
                token = "Token 9c8b06d329136da358c2d00e76946b0111ce2c48",
                page = 1 ,
                query = "chicken"
            )
            _recipes.value = result!!
        }
    }
    fun onQueryChange(query:String){
        this.query.value = query
    }
    fun searchRecipes(query: String){
        viewModelScope.launch {
            val result = repository.search(
                token = "Token 9c8b06d329136da358c2d00e76946b0111ce2c48",
                page = 1 ,
                query = query
            )
            _recipes.value = result!!
        }
    }
}