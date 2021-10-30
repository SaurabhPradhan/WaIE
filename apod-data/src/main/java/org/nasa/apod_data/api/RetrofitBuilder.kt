package org.nasa.apod_data.api

import okhttp3.OkHttpClient
import org.nasa.apod_data.BuildConfig
import org.nasa.apod_data.api.operation.DailyAPODOperation
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitBuilder {

    private val okHttpClient = OkHttpClient()
        .newBuilder()
        .readTimeout(1, TimeUnit.MINUTES)
        .build()

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BuildConfig.APOD_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val dailyOperation: DailyAPODOperation =
        retrofit.create(DailyAPODOperation::class.java)

}