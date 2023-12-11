package net.yuuzu.cmrdb_example

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.moriatsushi.insetsx.SystemBarsBehavior
import com.moriatsushi.insetsx.rememberWindowInsetsController
import moe.tlaster.precompose.PreComposeApp
import moe.tlaster.precompose.navigation.rememberNavigator
import net.yuuzu.cmrdb_example.common.navigation.Navigation
import net.yuuzu.cmrdb_example.common.navigation.Screen
import net.yuuzu.cmrdb_example.common.navigation.currentRoute
import net.yuuzu.cmrdb_example.common.theme.AppTheme

@Composable
fun App(
    darkTheme: Boolean,
    dynamicColor: Boolean
) {
    val windowInsets = rememberWindowInsetsController()
    val snackbarHost = SnackbarHostState()

    val routeList = listOf(
        Screen.FirstScreen,
        Screen.SecondScreen,
        Screen.ThirdScreen,
        Screen.FourScreen,
        Screen.FiveScreen,
        Screen.SixScreen,
        Screen.BmiScreen,
    )

    LaunchedEffect(Unit) {
        windowInsets?.setIsNavigationBarsVisible(false)
        windowInsets?.setIsStatusBarsVisible(true)
        windowInsets?.setSystemBarsBehavior(SystemBarsBehavior.Immersive)
    }

    PreComposeApp {
        val navigator = rememberNavigator()

        AppTheme(
            darkTheme = darkTheme,
            dynamicColor = dynamicColor
        ) {
            Scaffold(
                snackbarHost = { SnackbarHost(hostState = snackbarHost) },
                bottomBar = {
                    NavigationBar {
                        routeList.forEach { item ->
                            NavigationBarItem(
                                selected = item.route == currentRoute(navigator),
                                onClick = { navigator.navigate(item.route) },
                                icon = { item.navIcon?.let { icon -> Icon(icon, null) } },
                                label = { item.title?.let { title -> Text(text = title) } }
                            )
                        }
                    }
                }
            ) {
                Surface(
                    color = MaterialTheme.colorScheme.background,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Navigation(navigator, snackbarHost)
                }
            }
        }
    }
}