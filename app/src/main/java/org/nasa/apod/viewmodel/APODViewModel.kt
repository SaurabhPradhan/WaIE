package org.nasa.apod.viewmodel

import android.net.Network
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

    fun getDailyAPOD(isNetwork: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            if (isNetwork) {
                when (val result = ServiceInstance.dailyAPODService.getDailyAPOD()) {
                    is Result.Success -> {
                        db.podDao()?.insertDailyAPOD(result.data)
                        val dailyList = db.podDao()?.getDailAPOD()
                        checkData(dailyList)
                    }
                    is Result.Error -> {
                        val dailyList = db.podDao()?.getDailAPOD()
                        checkData(dailyList)
                    }
                }
            } else {
                checkData(db.podDao()?.getDailAPOD())
            }
        }
    }

    private fun checkData(dailyList: List<DailyAPOD>?) {
        val dailyAPODData = dailyList?.find { it1 ->
            it1.date.toDate() == System.currentTimeMillis().toDate()
        }
        if (dailyAPODData == null) {
            lastSeenData.postValue(dailyList?.find { it1 ->
                it1.date.toDate() == System.currentTimeMillis().toPrevDate()
            })
        } else {
            dailyData.postValue(dailyAPODData)
        }
    }
}