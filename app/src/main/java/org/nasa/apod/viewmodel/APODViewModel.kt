package org.nasa.apod.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.nasa.apod.db.database.APODDataBase
import org.nasa.apod.mapper.toDate
import org.nasa.apod.mapper.toPrevDate
import org.nasa.apod.model.DailyAPOD
import org.nasa.apod.service.ServiceInstance
import org.nasa.apod_domain.Result

class APODViewModel(private val db: APODDataBase) : ViewModel() {

    val dailyData = MutableLiveData<DailyAPOD>()
    val lastSeenData = MutableLiveData<DailyAPOD>()

    fun getDailyAPOD() {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = ServiceInstance.dailyAPODService.getDailyAPOD()) {
                is Result.Success -> {
                    db.podDao()?.insertDailyAPOD(result.data)
                    dailyData.value = db.podDao()?.getDailAPOD()
                        ?.find { it1 -> it1.date.toDate() == System.currentTimeMillis().toDate() }
                }
                is Result.Error -> {
                    val dailyList = db.podDao()?.getDailAPOD()
                    val data = dailyList?.find { it1 ->
                        it1.date.toDate() == System.currentTimeMillis().toDate()
                    }
                    if (data == null) {
                        lastSeenData.value = dailyList?.find { it1 ->
                            it1.date.toDate() == System.currentTimeMillis().toPrevDate()
                        }
                    } else {
                        dailyData.value = data
                    }
                }
            }
        }
    }
}