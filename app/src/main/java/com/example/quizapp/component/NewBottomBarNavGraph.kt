package com.example.quizapp.component

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.quizapp.data.QuizCategory
import com.example.quizapp.screen.AccountScreen
import com.example.quizapp.screen.CategoryDetails
import com.example.quizapp.screen.CategoryDetailsScreen
import com.example.quizapp.screen.HistoryScreen
import com.example.quizapp.screen.HomeScreen
import com.example.quizapp.screen.QuizQuestionScreen
import com.example.quizapp.screen.QuizResultScreen
import com.example.quizapp.screen.QuizResultScreenModel
import com.example.quizapp.screen.QuizScreen
import com.google.gson.Gson

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NewBottomBarNavGraph (
    navController: NavHostController,
    paddingValues: PaddingValues,

){
    NavHost(
        navController = navController,
        startDestination = HomeScreen
    ){
        composable<HomeScreen>{
            HomeScreen(
                navController = navController,

            )
        }
        composable<CategoryDetailsScreen>{
            val arg=it.toRoute<CategoryDetailsScreen>()

            Log.d("QuizCategory", "NewBottomBarNavGraph: $arg")
            CategoryDetails(arg.quizId,arg.categoryName,navController)

        }
        composable<QuizQuestionScreen>{
           val args=it.toRoute<QuizQuestionScreen>()
            QuizScreen(categoryId = args.categoryId, categoryName = args.categoryName, numberOfQuestion = args.numberOfQuestion, navController = navController,level = args.level)
        }
        composable<QuizResultScreenModel> {
            val arg=it.toRoute<QuizResultScreenModel>()
            QuizResultScreen(category = arg.category, totalAccuracy = arg.accuracy)
        }
        composable<HistoryScreen>{
              HistoryScreen()
        }
        composable<AccountScreen>{
            AccountScreen(paddingValues = paddingValues)
        }
    }
}