package com.example.quizapp.screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.quizapp.component.BottomBarScreen
import com.example.quizapp.component.MyBottomNavigationBar
import com.example.quizapp.component.NewBottomBarNavGraph
import com.example.quizapp.ui.theme.QuizAppTheme

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreen() {
    val navController= rememberNavController()


    Scaffold {

        NewBottomBarNavGraph(
            navController = navController,
            paddingValues = it,
        )
    }
}



@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun MainPreview() {
    QuizAppTheme {
        Surface {
            MainScreen()
        }
    }
}

