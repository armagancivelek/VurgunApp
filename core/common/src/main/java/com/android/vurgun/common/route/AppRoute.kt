package com.android.vurgun.common.route

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
sealed interface AppRoute {

    @Serializable
    data object HomeRoute : AppRoute

    @Serializable
    data object CurrentSlipRoute : AppRoute

    @Serializable
    data object SlipsRoute : AppRoute

    @Serializable
    data class SportEventsRoute(val sportKey: String) : AppRoute
}