package com.example.quizapp.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizapp.data.QuizQuestion
import com.example.quizapp.data.QuizRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class QuizViewModel(private val repository: QuizRepository):ViewModel() {
    private val _quizQuestions = MutableStateFlow<List<QuizQuestion>>(emptyList())
    val quizQuestions: StateFlow<List<QuizQuestion>> = _quizQuestions

    private val _selectedOption = MutableStateFlow<Int?>(null)
    val selectedOption: StateFlow<Int?> = _selectedOption

    private val _currentQuestionIndex = MutableStateFlow(0)
    val currentQuestionIndex: StateFlow<Int> = _currentQuestionIndex

    private val _score= MutableStateFlow(0)
    val score: StateFlow<Int> = _score
    private val _timerCount= MutableStateFlow(30)
    val timerCount:StateFlow<Int> = _timerCount

    private var numberOfAttempt=0.0
    private var numberOfQuestions=0
    private var _totalAccuracy=0.0
    fun fetchData(category:Int,numberOfQuestion:Int,level:String){
         Log.d("choose Data", "fetchData: $category $numberOfQuestion $level")
            viewModelScope.launch {
                try {
                    val quiz=repository.getQuiz(category,numberOfQuestion,level)
                    Log.d("fetch Data", "fetchData: $quiz")
                    _quizQuestions.value=quiz
                   numberOfQuestions= _quizQuestions.value.size
                }catch(e:Exception){
                    Log.d("Error", "fetchData: $e")
                }
            }
    }

    fun selectedOption(optionIndex:Int){
        _selectedOption.value=optionIndex
        val currentQuestion= quizQuestions.value[currentQuestionIndex.value]
        numberOfAttempt++
        Log.d("Accuracy", "selectedOption: $numberOfQuestions $numberOfAttempt $_totalAccuracy  A:${((1/numberOfAttempt)*100)}")
        if(currentQuestion.options[optionIndex]==currentQuestion.correctAnswer){
            _score.update { _score.value+1 }
            _totalAccuracy+=(((1/numberOfAttempt)*100)/numberOfQuestions)
            viewModelScope.launch {
                delay(200)
                nextQuestion()
            }
        }
    }
    fun nextQuestion(){
        numberOfAttempt=0.0
        if (_currentQuestionIndex.value < _quizQuestions.value.size - 1) {
            _currentQuestionIndex.value++
            _selectedOption.value = null
            _timerCount.value=30
            Log.d("Update", "nextQuestion:${_timerCount.value} ${timerCount.value} ")
        }

    }
    fun getAccuracy():Double{
        return _totalAccuracy
    }

}