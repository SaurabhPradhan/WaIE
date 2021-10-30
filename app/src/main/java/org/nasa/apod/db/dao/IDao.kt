package org.nasa.apod.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import org.nasa.apod.model.DailyAPOD

@Dao
interface IDao {

    @Query("SELECT * FROM dailyAPOD")
    suspend fun getDailAPOD(): List<DailyAPOD>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDailyAPOD(date: DailyAPOD)
}