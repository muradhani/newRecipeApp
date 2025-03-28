package com.example.newrecipeapp.components

enum class FoodCategory(val value : String){
    CHICKEN("Chicken"),
    BEEF("Beef"),
    SOUP("Soup"),
    Desert("Desert"),
    MILK("Milk"),
    VEGAN("Vegan"),
    PIZZA("Pizza"),
    DOUNT("Dount")
}

fun getAllFoodCategories(): List<FoodCategory>{
    return listOf(
        FoodCategory.CHICKEN,
        FoodCategory.BEEF,
        FoodCategory.SOUP,
        FoodCategory.Desert,
        FoodCategory.MILK,
        FoodCategory.VEGAN,
        FoodCategory.PIZZA,
        FoodCategory.DOUNT
    )
}
fun getFoodCategory(value:String) = FoodCategory.values().find { it.value == value }