package org.nasa.apod.service

object ServiceInstance {

    val dailyAPODService: DailyAPODService by lazy {
        DailyAPODService()
    }
}
