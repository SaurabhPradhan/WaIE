package org.nasa.apod.db.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import org.nasa.apod.db.dao.IDao
import org.nasa.apod.model.DailyAPOD
import org.nasa.apod.util.TimeStampConverter

@Database(
    entities = [DailyAPOD::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(TimeStampConverter::class)
abstract class APODDataBase : RoomDatabase() {
    abstract fun podDao(): IDao?

    companion object : SingletonHolder<APODDataBase, Context>({
        Room.databaseBuilder(it.applicationContext, APODDataBase::class.java, "apod.db")
            .allowMainThreadQueries().build()
    })
}
