package com.example.newrecipeapp.fragments

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.newrecipeapp.components.RecipeCard
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
                    modifier = Modifier.padding(top = 32.dp, start = 16.dp, end = 16.dp, bottom = 16.dp)
                )
            }

            item{
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.primary,
                    shadowElevation = 8.dp
                ) {
                    Row (
                        modifier = Modifier.fillMaxWidth()
                    ){
                        // Search Bar
                        TextField(
                            value = query,
                            onValueChange = { viewModel.onQueryChange(it) },
                            placeholder = { Text("Enter text here") },
                            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                            label = { Text(text = "Search") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Done),
                            leadingIcon = {Icon(Icons.Filled.Search, contentDescription = "Search Icon")},
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    // Handle the event when the "Done" key is pressed
                                    viewModel.searchRecipes(query) // Example function call (replace with your logic)
                                    keyboardController?.hide() // ✅ Hides keyboard when "Done" is clicked
                                }
                            ),
                            textStyle = TextStyle(color = MaterialTheme.colorScheme.onSurface),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = MaterialTheme.colorScheme.surface,
                                unfocusedContainerColor = MaterialTheme.colorScheme.surface
                            )
                        )
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
