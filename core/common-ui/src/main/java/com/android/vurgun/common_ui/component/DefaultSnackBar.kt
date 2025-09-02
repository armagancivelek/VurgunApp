package com.android.vurgun.common_ui.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.android.vurgun.common_ui.R
import com.android.vurgun.common_ui.theme.AlertErrorColor
import com.android.vurgun.common_ui.theme.AlertInformationColor
import com.android.vurgun.common_ui.theme.AlertSuccessColor
import com.android.vurgun.common_ui.theme.AlertWarningColor
import com.android.vurgun.common_ui.theme.primaryLight

@Composable
fun DefaultSnackBar(
    snackBarHostState: SnackbarHostState,
    type: State<SnackBarType>,
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit = { },
) {
    SnackbarHost(
        hostState = snackBarHostState,
        snackbar = { data ->
            Snackbar(
                containerColor = type.value.containerColor,
                modifier = Modifier.padding(horizontal = 16.dp),
                content = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(8.dp),
                    ) {
                        Icon(
                            painter = painterResource(id = type.value.resId),
                            contentDescription = "Icon",
                            modifier = Modifier.padding(end = 8.dp),
                        )
                        Text(
                            text = data.visuals.message,
                            style = MaterialTheme.typography.labelMedium,
                        )
                    }
                },
                dismissAction = { onDismiss() },
                action = {
                    data.visuals.actionLabel?.let { actionLabel ->
                        TextButton(onClick = onDismiss) {
                            Text(
                                text = actionLabel,
                                color = primaryLight,
                                style = MaterialTheme.typography.bodyMedium,
                            )
                        }
                    }
                },
            )
        },
        modifier = modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 52.dp)
            .wrapContentHeight(Alignment.Bottom),
    )
}

enum class SnackBarType(@DrawableRes var resId: Int, val containerColor: Color) {
    Error(resId = R.drawable.ic_error, containerColor = AlertErrorColor),
    Success(resId = R.drawable.ic_succeed_white, containerColor = AlertSuccessColor),
    Warning(resId = R.drawable.ic_succeed_white, containerColor = AlertWarningColor),
    Information(resId = R.drawable.ic_information, containerColor = AlertInformationColor),
}