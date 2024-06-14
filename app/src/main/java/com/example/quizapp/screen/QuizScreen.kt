package com.example.quizapp.screen

import android.content.Context
import android.icu.text.DecimalFormat
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.quizapp.apiServices.QuizViewModelFactory
import com.example.quizapp.apiServices.RetrofitInstance
import com.example.quizapp.component.CountdownTimer
import com.example.quizapp.data.QuizQuestion
import com.example.quizapp.data.QuizRepository
import com.example.quizapp.viewModel.QuizViewModel
import kotlinx.coroutines.delay
import java.util.Locale


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun QuizScreen(
    categoryId: Int,
    categoryName:String,
    numberOfQuestion: Int,
    level: String,
    navController: NavController,
    quizViewModel: QuizViewModel = viewModel(
        factory = QuizViewModelFactory(QuizRepository(RetrofitInstance.api))
    )
) {
    val context = LocalContext.current
    Log.d("passed data", "QuizScreen: $categoryId $numberOfQuestion $level")
    val quizQuestions by quizViewModel.quizQuestions.collectAsState()
    val selectedOption by quizViewModel.selectedOption.collectAsState()
    val currentQuestionIndex by quizViewModel.currentQuestionIndex.collectAsState()


    LaunchedEffect(key1 = Unit) {
        quizViewModel.fetchData(categoryId, numberOfQuestion, level.lowercase(Locale.getDefault()))
    }



    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) {
        if (quizQuestions.isNotEmpty()) {
            val currentQuestion = quizQuestions[currentQuestionIndex]
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
                    .background(MaterialTheme.colorScheme.surface),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .background(
                            MaterialTheme.colorScheme.primary,
                            shape = RoundedCornerShape(bottomStart = 18.dp)
                        )
                        .padding(bottom = 4.dp),
                    contentAlignment = Alignment.Center
                ) {
                   CounterHeader(quizViewModel = quizViewModel, currentQuestionIndex =currentQuestionIndex )
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 14.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = "Question ${currentQuestionIndex + 1} of ${quizQuestions.size}",
                        style = TextStyle(
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.primary
                        )
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(3.8f)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 14.dp)
                    ) {
                        Text(
                            text = currentQuestion.question,
                            style = TextStyle(
                                fontSize = 25.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.primary
                            ),
                            modifier = Modifier
                                .padding(bottom = 24.dp)
                                .weight(3f)
                                .fillMaxWidth(),
                            overflow = TextOverflow.Visible
                        )
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(4.5f),
                            verticalArrangement = Arrangement.SpaceEvenly
                        ) {
                            currentQuestion.options.forEachIndexed { index, option ->
                                val isCorrect = currentQuestion.options[index] == currentQuestion.correctAnswer
                                val backgroundColor = when {
                                    selectedOption == index && isCorrect -> Color.Green
                                    selectedOption == index -> Color.Red
                                    else -> Color.White
                                }
                                OptionButton(
                                    option = option,
                                    backgroundColor = backgroundColor,
                                    context = context
                                ) {
                                    quizViewModel.selectedOption(index)
                                }
                            }
                        }
                    }
                }
                if(currentQuestionIndex==quizQuestions.size-1) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        contentAlignment = Alignment.BottomCenter
                    ) {
                        Button(
                            onClick = {

                                    navController.navigate(
                                        QuizResultScreenModel(
                                            category = categoryName,
                                            accuracy = DecimalFormat("#.##").format(quizViewModel.getAccuracy())
                                        )
                                    ) {
                                        navController.popBackStack()
                                    }

                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(250.dp)
                                .padding(horizontal = 18.dp, vertical = 42.dp),
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
                        ) {

                                Text(text = "Submit")
                        }
                    }
                }
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
                    .background(MaterialTheme.colorScheme.primary),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.surface
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun OptionButton(
    option: String,
    backgroundColor: Color,
    context: Context,
    onClick: () -> Unit
) {
    Log.d("vibrate", "QuizScreen:${backgroundColor} ${Color.Red} ")
    if(backgroundColor==Color.Red) {
        vibrate(context)
    }
    Card(
        elevation = CardDefaults.elevatedCardElevation(6.dp),
        colors = CardDefaults.cardColors(Color.White)
    ) {
        Button(
            onClick = onClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 8.dp)
                .background(backgroundColor, shape = RoundedCornerShape(8.dp)),
            colors = ButtonDefaults.buttonColors(backgroundColor)
        ) {
            Text(text = option, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.primary)
        }
    }
}

@Composable
fun DemoScreen() {
    var selectedOption by remember {
        mutableIntStateOf(5)
    }
    val totalTime = 30
    var timeLeft by remember { mutableIntStateOf(totalTime) }

    LaunchedEffect(key1 = 2) {
        timeLeft = totalTime // Reset the timer for each new question
    }

    LaunchedEffect(key1 = timeLeft) {
        if (timeLeft > 0) {
            delay(1000L) // delay for 1 second
            timeLeft--
        } else {
//            quizViewModel.nextQuestion()
        }
    }
    val currentQuestion =QuizQuestion(
        question = "Hi ",
        options = listOf("hi","hello","bye","kyi"),
        correctAnswer = "hello"
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(
                    MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(bottomStart = 18.dp)
                )
                .padding(bottom = 4.dp),
            contentAlignment = Alignment.Center
        ) {
            Card(
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .height(60.dp)
                    ,
                colors = CardDefaults.cardColors(Color.White),
                elevation = CardDefaults.elevatedCardElevation(6.dp)
            ) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp, vertical = 6.dp)
                ) {
                    CountdownTimer(timeLeft = timeLeft,totalTime = 30)
                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 6.dp)
        ) {
            Text(
                text = "Question 0 of 20",
                style = TextStyle(
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.primary
                )
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(3f)
        ) {
            Column (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp)
            ){
                Text(
                    text = currentQuestion.question,
                    style = TextStyle(
                        fontSize = 30.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.primary
                    ),
                    modifier = Modifier
                        .padding(bottom = 24.dp)
                        .weight(3f)
                        .fillMaxWidth(),
                    overflow = TextOverflow.Visible
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(4.5f),
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    currentQuestion.options.forEachIndexed { index, option ->
                        val isCorrect = currentQuestion.options[index] == currentQuestion.correctAnswer
                        val backgroundColor = when {
                            selectedOption == index && isCorrect -> Color.Green
                            selectedOption == index -> Color.Red
                            else -> Color.White
                        }

                    }
                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.8f),
            contentAlignment = Alignment.BottomCenter
        ){
           Button(
               onClick = { /*TODO*/ },
               modifier = Modifier
                   .fillMaxWidth()
                   .height(250.dp)
                   .padding(horizontal = 18.dp, vertical = 42.dp),
               shape = RoundedCornerShape(8),
               colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
           ) {
               Text(text = "Next")
           }
        }
    }

}

@RequiresApi(Build.VERSION_CODES.O)
fun vibrate(context: Context) {
    val vibrator = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
        val vibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
        vibratorManager.defaultVibrator
    } else {
        @Suppress("DEPRECATION")
        context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    }

    val vibrationEffect = VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE)
    vibrator.vibrate(vibrationEffect)
}


@Composable
fun CounterHeader(
    quizViewModel: QuizViewModel,
    currentQuestionIndex:Int
) {
    val totalTime = 30 // Set the total time for each question



    var timeLeft by remember { mutableIntStateOf(totalTime) }

    LaunchedEffect(key1 = currentQuestionIndex) {
        timeLeft = totalTime // Reset the timer for each new question
    }

    LaunchedEffect(key1 = timeLeft) {
        if (timeLeft > 0) {
            delay(1000L) // delay for 1 second
            timeLeft--
        } else {
            quizViewModel.nextQuestion()
        }
    }
    Card(
        modifier = Modifier
            .padding(horizontal = 12.dp)
            .height(60.dp),
        colors = CardDefaults.cardColors(Color.White),
        elevation = CardDefaults.elevatedCardElevation(6.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp, vertical = 6.dp)
        ) {
            CountdownTimer( timeLeft,  totalTime)
        }
    }

}

@Preview(showBackground = true)
@Composable
fun QuizScreenPreview() {
    DemoScreen()
}

