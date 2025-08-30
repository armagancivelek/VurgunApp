package com.android.vurgun.common_ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.sp
import com.android.vurgun.common_ui.R

const val BODY_WEIGHT = 400
const val TITLE_WEIGHT = 500
const val HEADLINE_WEIGHT = 600

val AppTypography = Typography(
    displayLarge = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 57.sp,
        lineHeight = 64.sp,
        letterSpacing = (-0.25).sp,
    ),
    displayMedium = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 45.sp,
        lineHeight = 52.sp,
        letterSpacing = 0.sp,
    ),
    displaySmall = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 36.sp,
        lineHeight = 44.sp,
        letterSpacing = 0.sp,
    ),
    headlineLarge = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 32.sp,
        lineHeight = 40.sp,
        letterSpacing = 0.sp,
    ),
    headlineMedium = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 28.sp,
        lineHeight = 36.sp,
        letterSpacing = 0.sp,
    ),
    headlineSmall = TextStyle(
        fontWeight = FontWeight(HEADLINE_WEIGHT),
        fontSize = 24.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.sp,
    ),
    titleLarge = TextStyle(
        fontFamily = FontFamily(Font(R.font.inter_bold)),
        fontWeight = FontWeight(TITLE_WEIGHT),
        lineHeight = 26.sp,
        fontSize = 20.sp,
    ),
    titleMedium = TextStyle(
        fontFamily = FontFamily(Font(R.font.inter_medium)),
        fontWeight = FontWeight(TITLE_WEIGHT),
        lineHeight = 26.sp,
        fontSize = 20.sp,
        letterSpacing = TextUnit(-0.5F, TextUnitType.Sp),
    ),
    titleSmall = TextStyle(
        fontFamily = FontFamily(Font(R.font.inter_regular)),
        fontWeight = FontWeight(TITLE_WEIGHT),
        lineHeight = 24.sp,
        fontSize = 16.sp,
    ),
    bodyLarge = TextStyle(
        fontFamily = FontFamily(Font(R.font.inter_regular)),
        fontWeight = FontWeight(TITLE_WEIGHT),
        lineHeight = 24.sp,
        fontSize = 16.sp,
    ),
    bodyMedium = TextStyle(
        fontFamily = FontFamily(Font(R.font.inter_regular)),
        fontWeight = FontWeight(BODY_WEIGHT),
        lineHeight = 22.sp,
        fontSize = 14.sp,
    ),
    bodySmall = TextStyle(
        fontFamily = FontFamily(Font(R.font.inter_regular)),
        fontWeight = FontWeight(BODY_WEIGHT),
        lineHeight = 20.sp,
        fontSize = 12.sp,
    ),
    labelLarge = TextStyle(
        fontFamily = FontFamily(Font(R.font.inter_regular)),
        fontWeight = FontWeight(TITLE_WEIGHT),
        lineHeight = 20.sp,
        fontSize = 14.sp,
    ),
    labelMedium = TextStyle(
        fontFamily = FontFamily(Font(R.font.inter_regular)),
        fontWeight = FontWeight(TITLE_WEIGHT),
        lineHeight = 18.sp,
        fontSize = 12.sp,
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily(Font(R.font.inter_regular)),
        fontWeight = FontWeight(TITLE_WEIGHT),
        lineHeight = 16.sp,
        fontSize = 11.sp,
    ),
)
