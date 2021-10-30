package org.nasa.apod.mapper

import org.nasa.apod.model.DailyAPOD
import org.nasa.apod_domain.entity.APODEntity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun APODEntity.toDailyAPOD(): DailyAPOD {
    return DailyAPOD(
        date = SimpleDateFormat("yyyy-MM-dd", Locale.US).parse(date) ?: Date(),
        title = title,
        imgUrl = hdImgUrl,
        description = explanation
    )
}

fun Long.toDate(): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.US);
    return sdf.format(Date(System.currentTimeMillis()))
}

fun Date.toDate(): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.US);
    return sdf.format(this)
}

fun Long.toPrevDate(): String {
    val ONE_DAY_MILLI_SECONDS = 24 * 60 * 60 * 1000
    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.US);
    return sdf.format(Date(System.currentTimeMillis() - ONE_DAY_MILLI_SECONDS))
}