package org.nasa.apod_data.api.operation

import org.nasa.apod_data.api.model.DailyAPOD
import retrofit2.http.GET
import retrofit2.http.Query

interface DailyAPODOperation {
    // returns apod data
    @GET("/apod")
    suspend fun getDailyAPOD(@Query("api_key") key: String): DailyAPOD
}