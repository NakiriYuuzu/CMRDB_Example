package net.yuuzu.cmrdb_example.common.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.Navigator
import net.yuuzu.cmrdb_example.presentation.BmiScreen
import net.yuuzu.cmrdb_example.presentation.FirstScreen
import net.yuuzu.cmrdb_example.presentation.FiveScreen
import net.yuuzu.cmrdb_example.presentation.FourScreen
import net.yuuzu.cmrdb_example.presentation.SecondScreen
import net.yuuzu.cmrdb_example.presentation.SixScreen
import net.yuuzu.cmrdb_example.presentation.SuccessScreen
import net.yuuzu.cmrdb_example.presentation.ThirdScreen

@Composable
fun Navigation(navigator: Navigator, snackbarHost: SnackbarHostState) {
    NavHost(
        navigator = navigator,
        initialRoute = Screen.FirstScreen.route,
    ) {
        scene(route = Screen.SuccessScreen.route) {
            SuccessScreen(onBack = navigator::popBackStack)
        }
        scene(route = Screen.BmiScreen.route) {
            BmiScreen(snackbarHost = snackbarHost)
        }
        scene(route = Screen.FirstScreen.route) {
            FirstScreen()
        }
        scene(route = Screen.SecondScreen.route) {
            SecondScreen()
        }
        scene(route = Screen.ThirdScreen.route) {
            ThirdScreen()
        }
        scene(route = Screen.FourScreen.route) {
            FourScreen()
        }
        scene(route = Screen.FiveScreen.route) {
            FiveScreen()
        }
        scene(route = Screen.SixScreen.route) {
             SixScreen(onNext = { navigator.navigate(Screen.SuccessScreen.route) }, snackbarHost = snackbarHost)
        }
    }
}

@Composable
fun currentRoute(navigator: Navigator): String? {
    return navigator.currentEntry.collectAsState(null).value?.route?.route
}