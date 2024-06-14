package com.example.quizapp.component

import com.example.quizapp.R
import com.example.quizapp.screen.AccountScreen
import com.example.quizapp.screen.CategoryDetailsScreen
import com.example.quizapp.screen.HistoryScreen
import com.example.quizapp.screen.HomeScreen
import com.example.quizapp.screen.QuizQuestionScreen

sealed class BottomBarScreen (
    val route:Any,
    val title:String,
    val icon:Int
){
     object Home:BottomBarScreen(
        route = HomeScreen,
        title = "Home",
        icon= R.drawable.outline_home_24
    )
     object History:BottomBarScreen(
        route = HistoryScreen,
        title = "History",
        icon= R.drawable.outline_history_edu_24
    )
     object Account:BottomBarScreen(
        route = AccountScreen,
        title = "Account",
        icon= R.drawable.rounded_account_circle_24
    )
    object QuizInfo:BottomBarScreen(
        route = CategoryDetailsScreen,
        title = "Category",
        icon= R.drawable.rounded_account_circle_24
    )
    object QuizScreen:BottomBarScreen(
        route = QuizQuestionScreen,
        title = "Quiz",
        icon= R.drawable.rounded_account_circle_24
    )

}