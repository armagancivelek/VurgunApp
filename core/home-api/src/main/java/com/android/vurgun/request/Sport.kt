package com.android.vurgun.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Sport(
    @SerialName("key")
    val key: String,
    @SerialName("group")
    val group: String,
    @SerialName("title")
    val title: String,
    @SerialName("description")
    val description: String,
    @SerialName("active")
    val active: Boolean,
    @SerialName("has_outrights")
    val hasOutrights: Boolean,
)