package com.android.vurgun.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.android.vurgun.common_ui.theme.BlueColor
import com.android.vurgun.navigation.TopLevelDestination

@Composable
fun BottomNavigationBar(
    selectedItem: TopLevelDestination,
    navigationItems: List<TopLevelDestination>,
    modifier: Modifier = Modifier,
    onClickedBottomNavItem: (TopLevelDestination) -> Unit = {},
) {
    NavigationBar(
        modifier = modifier.fillMaxWidth(),
        containerColor = MaterialTheme.colorScheme.onPrimary,
        tonalElevation = 16.dp,
    ) {
        navigationItems.forEach { item ->
            if (item.name == TopLevelDestination.CURRENT_SLIP.name) {
                NavigationBarItem(
                    modifier = Modifier.size(42.dp),
                    enabled = true,
                    alwaysShowLabel = true,
                    icon = {},
                    selected = false,
                    onClick = {},
                    colors = NavigationBarItemDefaults.colors().copy(
                        selectedIndicatorColor = Color.Transparent,
                        selectedIconColor = Color.Transparent,
                    ),
                )
            } else {
                NavigationBarItem(
                    modifier = Modifier.size(42.dp),
                    enabled = true,
                    alwaysShowLabel = true,
                    icon = {
                        Icon(
                            painter = painterResource(id = item.icon),
                            contentDescription = stringResource(item.titleTextId),
                        )
                    },
                    selected = selectedItem == item,
                    onClick = {
                        onClickedBottomNavItem.invoke(item)
                    },
                    colors = NavigationBarItemDefaults.colors().copy(
                        selectedIndicatorColor = BlueColor,
                        selectedIconColor = Color.White,
                    ),
                )
            }
        }
    }
}

@Composable
fun currentRoute(navController: NavController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}