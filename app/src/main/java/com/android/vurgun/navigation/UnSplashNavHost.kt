package com.android.vurgun.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.android.vurgun.ui.state.AppState

@ExperimentalLayoutApi
@ExperimentalFoundationApi
@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@Composable
fun UnSplashNavHost(
    appState: AppState,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = appState.navController,
        startDestination = appState.startDestination,
        modifier = modifier,
    ) {

//
//        homeScreen(
//            onItemClick = { photoId ->
//                photoId?.let {
//                    appState.navController.navigateToPhotoDetail(photoId = it)
//                }
//            },
//            onShowSnackbar = { _, _ -> false },
//        )


    }
}
