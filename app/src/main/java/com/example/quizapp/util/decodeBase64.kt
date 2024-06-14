package com.example.quizapp.util

import android.util.Base64
import android.util.Log
import com.example.quizapp.data.QuizQuestion
import com.example.quizapp.data.QuizResult

fun String.decodeBase64():String{
    return String(Base64.decode(this,Base64.DEFAULT))
}

fun mapToQuizQuestions(results: List<QuizResult>): List<QuizQuestion> {
    return results.map { result ->
        Log.d("Map", "mapToQuizQuestions: $result")
        QuizQuestion(
            question = result.question.decodeBase64(),
            options = (result.incorrect_answers.map { it.decodeBase64() } + result.correct_answer.decodeBase64()).toList().shuffled(),
            correctAnswer = result.correct_answer.decodeBase64()
        )
    }
}
