package com.android.vurgun.common_ui.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.android.vurgun.common_ui.R

@Composable
fun getBetTypeDisplayName(betType: String): String {
    return when (betType.uppercase()) {
        "MS 1", "HOME", "1" -> stringResource(R.string.bet_type_home_win)
        "MS 2", "AWAY", "2" -> stringResource(R.string.bet_type_away_win)
        "MS X", "DRAW", "X" -> stringResource(R.string.bet_type_draw)
        else -> betType
    }
}