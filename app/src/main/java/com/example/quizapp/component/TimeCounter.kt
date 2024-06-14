package com.example.quizapp.component

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

//@Composable
//fun CountdownTimer(
//    totalTime: Int, // total time in seconds
//    onTimeUp: () -> Unit
//) {
//    var timeLeftOfQuestion by rememberSaveable {
//        mutableIntStateOf(totalTime)
//    }
//    val progress by animateFloatAsState(
//        targetValue = timeLeftOfQuestion / totalTime.toFloat(),
//        animationSpec = tween(durationMillis = 1000, easing = LinearEasing), label = ""
//    )
//
//    LaunchedEffect(key1 = timeLeftOfQuestion) {
//        if (timeLeftOfQuestion > 0) {
//            delay(1000L) // delay for 1 second
//            timeLeftOfQuestion--
//        } else {
//            onTimeUp()
//        }
//    }
//
//    Column(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(2.dp)
//            ,
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.Start
//    ) {
//        Text(
//            text = "Timer: ${timeLeftOfQuestion}s",
//            style = MaterialTheme.typography.bodySmall,
//            color = MaterialTheme.colorScheme.primary,
//            modifier = Modifier.padding(vertical = 4.dp, horizontal = 2.dp)
//        )
//        LinearProgressIndicator(
//            progress = progress,
//            color = MaterialTheme.colorScheme.primary,
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(14.dp)
//                .border(1.dp, Color.Black, RoundedCornerShape(50)),
//            strokeCap = StrokeCap.Round
//        )
//    }
//}
@Composable
fun CountdownTimer(
    timeLeft: Int,
    totalTime: Int // total time in seconds
) {
    val progress by animateFloatAsState(
        targetValue = timeLeft / totalTime.toFloat(),
        animationSpec = tween(durationMillis = 1000, easing = LinearEasing)
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Timer: ${timeLeft}s",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(vertical = 4.dp, horizontal = 2.dp)
        )
        LinearProgressIndicator(
            progress = progress,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .fillMaxWidth()
                .height(14.dp)
                .border(1.dp, Color.Black, RoundedCornerShape(50)),
            strokeCap = StrokeCap.Round
        )
    }
}

@Composable
fun ProgressScore(
    title:String,
    number: Int,
    total:Int
) {
    var color:Color;
    if(title=="Correct"){
       color= Color.Green
    }else if(title=="Wrong"){
        color=Color.Red
    }else{
        color=Color.DarkGray
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 6.dp)
        ,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "$title : $number",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(vertical = 4.dp, horizontal = 2.dp)
        )
        LinearProgressIndicator(
            progress = number/(total*1f),
            color = color,
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp)
                .border(1.dp, Color.Black, RoundedCornerShape(50)),
            strokeCap = StrokeCap.Round
        )
    }
}
@Preview
@Composable
private fun CountdownTimerPreview() {
    Column {
        ProgressScore(title = "Correct", number = 10, total = 15)
        ProgressScore(title = "Wrong", number = 3, total = 15)
        ProgressScore(title = "Miss", number = 2, total = 15)
    }
}