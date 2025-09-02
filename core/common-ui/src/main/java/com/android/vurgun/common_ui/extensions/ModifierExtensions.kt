package com.android.vurgun.common_ui.extensions

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import com.valentinilk.shimmer.Shimmer
import com.valentinilk.shimmer.shimmer

@SuppressLint("ComposableNaming")
@Composable
fun Modifier.noRippleClickable(onClick: () -> Unit) = composed {
    this.clickable(
        interactionSource = remember { MutableInteractionSource() },
        indication = null,
    ) { onClick() }
}

@SuppressLint("SuspiciousModifierThen")
fun Modifier.shimmerWhen(
    enabled: Boolean,
    shimmerInstance: Shimmer,
) = this.then(
    if (enabled) {
        shimmer(customShimmer = shimmerInstance)
    } else {
        this
    },
)