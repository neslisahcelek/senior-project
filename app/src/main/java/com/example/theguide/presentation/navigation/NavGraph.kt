package com.example.theguide.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.theguide.presentation.dashboard.DashboardScreen
import com.example.theguide.presentation.topplaces.TopPlacesScreen
import com.example.theguide.presentation.topplaces.TopPlacesVM
import com.example.theguide.presentation.welcome.WelcomeScreen
import com.example.theguide.presentation.welcome.WelcomeVM

@Composable
fun TheGuideNavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Route.WelcomeScreen.route
    ) {
        composable(Route.WelcomeScreen.route) {
            val viewModel: WelcomeVM = hiltViewModel()
            WelcomeScreen(event = viewModel::onEvent, navigate = { route ->
                navController.navigate(route)
            })
        }

        composable(Route.DashboardScreen.route) {
            DashboardScreen(navigate = { route ->
                navController.navigate(route)
            })
        }

        composable(Route.TopPlacesScreen.route) {
            val viewModel: TopPlacesVM = hiltViewModel()
            TopPlacesScreen(event = viewModel::onEvent, navigate = { route ->
                navController.navigate(route)
            })
        }

    }
}