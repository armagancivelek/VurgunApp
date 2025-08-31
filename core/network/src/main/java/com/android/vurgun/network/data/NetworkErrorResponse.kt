package com.android.vurgun.network.data

import kotlinx.serialization.Serializable

/**
 *
 * It should be edited according to the error response returned from the body.
 * @param errors A list of error messages
 */
@Serializable
data class NetworkErrorResponse(
    val errors: List<String>?,
)
