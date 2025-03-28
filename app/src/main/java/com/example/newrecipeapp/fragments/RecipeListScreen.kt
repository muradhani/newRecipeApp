package com.example.newrecipeapp.fragments

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.newrecipeapp.components.FoodCategoryChip
import com.example.newrecipeapp.components.RecipeCard
import com.example.newrecipeapp.components.SearchTextField
import com.example.newrecipeapp.components.getAllFoodCategories
import com.example.newrecipeapp.viewModels.RecipeListViewModel

@Composable
fun RecipeListScreen(viewModel: RecipeListViewModel = hiltViewModel()) {
    val recipes = viewModel.recipes.collectAsState().value
    val query = viewModel.query.collectAsState().value
    val keyboardController = LocalSoftwareKeyboardController.current

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // Header (Title)
            item {
                Text(
                    text = "Recipe List",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(
                        top = 32.dp,
                        start = 16.dp,
                        end = 16.dp,
                        bottom = 16.dp
                    )
                )
            }

            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp) // Add outer padding for better spacing
                ) {
                    // TextField with elevation and rounded corners
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .shadow(
                                elevation = 8.dp,
                                shape = RoundedCornerShape(12.dp) // Rounded corners
                            )
                            .background(
                                color = MaterialTheme.colorScheme.surface,
                                shape = RoundedCornerShape(12.dp) // Match shadow shape
                            )
                    ) {
                        SearchTextField(
                            query = query,
                            onQueryChange = { viewModel.onQueryChange(it) },
                            onSearch = { viewModel.searchRecipes(query) },
                            keyboardController = keyboardController
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp)) // Add spacing between TextField and Row
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .horizontalScroll(rememberScrollState())
                                .fillMaxWidth()
                        ) {
                            for (category in getAllFoodCategories()) {
                                FoodCategoryChip(
                                    foodCategory = category,
                                    onClick = {
                                        viewModel.onQueryChange(category.value)
                                    }
                                )
                            }
                        }
                    }
                }
            }

            // Spacer for separation
            item {
                Spacer(modifier = Modifier.height(10.dp))
            }

            // List of Recipes
            itemsIndexed(recipes) { _, recipe ->
                RecipeCard(recipe, onClick = { })
            }
        }
    }
}
