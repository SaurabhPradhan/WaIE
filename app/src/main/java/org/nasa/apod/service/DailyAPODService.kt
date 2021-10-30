package org.nasa.apod.service

import android.R.attr
import org.nasa.apod.BuildConfig
import org.nasa.apod.mapper.toDailyAPOD
import org.nasa.apod.model.DailyAPOD
import org.nasa.apod_domain.Result
import org.nasa.apod_data.DI
import org.nasa.apod_domain.parameters.APODParameter

class DailyAPODService {

    suspend fun getDailyAPOD(): Result<DailyAPOD> {
        return when (val result =
            DI.dailyAPODRepository.get(APODParameter.DailyAPOD(BuildConfig.api_Key))) {
            is Result.Success -> return Result.Success(result.data.toDailyAPOD())
            is Result.Error -> result
            else -> throw java.lang.UnsupportedOperationException("")
        }
    }
}