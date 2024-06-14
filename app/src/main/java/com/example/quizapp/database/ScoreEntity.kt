package com.example.quizapp.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ScoreEntity(
    @PrimaryKey(autoGenerate = true) val id:Int,
   @ColumnInfo(name = "Category") val category:String,
    @ColumnInfo(name = "High Accuracy") val highScore:String,
    @ColumnInfo (name = "Last Accuracy") val lastScore:String,
)