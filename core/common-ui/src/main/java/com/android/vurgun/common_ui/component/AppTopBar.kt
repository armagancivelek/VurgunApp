package com.android.vurgun.common_ui.component

import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.with
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.vurgun.common_ui.R
import com.android.vurgun.common_ui.icon.AppIcons
import com.android.vurgun.common_ui.theme.Black900
import com.android.vurgun.common_ui.theme.surfaceContainerLowLight
import com.android.vurgun.util.Constants

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun AppTopBar(
    @StringRes titleRes: Int,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    navigationIcon: ImageVector? = null,
    navigationIconContentDescription: String,
    actionIcon: ImageVector? = null,
    actionIconContentDescription: String = Constants.EMPTY_STRING,
    modifier: Modifier = Modifier,
    colors: TopAppBarColors = TopAppBarDefaults.centerAlignedTopAppBarColors(),
    onNavigationClick: () -> Unit = {},
    onActionClick: () -> Unit = {},
) {
    val focusRequester = remember { FocusRequester() }
    val isSearching = remember { mutableStateOf(false) }

    LaunchedEffect(isSearching.value) {
        if (isSearching.value) focusRequester.requestFocus()
    }

    TopAppBar(
        title = {
            AnimatedContent(
                targetState = isSearching,
                transitionSpec = {
                    slideInHorizontally { it } + fadeIn() with slideOutHorizontally { -it } + fadeOut()
                },
                label = "",
            ) { searching ->
                if (searching.value) {
                    BasicTextField(
                        value = searchQuery,
                        onValueChange = onSearchQueryChange,
                        singleLine = true,
                        textStyle = TextStyle(
                            fontSize = 13.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        ),
                        cursorBrush = SolidColor(Black900),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(45.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .border(
                                width = 1.dp,
                                color = MaterialTheme.colorScheme.outline,
                                shape = RoundedCornerShape(12.dp)
                            )
                            .background(
                                color = surfaceContainerLowLight,
                                shape = RoundedCornerShape(10.dp)
                            )
                            .focusRequester(focusRequester),
                        decorationBox = { innerTextField ->
                        }
                    )
                } else {
                    Text(text = stringResource(id = titleRes))
                }
            }
        },
        navigationIcon = {
            navigationIcon?.let {
                IconButton(onClick = {
                    isSearching.value = !isSearching.value
                    onNavigationClick.invoke()
                }) {
                    if (!isSearching.value) {
                        Icon(
                            imageVector = navigationIcon,
                            contentDescription = navigationIconContentDescription,
                            tint = MaterialTheme.colorScheme.onSurface,
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = navigationIconContentDescription,
                            tint = MaterialTheme.colorScheme.onSurface,
                        )
                    }
                }
            }
        },
        actions = {
            actionIcon?.let {
                IconButton(onClick = onActionClick) {
                    Icon(
                        imageVector = actionIcon,
                        contentDescription = actionIconContentDescription,
                        tint = MaterialTheme.colorScheme.onSurface,
                    )
                }
            }

        },
        colors = colors,
        modifier = modifier.testTag("AppTopBar"),
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview("Top App Bar")
@Composable
private fun NiaTopAppBarPreview() {
    Surface {
        AppTopBar(
            titleRes = R.string.feature_home_title,
            searchQuery = "",
            onSearchQueryChange = {},
            navigationIconContentDescription = "Navigation icon",
            actionIconContentDescription = "Action icon",
            actionIcon = AppIcons.Notification,
        )
    }
}
