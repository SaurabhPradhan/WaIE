package org.nasa.apod_data

import org.nasa.apod_data.api.RetrofitBuilder
import org.nasa.apod_data.api.operation.DailyAPODOperation
import org.nasa.apod_data.datasource.APODDataSource
import org.nasa.apod_data.repository.APODRepository
import org.nasa.apod_domain.ErrorHandler

object DI {

    private val retrofit: RetrofitBuilder by lazy {
        RetrofitBuilder()
    }

    val genericHandler: ErrorHandler by lazy {
        GenericErrorHandler()
    }

    val dailyAPODOperation: DailyAPODOperation by lazy {
        retrofit.dailyOperation
    }

    val dailyAPODDataSource: APODDataSource by lazy {
        APODDataSource()
    }

    val dailyAPODRepository: APODRepository by lazy {
        APODRepository()
    }
}
