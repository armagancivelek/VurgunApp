package com.android.vurgun.common_ui.component

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.android.vurgun.common_ui.theme.AlertSuccessColor

@Composable
@SuppressLint("ModifierParameter")
fun LoadingView(
    modifier: Modifier = Modifier
        .fillMaxSize()
        .clickable(enabled = false) {},
) {
    Box(
        modifier = modifier.background(Color.Transparent),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .background(Color.Transparent)
                .wrapContentSize(),
            color = AlertSuccessColor,
        )
    }
}
