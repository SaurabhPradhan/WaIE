package org.nasa.apod_domain.entity

data class APODEntity(
    val copyright: String,
    val date: String,
    val explanation: String,
    val hdImgUrl: String,
    val mediaType: String,
    val apiVersion: String,
    val title: String,
    val imgUrl: String
)