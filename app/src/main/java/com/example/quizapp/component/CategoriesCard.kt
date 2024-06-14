package com.example.quizapp.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon.Companion.Text
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign

import androidx.compose.ui.unit.dp
import com.example.quizapp.R
import java.nio.file.WatchEvent

@Composable
fun CategoriesCard(
    name:String,
    img:Int,
    onClick:()->Unit
) {
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.height(90.dp)
    ) {
        ElevatedCard(
            shape = RoundedCornerShape(40),
            modifier = Modifier
                .size(60.dp)
                .border(2.dp, color = Color.Gray, shape = RoundedCornerShape(40))
        ) {
            Image(painter = painterResource(id = img), contentDescription = "Science")
        }
       Text(
           text = name,
           textAlign = TextAlign.Center,
           style = MaterialTheme.typography.bodySmall
       )
    }
}
