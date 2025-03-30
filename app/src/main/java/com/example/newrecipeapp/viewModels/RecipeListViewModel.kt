package com.example.newrecipeapp.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.RecipeEntity
import com.example.domain.repositories.RecipeRepository
import com.example.newrecipeapp.components.FoodCategory
import com.example.newrecipeapp.components.getAllFoodCategories
import com.example.newrecipeapp.components.getFoodCategory
import com.example.newrecipeapp.uiStates.RecipeListScreenStates
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
    private val _recipes = MutableStateFlow<RecipeListScreenStates>(RecipeListScreenStates.Loading)
    val recipes: StateFlow<RecipeListScreenStates> = _recipes.asStateFlow()

    val query = MutableStateFlow<String>("")

    val selectedCategory : MutableStateFlow<FoodCategory?> = MutableStateFlow<FoodCategory?>(null)

    init {
        viewModelScope.launch{
            val result = repository.search(
                token = "Token 9c8b06d329136da358c2d00e76946b0111ce2c48",
                page = 1 ,
                query = "chicken"
            )
            _recipes.value = RecipeListScreenStates.Success(result!!)
        }
    }
    fun onQueryChange(query:String){
        this.query.value = query
        checkIfNewQueryMatchFoodCategory(query)?.let {
            selectedCategory.value = it
            searchRecipes()
        }
    }
    fun searchRecipes(){
        viewModelScope.launch {
            _recipes.value = RecipeListScreenStates.Loading
            val result = repository.search(
                token = "Token 9c8b06d329136da358c2d00e76946b0111ce2c48",
                page = 1 ,
                query = query.value
            )
            _recipes.value = RecipeListScreenStates.Success(result!!)
        }
    }
    fun onSelectedCategoryChanged(category:String){
        val newCategory = getFoodCategory(category)
        selectedCategory.value = newCategory
        onQueryChange(category)
    }
    fun checkIfNewQueryMatchFoodCategory(query: String): FoodCategory?{
        return getAllFoodCategories().find { category-> category.value.lowercase() == query.lowercase() }
    }
}