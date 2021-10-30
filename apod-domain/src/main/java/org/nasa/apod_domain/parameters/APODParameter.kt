package org.nasa.apod_domain.parameters

sealed class APODParameter {

    data class DailyAPOD(val apiKey: String) : APODParameter()
}
