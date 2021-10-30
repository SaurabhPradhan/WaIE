package org.nasa.apod.util

import android.util.Log
import androidx.room.TypeConverter
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object TimeStampConverter {
    private const val TAG = "TimeStampConverter"
    private const val TIME_STAMP_FORMAT = "yyyy-MM-dd"

    var df: DateFormat = SimpleDateFormat(TIME_STAMP_FORMAT, Locale.getDefault())

    @TypeConverter
    fun timeToDate(value: String?): Date? {
        return if (value != null) {
            try {
                return df.parse(value)
            } catch (e: ParseException) {
                Log.e(TAG, e.message.toString())
            }
            null
        } else {
            null
        }
    }

    @TypeConverter
    fun dateToTime(value: Date?): String? {
        return if (value != null) {
            df.format(value)
        } else {
            null
        }
    }

}