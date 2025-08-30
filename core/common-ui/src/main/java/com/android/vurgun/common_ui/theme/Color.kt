package com.android.vurgun.common_ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val White900 = Color(0xFF616161)
val White800 = Color(0xFF7F7F7F)
val White700 = Color(0xFFA3A3A3)
val White600 = Color(0xFFD1D1D1)
val White500 = Color(0xFFE6E6E6)
val White400 = Color(0xFFEBEBEB)
val White300 = Color(0xFFEEEEEE)
val White200 = Color(0xFFF4F4F4)
val White100 = Color(0xFFF7F7F7)
val White50 = Color(0xFFFDFDFD)

val Black900 = Color(0xFF000000)
val Black800 = Color(0xFF000000)
val Black700 = Color(0xFF000000)
val Black600 = Color(0xFF000000)
val Black500 = Color(0xFF000000)
val Black400 = Color(0xFF333333)
val Black300 = Color(0xFF545454)
val Black200 = Color(0xFF8A8A8A)
val Black100 = Color(0xFFB0B0B0)
val Black50 = Color(0xFFE6E6E6)

val Blue900 = Color(0xFF023D6B)
val Blue800 = Color(0xFF02508C)
val Blue700 = Color(0xFF0368B5)
val Blue600 = Color(0xFF0485E8)
val Blue500 = Color(0xFF0492FF)
val Blue400 = Color(0xFF36A8FF)
val Blue300 = Color(0xFF57B6FF)
val Blue200 = Color(0xFF8CCDFF)
val Blue100 = Color(0xFFB1DDFF)
val Blue50 = Color(0xFFE6F4FF)

val primaryLight = Color(0xFFFDFDFD)
val primaryDark = Color(0xFF000000)

val AlertErrorColor = Color(0xFFD90000)
val AlertWarningColor = Color(0xFFDAA745)
val AlertInformationColor = Color(0xFF3280C8)
val AlertSuccessColor = Color(0xFF00C066)
val AlertGrayColor = Color(0xFFA8A8A8)

val surfaceContainerLowLight = Color(0xFFF5F3F3)


val ColorScheme.shareButtonColor
    @Composable
    get() = if(isSystemInDarkTheme()) Color(0xFFDADADA) else Color(0xFFDADADA)
