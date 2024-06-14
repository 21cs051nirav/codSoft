package com.example.quizapp.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.quizapp.R
import com.example.quizapp.data.QuizCategory


@Composable
fun CategoryDetails(
    quizId:Int,
    quizCategoryName:String,
    navController: NavController,
) {
    var selectedNumberOfQuestion by rememberSaveable {
        mutableStateOf("1-5")
    }
    var selectedQuizLevel by remember {
        mutableStateOf("Easy")
    }
    val listOfLevel= listOf("Mixed","Easy","Medium","Hard")
    val numberOfQuestion= listOf("1-5","1-10","1-20","1-30")
    Scaffold { it ->
          Column(
              modifier = Modifier
                  .padding(it)
                  .fillMaxSize()
                  .background(MaterialTheme.colorScheme.primary)
          ) {
               Box(
                   modifier = Modifier
                       .weight(1f)
                       .fillMaxWidth(),
                   contentAlignment = Alignment.Center
               ) {
                   Text(
                       text = quizCategoryName,
                       style = TextStyle(
                           fontSize = 35.sp,
                           fontWeight = FontWeight.Bold,
                           color = Color.White,
                           fontFamily = FontFamily.SansSerif
                       )
                   )
               }
              Box(
                  modifier = Modifier
                      .fillMaxWidth()
                      .weight(6f)
                      .background(
                          MaterialTheme.colorScheme.surface,
                          RoundedCornerShape(topStart = 35.dp)
                      )
              ) {
                 Column {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(5f)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 14.dp, vertical = 16.dp)

                        ) {
                            Text(
                                text = stringResource(R.string.level_of_quiz),
                                fontSize = 25.sp,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.padding(vertical = 12.dp),
                                color = MaterialTheme.colorScheme.primary
                            )
                            LazyVerticalGrid(
                                columns = GridCells.Fixed(3),
                                contentPadding = PaddingValues(8.dp)
                            ) {
                                items(listOfLevel.size){
                                    Box (
                                        modifier = Modifier.padding(2.dp)
                                    ){
                                        SelectCard(title = listOfLevel[it],selectedQuizLevel) {
                                            selectedQuizLevel=listOfLevel[it]
                                        }
                                    }
                                }
                            }
                            Text(
                                text = stringResource(R.string.number_of_question),
                                fontSize = 25.sp,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.padding(vertical = 12.dp),
                                color = MaterialTheme.colorScheme.primary
                            )
                            LazyVerticalGrid(
                                columns = GridCells.Fixed(3),

                            ) {
                                items(numberOfQuestion.size){
                                    Box (
                                        modifier = Modifier.padding(2.dp)
                                    ){
                                        SelectCard(title = numberOfQuestion[it],selectedNumberOfQuestion) {
                                            selectedNumberOfQuestion=numberOfQuestion[it]
                                        }
                                    }
                                }
                            }
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(4f)
                                    .padding(12.dp),
                                elevation = CardDefaults.elevatedCardElevation(6.dp)
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(8.dp)
                                ) {
                                    Text(
                                        text = stringResource(R.string.quiz_information),
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        modifier = Modifier.padding(vertical = 12.dp)
                                    )
                                    Text(
                                        text = "Difficulty Level: $selectedQuizLevel ",
                                        fontWeight = FontWeight.Medium,
                                        fontSize = 12.sp
                                    )
                                    Text(
                                        text = "Number of question: $selectedNumberOfQuestion",
                                        fontWeight = FontWeight.Medium,
                                        fontSize = 12.sp
                                    )
                                }
                            }
                        }
                    }
                     Box (
                         modifier = Modifier
                             .fillMaxWidth()
                             .weight(1f)
                     ){
                         Button(
                             onClick = {
                                       navController.navigate(QuizQuestionScreen(
                                           categoryId = quizId,
                                           categoryName = quizCategoryName,
                                           numberOfQuestion = selectedNumberOfQuestion.split("-").last().toInt(),
                                           level = selectedQuizLevel
                                       ))
                                       },
                             modifier = Modifier
                                 .fillMaxWidth()
                                 .padding(12.dp),
                             shape = RoundedCornerShape(8)
                         ) {
                             Text(
                                 text = "Start",
                                 style = TextStyle(
                                     fontSize = 20.sp,
                                     fontWeight = FontWeight.SemiBold
                                 )
                             )
                         }
                     }
                 }
              }
          }
      }
}


@Preview
@Composable
private fun CategoryDetailsPreview() {
    Surface {
        CategoryDetails(17,"Sport",rememberNavController())
    }
}

@Composable
fun SelectCard(
    title:String,
    selectedLevel:String,
    onClick:()->Unit
) {
   val selected =selectedLevel==title
    AssistChip(
        onClick = {
            onClick()
                  },
        colors=if(selected)AssistChipDefaults.assistChipColors(Color.Green) else AssistChipDefaults.assistChipColors(Color.Transparent),
        label = { Text(
            title,
            color = Color.Black
        ) },
        leadingIcon = {
            Icon(
                if(selected) Icons.Filled.Check else Icons.Filled.Add,
                contentDescription = "Localized description",
                modifier = Modifier.size(AssistChipDefaults.IconSize),
                tint = Color.Black
            )
        }
    )
}
