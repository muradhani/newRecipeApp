package com.example.newrecipeapp.fragments

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.newrecipeapp.components.RecipeCard
import com.example.newrecipeapp.viewModels.RecipeListViewModel

@Composable
fun RecipeListScreen(viewModel: RecipeListViewModel = hiltViewModel()) {
    val recipes = viewModel.recipes.collectAsState().value
    val query = viewModel.query.collectAsState().value
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { padding ->
        Column {
            TextField(
                value = query,
                onValueChange = { viewModel.onQueryChange(it) },
                placeholder = { Text("Enter text here") }
            )
            Spacer(modifier = Modifier.padding(10.dp))
            LazyColumn(modifier = Modifier.padding(padding)) {
                itemsIndexed(recipes) { index, recipe ->
                    RecipeCard(recipe, onClick = { })
                }
            }
        }
    }
}
