package com.example.quizapp.apiServices

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.quizapp.data.QuizApiResponse
import com.example.quizapp.data.QuizQuestion
import com.example.quizapp.data.QuizRepository
import com.example.quizapp.viewModel.QuizViewModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface QuizApiService {
    @GET("api.php?encode=base64")
    suspend fun getQuiz(
        @Query("category") category:Int,
        @Query("amount") numberOfQuestion:Int,
        @Query("difficulty") difficulty:String,
    ):QuizApiResponse


}

object RetrofitInstance {
    private const val BASE_URL = "https://opentdb.com/"

    val api: QuizApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(QuizApiService::class.java)
    }
}


class QuizViewModelFactory(private val repository: QuizRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        if (modelClass.isAssignableFrom(QuizViewModel::class.java)) {
            return QuizViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}