package com.example.newrecipeapp.fragments

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.newrecipeapp.components.RecipeCard
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
                        TextField(
                            value = query,
                            onValueChange = { viewModel.onQueryChange(it) },
                            placeholder = { Text("Enter text here") },
                            modifier = Modifier
                                .fillMaxWidth(),
                            label = { Text(text = "Search") },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Done
                            ),
                            leadingIcon = {
                                Icon(
                                    Icons.Filled.Search,
                                    contentDescription = "Search Icon"
                                )
                            },
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    viewModel.searchRecipes(query)
                                    keyboardController?.hide()
                                }
                            ),
                            textStyle = TextStyle(color = MaterialTheme.colorScheme.onSurface),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.Transparent, // Make transparent to show Box background
                                unfocusedContainerColor = Color.Transparent,
                                disabledContainerColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent, // Removes the black line
                                unfocusedIndicatorColor = Color.Transparent, // Removes the black line
                            ),
                            shape = RoundedCornerShape(12.dp) // TextField inner shape
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
                                Box(
                                    modifier = Modifier
                                        .padding(end = 8.dp) // Space between items
                                        .shadow(
                                            elevation = 2.dp,
                                            shape = RoundedCornerShape(8.dp)
                                        )
                                        .background(
                                            color = MaterialTheme.colorScheme.surface,
                                            shape = RoundedCornerShape(8.dp)
                                        ).clickable(onClick = {})
                                        .fillMaxWidth()
                                ) {
                                    Text(
                                        text = category.value,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.secondary,
                                        modifier = Modifier.padding(
                                            horizontal = 12.dp,
                                            vertical = 8.dp
                                        )
                                    )
                                }
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
