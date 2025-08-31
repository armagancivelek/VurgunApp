package com.android.vurgun.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration.Indefinite
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.android.vurgun.R
import com.android.vurgun.common_ui.theme.BlueColor
import com.android.vurgun.common_ui.theme.WhiteColor
import com.android.vurgun.navigation.AppNavHost
import com.android.vurgun.navigation.TopLevelDestination
import com.android.vurgun.ui.state.AppState

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class, ExperimentalFoundationApi::class, ExperimentalComposeUiApi::class, ExperimentalAnimationApi::class)
@Composable
fun VurgunApp(
    appState: AppState,
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val isOffline by appState.isOffline.collectAsStateWithLifecycle()

    // If user is not connected to the internet show a snack bar to inform them.
    val notConnectedMessage = stringResource(R.string.not_connected)

    LaunchedEffect(isOffline) {
        if (isOffline) {
            snackBarHostState.showSnackbar(
                message = notConnectedMessage,
                duration = Indefinite,
            )
        } else {
            snackBarHostState.currentSnackbarData?.dismiss()
        }
    }


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Text(
                            text = stringResource(id = R.string.app_name).uppercase(),
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.White
                        )
                        Image(
                            painter = painterResource(id = R.drawable.ic_splash),
                            contentDescription = "Logo",
                            modifier = Modifier
                                .size(64.dp)
                                .offset(x = -14.dp,y =- 4.dp)
                        )
                    }
                },
                actions = {
                    Box(
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .background(
                                Color(0xFF4CAF50),
                                RoundedCornerShape(16.dp)
                            )
                            .padding(horizontal = 10.dp, vertical = 5.dp)
                    ) {
                        Text(
                            text = "Bakiye : 500,75 TL",
                            color = Color.White,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = BlueColor
                )
            )
        },
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            appState.currentTopLevelDestination?.let { currentDest ->
                if (currentDest == TopLevelDestination.CURRENT_SLIP) {
                    Box(
                        modifier = Modifier
                            .size(82.dp)
                            .offset(y = 60.dp)
                            .background(BlueColor, CircleShape)
                            .clickable {
                                appState.navigateToTopLevelDestination(TopLevelDestination.CURRENT_SLIP)
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "0 Maç",
                                color = WhiteColor,
                                fontSize = 14.sp
                            )
                            Text(
                                text = "0,00",
                                color = WhiteColor,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                } else {
                    Box(
                        modifier = Modifier
                            .size(82.dp)
                            .offset(y = 60.dp)
                            .border(
                                width = 2.dp,
                                color = BlueColor,
                                shape = CircleShape
                            )
                            .background(Color.White, CircleShape)
                            .clickable {
                                appState.navigateToTopLevelDestination(TopLevelDestination.CURRENT_SLIP)
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "0 Maç",
                                color = Color.Black,
                                fontSize = 14.sp
                            )
                            Text(
                                text = "0,00",
                                color = Color.Black,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

            }

        },
        bottomBar = {
            appState.currentTopLevelDestination?.let { currentDestination ->
                BottomAppBar(
                    modifier = Modifier.shadow(
                        elevation = 32.dp,
                    ),
                    containerColor = MaterialTheme.colorScheme.onPrimary,
                ) {
                    BottomNavigationBar(
                        modifier = Modifier.padding(bottom = 8.dp),
                        selectedItem = currentDestination,
                        navigationItems = appState.topLevelDestinations,
                        onClickedBottomNavItem = { selectedItem ->
                            if (selectedItem != currentDestination) {
                                appState.navigateToTopLevelDestination(selectedItem)
                            }
                        },
                    )
                }
            }
        },
        containerColor = Color.Transparent,
        contentColor = MaterialTheme.colorScheme.onBackground,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        snackbarHost = {
            SnackbarHost(
                snackBarHostState,
                modifier = Modifier.windowInsetsPadding(WindowInsets.safeDrawing),
            )
        },
    ) { padding ->
        Surface(
            Modifier
                .fillMaxSize()
                .padding(padding)
                .consumeWindowInsets(padding)
                .windowInsetsPadding(
                    WindowInsets.safeDrawing.only(
                        WindowInsetsSides.Horizontal,
                    ),
                ),
        ) {
            AppNavHost(
                appState = appState,
            )
        }
    }
}
