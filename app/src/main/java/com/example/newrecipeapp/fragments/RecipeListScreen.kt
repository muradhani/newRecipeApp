package com.example.newrecipeapp.fragments


import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.newrecipeapp.components.CircularInterminateProgressBar
import com.example.newrecipeapp.components.FoodCategoryChip
import com.example.newrecipeapp.components.RecipeCard
import com.example.newrecipeapp.components.SearchTextField
import com.example.newrecipeapp.components.ShimmerRecipeCard
import com.example.newrecipeapp.components.getAllFoodCategories
import com.example.newrecipeapp.uiStates.RecipeListScreenStates
import com.example.newrecipeapp.viewModels.RecipeListViewModel
import kotlinx.coroutines.launch

@Composable
fun RecipeListScreen(viewModel: RecipeListViewModel = hiltViewModel()) {
    val recipes = viewModel.recipes.collectAsState().value
    val query = viewModel.query.collectAsState().value
    val keyboardController = LocalSoftwareKeyboardController.current
    val selectedCategory = viewModel.selectedCategory.collectAsState().value
    val scrollState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val showScrollToTop by remember {
        derivedStateOf {
            scrollState.firstVisibleItemIndex > 5 // Show button when not at top
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            state = scrollState
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
                            onSearch = { viewModel.searchRecipes() },
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
                                    isSelected = selectedCategory == category,
                                    foodCategory = category,
                                    onExecuteSearch = viewModel::searchRecipes,
                                    onSelectedCategoryChanged = { viewModel.onSelectedCategoryChanged(it)}
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

            displayRecipesList(recipes)
        }
        if (showScrollToTop) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 40.dp),
                contentAlignment = Alignment.TopCenter
            ) {
                IconButton(
                    onClick = {
                        coroutineScope.launch {
                            scrollState.animateScrollToItem(index = 0)
                        }
                    },
                    modifier = Modifier
                        .size(40.dp)
                        .background(MaterialTheme.colorScheme.surface, CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowUp,
                        contentDescription = "Scroll to top",
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}

fun LazyListScope.displayRecipesList(recipesState : RecipeListScreenStates) {
    when (recipesState) {
        is RecipeListScreenStates.Loading -> {
            item {
                CircularInterminateProgressBar(true) // Show loading indicator
            }

            items(5) {
                ShimmerRecipeCard() // Show 5 shimmer placeholders
            }
        }
        is RecipeListScreenStates.Success -> {
            itemsIndexed(recipesState.recipes) { _, recipe ->
                RecipeCard(recipe, onClick = { })
            }
        }

        is RecipeListScreenStates.Error -> TODO()
    }
}