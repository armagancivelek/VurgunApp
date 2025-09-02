package com.android.vurgun.common_ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import com.android.vurgun.common_ui.R
import com.valentinilk.shimmer.defaultShimmerTheme

private val lightScheme = lightColorScheme(
    primary = primaryLight,
    onPrimary = primaryLight,
    onSecondary = Black700,
    onSecondaryContainer = White800,
)

private val darkScheme = darkColorScheme(
    primary = primaryDark,
    onPrimary = primaryDark,
    onSecondary = White50,
    onSecondaryContainer = White800,
)

@Composable
fun BaseAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit,
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> darkScheme
        else -> lightScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.setBackgroundDrawableResource(R.drawable.window_background)
            window.navigationBarColor = colorScheme.primary.toArgb()
            window.statusBarColor = Color.Transparent.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    // Background theme
    val defaultBackgroundTheme = BackgroundTheme(
        color = colorScheme.surface,
        tonalElevation = 2.dp,
    )

    val defaultContainerShimmerTheme = defaultShimmerTheme.copy(
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
                delayMillis = 400,
                easing = LinearEasing,
            ),
            repeatMode = RepeatMode.Restart,
        ),
        shaderColors = listOf(
            Color.Unspecified.copy(alpha = 0.45f),
            Color.Unspecified.copy(alpha = 1.00f),
            Color.Unspecified.copy(alpha = 0.45f),
        ),
    )

    val defaultContentShimmerTheme = defaultShimmerTheme.copy(
        blendMode = BlendMode.ColorBurn,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
                delayMillis = 400,
                easing = LinearEasing,
            ),
            repeatMode = RepeatMode.Restart,
        ),
        shaderColors = listOf(
            Color.Gray.copy(alpha = 0.25f),
            Color.Gray.copy(alpha = 0.10f),
            Color.Gray.copy(alpha = 0.25f),
        ),
    )

    CompositionLocalProvider(
        LocalBackgroundTheme provides defaultBackgroundTheme,
        LocalContainerShimmerTheme provides defaultContainerShimmerTheme,
        LocalContentShimmerTheme provides defaultContentShimmerTheme,
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = AppTypography,
            content = content,
        )
    }
}