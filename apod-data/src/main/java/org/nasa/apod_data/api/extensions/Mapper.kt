package org.nasa.apod_data.api.extensions

import org.nasa.apod_data.api.model.DailyAPOD
import org.nasa.apod_domain.entity.APODEntity

fun DailyAPOD.toAPODEntity(): APODEntity {
    return APODEntity(
        copyright = copyright,
        date = date,
        explanation = explanation,
        hdImgUrl = hdurl,
        mediaType = media_type,
        apiVersion = service_version,
        title = title,
        imgUrl = url
    )
}