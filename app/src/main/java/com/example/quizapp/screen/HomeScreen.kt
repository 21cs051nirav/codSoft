package com.example.quizapp.screen

import android.content.res.Configuration
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key.Companion.Back
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.quizapp.R
import com.example.quizapp.component.BottomBarScreen
import com.example.quizapp.component.CategoryCard
import com.example.quizapp.ui.theme.QuizAppTheme
import com.example.quizapp.viewModel.HomeScreenViewMode
import com.google.gson.Gson

@Composable
fun HomeScreen(
    navController: NavController,
    homeScreenViewMode: HomeScreenViewMode= viewModel(),

) {
    val quizCategory by homeScreenViewMode.quizCategory.collectAsState()

    Column(
        modifier = Modifier
            .background(
                MaterialTheme.colorScheme.primary
            )
    ){
        Box(modifier = Modifier
            .weight(2f)
            .fillMaxWidth()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 12.dp, horizontal = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    text = "QuizIt",
                    style = TextStyle(
                        fontSize = 36.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    ),
                    modifier = Modifier.weight(2f)
                )
                Row(
                    Modifier
                        .fillMaxWidth()
                        .weight(3f),
                    verticalAlignment = Alignment.CenterVertically
                )
                {
                    Column(
                        modifier = Modifier.fillMaxHeight(),
                        verticalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Text(
                            text = stringResource(R.string.challenge_your_knowledge),
                            style = TextStyle(
                                fontSize = 18.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.White
                            )
                        )
                        Text(
                            text = stringResource(R.string.play_quiz_game_and_check_your_knowledge),
                            style = TextStyle(
                                fontSize = 10.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.White
                            )
                        )
                        Button(
                                  onClick = {

                                      navController.navigate(QuizQuestionScreen(
                                          9,
                                          categoryName = "Random",
                                          20,
                                          "easy"
                                      ))
                                            },
                                  colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Color(0xff31304d)),
                                  shape = RoundedCornerShape(10)
                        ) {
                                  Text(text = stringResource(R.string.start_now))
                        }
                    }

                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                     Image(
                         painter = painterResource(id = R.drawable.trophy64), contentDescription ="trophy",
                         modifier = Modifier.size(85.dp)
                     )
                   }


                }
            }
        }
        Box(
            modifier = Modifier
                .weight(5f)
                .background(
                    Color(0xffEFEFEF),
                    RoundedCornerShape(topStart = 48.dp)
                )
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 35.dp, start = 18.dp, end = 18.dp)
            ) {
                Row (
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    Text(
                        text = stringResource(R.string.explore_quizzes),
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xff31304d)
                        )
                    )
                    Text(
                        text = stringResource(R.string.view_all),
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xff0B5FA8)
                        )
                    )
                }
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 20.dp)
                ) {
                    items(quizCategory){
                        Box(
                            Modifier.padding(12.dp)
                        ) {
                            CategoryCard(icon = it.img, title = it.title) {

                                val json= Uri.encode(Gson().toJson(it))
                                Log.d("Encoded Json", "HomeScreen: $json")
                                navController.navigate(CategoryDetailsScreen(
                                    quizId = it.id,
                                    categoryName = it.title
                                ))
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showSystemUi = true)
@Composable
private fun HomeScreenPreview() {
    QuizAppTheme {
        Surface {
            HomeScreen(rememberNavController())
        }
    }
}