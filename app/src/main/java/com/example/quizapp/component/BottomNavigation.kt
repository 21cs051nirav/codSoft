package com.example.quizapp.component

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.quizapp.ui.theme.QuizAppTheme





@Composable
fun MyBottomNavigationBar(navController:NavHostController) {
    val screens= listOf(
        BottomBarScreen.Home,
        BottomBarScreen.History,
        BottomBarScreen.Account
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination= navBackStackEntry?.destination
        Card(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(50),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                screens.forEach { screen->
                    Log.d("Bottom bar ", "BottomBar: $screen $currentDestination")
                    if (currentDestination != null) {
                        BottomNavigationItem(screen = screen, currentDestination = currentDestination, navController =navController )
                    }else{
                        BottomNavigationItem(screen = screen, currentDestination = NavDestination(BottomBarScreen.Home.title), navController =navController )
                    }
                }
            }
        }

}

@Composable
fun BottomNavigationItem(
    screen: BottomBarScreen,
    currentDestination:NavDestination,
    navController: NavController
) {
    IconButton(onClick = {
        if(screen.route==BottomBarScreen.Home.route) navController.popBackStack()
        navController.navigate(screen.route){
            if(currentDestination.route!=BottomBarScreen.Home.route)navController.popBackStack()
        }
    }) {
        Icon(
            painter = painterResource(id = screen.icon),
            contentDescription = screen.title,
            tint = if (currentDestination.hierarchy.any { it.route==screen.route }) Color(0xff31304d) else Color.Gray,
        )
    }
}

@Preview
@Composable
private fun BottomBarPreview() {
        QuizAppTheme {

                MyBottomNavigationBar(rememberNavController())

        }   
}