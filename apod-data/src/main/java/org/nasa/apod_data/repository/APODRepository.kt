package org.nasa.apod_data.repository

import org.nasa.apod_data.DI
import org.nasa.apod_domain.entity.APODEntity
import org.nasa.apod_domain.Result
import org.nasa.apod_domain.parameters.APODParameter
import org.nasa.apod_domain.repository.Repository

class APODRepository() : Repository<APODParameter, APODEntity> {

    override suspend fun get(params: APODParameter): Result<APODEntity> {
        return DI.dailyAPODDataSource.get(params)
    }
}