package com.example.quizapp.data

import android.util.Log
import com.example.quizapp.apiServices.QuizApiService
import com.example.quizapp.util.mapToQuizQuestions

class QuizRepository(private val apiService:QuizApiService) {
    suspend fun getQuiz(category:Int,amount:Int,difficulty:String):List<QuizQuestion>{
        val response= apiService.getQuiz(category,amount,difficulty)
        Log.d("Net response", "getQuiz: $response")
        return mapToQuizQuestions(response.results)
    }
}