package com.android.vurgun.common_ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import com.valentinilk.shimmer.ShimmerTheme
import com.valentinilk.shimmer.defaultShimmerTheme

/**
 * A composition local container for [ShimmerTheme].
 */
val LocalContainerShimmerTheme = staticCompositionLocalOf { defaultShimmerTheme }

/**
 * A composition local content for [ShimmerTheme].
 */
val LocalContentShimmerTheme = staticCompositionLocalOf { defaultShimmerTheme }