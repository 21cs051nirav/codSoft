package com.example.quizapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizapp.R
import com.example.quizapp.data.QuizCategory
import com.example.quizapp.data.QuizQuestion
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeScreenViewMode:ViewModel() {
    private val _quizCategory = MutableStateFlow<List<QuizCategory>>(emptyList())
    val quizCategory: StateFlow<List<QuizCategory>> = _quizCategory
    init {
        _quizCategory.value=getQuizCategory()
    }
    private fun getQuizCategory():List<QuizCategory>{
        val list= listOf(
            QuizCategory(21, R.drawable.outline_sports_baseball_24,"Sport"),
            QuizCategory(17, R.drawable.outline_science_24,"Science"),
            QuizCategory(32,R.drawable.baseline_filter_center_focus_24,"Entertainment"),
            QuizCategory(9,R.drawable.baseline_filter_center_focus_24,"G.K"),
            QuizCategory(11,R.drawable.baseline_filter_center_focus_24,"Film"),
            QuizCategory(31,R.drawable.baseline_filter_center_focus_24,"Anime"),
            QuizCategory(23,R.drawable.baseline_filter_center_focus_24,"History")
        )
        return list
    }
}