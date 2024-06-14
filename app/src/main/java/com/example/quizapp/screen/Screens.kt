package com.example.quizapp.screen

import com.example.quizapp.data.QuizCategory
import kotlinx.serialization.Serializable

@Serializable
object HomeScreen

@Serializable
data class CategoryDetailsScreen(
    val quizId:Int,
    val categoryName: String,
)
@Serializable
data class QuizQuestionScreen(
    val categoryId:Int,
    val categoryName: String,
    val numberOfQuestion:Int,
    val level:String,
)
@Serializable
object HistoryScreen
@Serializable
object AccountScreen


@Serializable
data class QuizResultScreenModel(
    val category: String,
    val accuracy:String
)

