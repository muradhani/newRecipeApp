package com.example.newrecipeapp.fragments

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.newrecipeapp.components.RecipeCard
import com.example.newrecipeapp.viewModels.RecipeListViewModel
import kotlin.getValue

@Composable
fun RecipeListScreen(viewModel: RecipeListViewModel = hiltViewModel()) {
    val recipes = viewModel.recipes.collectAsState().value
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding)) {
            itemsIndexed(recipes) { index, recipe ->
                RecipeCard(recipe, onClick = { })
            }
        }
    }
}
