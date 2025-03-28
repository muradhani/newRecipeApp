package com.example.newrecipeapp.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.newrecipeapp.ui.theme.Purple40

@Composable
fun FoodCategoryChip(
    foodCategory: FoodCategory,
    isSelected : Boolean = false ,
    onExecuteSearch : () ->Unit,
    onSelectedCategoryChanged : (String) -> Unit
) {
    Box(
        modifier = Modifier
            .padding(end = 8.dp) // Space between items
            .shadow(
                elevation = 2.dp,
                shape = RoundedCornerShape(8.dp)
            )
            .background(
                color = if (!isSelected)MaterialTheme.colorScheme.surface else Purple40,
                shape = RoundedCornerShape(8.dp)
            ).toggleable(value = isSelected, onValueChange = {
                onSelectedCategoryChanged(foodCategory.value)
                onExecuteSearch()
            })
            .fillMaxWidth()
    ) {
        Text(
            text = foodCategory.value,
            style = MaterialTheme.typography.bodyMedium,
            color = if (!isSelected) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.surface ,
            modifier = Modifier.padding(
                horizontal = 12.dp,
                vertical = 8.dp
            )
        )
    }
}