package com.example.quizapp.screen

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quizapp.R
import com.example.quizapp.ui.theme.QuizAppTheme


@Composable
fun QuizResultScreen(
    category:String,
   totalAccuracy:String
) {
    val context=LocalContext.current
  Column(
      modifier = Modifier
          .fillMaxSize()
          .background(MaterialTheme.colorScheme.primary)
  ) {
      Box(
          modifier = Modifier
              .fillMaxWidth()
              .padding(vertical = 12.dp)
              .weight(1f)
      ) {
          Row(
              modifier = Modifier.fillMaxHeight()
          ) {
              Box(
                  modifier = Modifier
                      .fillMaxHeight()
                      .weight(1f),
                  contentAlignment = Alignment.Center
              ) {
                  Text(
                      text = "Result",
                      color = MaterialTheme.colorScheme.onPrimary,
                      fontSize = 38.sp,
                      fontWeight = FontWeight.SemiBold,
                      modifier = Modifier.fillMaxWidth(),
                      textAlign = TextAlign.Center
                  )
              }

          }
      }
      Column(
          modifier = Modifier
              .fillMaxSize()
              .weight(6f)
              .background(
                  MaterialTheme.colorScheme.surface,
                  shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)
              ),

      ) {
         Card (
             modifier = Modifier.padding(12.dp),
             colors = CardDefaults.cardColors(Color.White),
             elevation = CardDefaults.elevatedCardElevation(12.dp),
         ){
             Box(
                 modifier = Modifier
                     .fillMaxWidth()
                     .padding(top = 40.dp)
                 ,
                 contentAlignment = Alignment.Center
             ) {
                 Image(
                     painter = painterResource(id = R.drawable.accuracy),
                     contentDescription = "Accuracy",
                     modifier = Modifier.size(150.dp)
                 )
             }
             Box(
                 modifier = Modifier
                     .padding(15.dp)
                     .fillMaxWidth(),
                 contentAlignment = Alignment.TopCenter
             ) {
                 Column(
                     modifier = Modifier.fillMaxHeight(.3f),
                     verticalArrangement = Arrangement.SpaceEvenly
                 ) {
                     Text(
                         text = "Category: $category",
                         style = TextStyle(
                             fontSize = 26.sp,
                             fontWeight = FontWeight.SemiBold
                         )
                     )
                     Text(
                         text = "Accuracy: $totalAccuracy %",
                         style = TextStyle(
                             fontSize = 20.sp,
                             fontWeight = FontWeight.SemiBold
                         )
                     )
                     Text(
                         text = "Last Accuracy: $totalAccuracy %",
                         style = TextStyle(
                             fontSize = 20.sp,
                             fontWeight = FontWeight.SemiBold
                         )
                     )
                     Text(
                         text = "High Accuracy: $totalAccuracy %",
                         style = TextStyle(
                             fontSize = 20.sp,
                             fontWeight = FontWeight.SemiBold
                         )
                     )
                 }
             }
         }
          Card (
              modifier = Modifier.padding(12.dp),
              colors = CardDefaults.cardColors(Color.White),
              elevation = CardDefaults.elevatedCardElevation(12.dp),
              ){
              Column(
                  modifier = Modifier
                      .fillMaxSize()
                      .padding(35.dp),
                 verticalArrangement = Arrangement.SpaceBetween
              ) {
                  Text(
                      text = "Quiz question provided by Open Trivia DB",
                      style = TextStyle(
                          fontSize = 24.sp,
                          fontWeight = FontWeight.Medium
                      ),
                      textAlign = TextAlign.Center,
                  )
                  Text(
                      text = "Website: https://opentdb.com/",
                      modifier = Modifier.pointerInput(Unit){
                          detectTapGestures (
                              onLongPress = {
                                  copyToClipboard(context = context,"https://opentdb.com/")
                                  Toast.makeText(context,"Text copied to clipboard", Toast.LENGTH_SHORT).show()
                              }
                          )
                      }
                  )
              }

          }
      }
  }
}

private fun copyToClipboard(context: Context, text: String) {
    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("Copied Text", text)
    clipboard.setPrimaryClip(clip)
}
@Preview
@Composable
private fun QuizResultScreenPreview() {
    QuizAppTheme {
        Surface {
            QuizResultScreen(category = "Sport", totalAccuracy = "83.0")
        }
    }
}