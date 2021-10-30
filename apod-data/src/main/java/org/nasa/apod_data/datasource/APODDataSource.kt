package org.nasa.apod_data.datasource

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.nasa.apod_data.DI
import org.nasa.apod_domain.Result
import org.nasa.apod_data.api.extensions.toAPODEntity
import org.nasa.apod_domain.datasource.Datasource
import org.nasa.apod_domain.entity.APODEntity
import org.nasa.apod_domain.parameters.APODParameter

class APODDataSource : Datasource<APODParameter, APODEntity> {

    override suspend fun get(parameters: APODParameter): Result<APODEntity> {
        when (parameters) {
            is APODParameter.DailyAPOD ->
                return try {
                    withContext(Dispatchers.IO) {
                        Result.Success( DI.dailyAPODOperation.getDailyAPOD(parameters.apiKey).toAPODEntity())
                    }
                } catch (e: Throwable) {
                    DI.genericHandler.getError(e)
                }
        }
    }
}
