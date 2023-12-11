package net.yuuzu.cmrdb_example.common.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Abc
import androidx.compose.material.icons.filled.Backpack
import androidx.compose.material.icons.filled.Cabin
import androidx.compose.material.icons.filled.Dangerous
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.Height
import androidx.compose.material.icons.filled.Numbers
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(
    val route: String,
    val title: String? = null,
    val navIcon: ImageVector? = null,
    val objectName: String? = null,
    val objectPath: String? = null
) {
    data object HomeScreen: Screen(
        route = "home_screen",
    )
    data object BmiScreen: Screen(
        route = "bmi_screen",
        title = "BMI",
        navIcon = Icons.Default.Height
    )
    data object SuccessScreen: Screen(
        route = "success_screen",
        title = "Success"
    )
    data object FirstScreen: Screen(
        route = "first_screen",
        title = "First",
        navIcon = Icons.Default.Abc
    )
    data object SecondScreen: Screen(
        route = "second_screen",
        title = "Second",
        navIcon = Icons.Default.Backpack
    )
    data object ThirdScreen: Screen(
        route = "third_screen",
        title = "Third",
        navIcon = Icons.Default.Cabin
    )
    data object FourScreen: Screen(
        route = "four_screen",
        title = "Four",
        navIcon = Icons.Default.Dangerous
    )
    data object FiveScreen: Screen(
        route = "five_screen",
        title = "Five",
        navIcon = Icons.Default.Eco
    )
    data object SixScreen: Screen(
        route = "six_screen",
        title = "Six",
        navIcon = Icons.Default.Numbers
    )
}