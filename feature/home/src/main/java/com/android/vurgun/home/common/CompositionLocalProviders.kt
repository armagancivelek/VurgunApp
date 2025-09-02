package com.android.vurgun.home.common

import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf
import com.valentinilk.shimmer.Shimmer

internal val LocalHomeContentShimmer: ProvidableCompositionLocal<Shimmer> = provideCompositionLocal()

private inline fun <reified C> provideCompositionLocal() = compositionLocalOf<C> {
    error("No ${C::class} provided")
}