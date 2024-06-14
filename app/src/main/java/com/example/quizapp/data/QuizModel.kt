package com.example.quizapp.data


data class QuizQuestion(
    val question: String,
    val options: List<String>,
    val correctAnswer: String // Index of the correct answer
)
data class QuizApiResponse(
    val responseStatus:Int,
    val results:List<QuizResult>
)
data class QuizResult(
    val type: String,
    val difficulty: String,
    val category: String,
    val question: String,
    val correct_answer: String,
    val incorrect_answers: List<String>
)