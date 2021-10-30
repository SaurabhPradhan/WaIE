package org.nasa.apod.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import org.nasa.apod.util.TimeStampConverter
import java.util.*

@Entity(tableName = "dailyAPOD")
data class DailyAPOD(
    @PrimaryKey
    val date: Date,
    val title: String,
    val imgUrl: String,
    val description: String
)
