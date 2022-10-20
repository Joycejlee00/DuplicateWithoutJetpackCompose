package com.example.bottomnavwfab.data


import java.util.*



data class RecipeData(
    val image: String?,
    var title: String?,
    var missedIngredients: ArrayList<MissedIngredient>,
    var usedIngredients: ArrayList<UsedIngredient>,
    var expandable : Boolean = false)
{
    override fun toString(): String {
        var getString = "\n"
        for (i in 0 until missedIngredients.size) {
            for (j in 0 until usedIngredients.size) {
                getString += "-" + usedIngredients[j].original + "\n"
            }
            getString += "-" + missedIngredients[i].original + "\n"
        }
        return getString
    }
}