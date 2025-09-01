package com.android.vurgun.network.data

import kotlinx.serialization.Serializable

/**
 *  Represents a network error response from the server.
 */
@Serializable
data class NetworkErrorResponse(
    val details_url: String?,
    val error_code: String?,
    val message: String?
)