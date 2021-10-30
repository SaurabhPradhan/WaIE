package org.nasa.apod.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.nasa.apod.db.database.APODDataBase

class ViewModelFactory(private val dbHelper: APODDataBase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(APODViewModel::class.java)) {
            return APODViewModel(dbHelper) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}

