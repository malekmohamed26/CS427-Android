package com.example.carbooking.network

import com.squareup.moshi.Json
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * This data class defines a Mars photo which includes an ID, and the image URL.
 */

data class MarsPhoto(
    val id: String,
    @Json(name = "img_src") val imgSrcUrl: String
)