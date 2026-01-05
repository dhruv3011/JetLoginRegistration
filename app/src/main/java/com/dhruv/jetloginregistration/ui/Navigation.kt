package com.dhruv.jetloginregistration.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.dhruv.jetloginregistration.ui.home.HomeScreen
import com.dhruv.jetloginregistration.ui.login.LoginScreen
import com.dhruv.jetloginregistration.ui.signup.PolicyScreen
import com.dhruv.jetloginregistration.ui.signup.PrivacyScreen
import com.dhruv.jetloginregistration.ui.signup.SignUpScreen

sealed class Route {
    data class LoginScreen(val name: String = "Login"): Route()
    data class SignUpScreen(val name: String = "SingUp"): Route()
    data class PrivacyScreen(val name: String = "Privacy"): Route()
    data class PolicyScreen(val name: String = "Policy"): Route()
    data class HomeScreen(val name: String = "Home"): Route()
}

@Composable
fun MyNavigation(navHostController: NavHostController) {
    NavHost(navController = navHostController,
        startDestination = "loginFlow") {
        navigation(startDestination = Route.LoginScreen().name , route = "loginFlow") {
            composable(route = Route.LoginScreen().name) {
                LoginScreen (
                    onLoginClick = {
                        navHostController.navigate(
                            Route.HomeScreen().name
                        ) {
                            popUpTo (route = "loginFlow")
                        }
                    },
                    onSignUpClicked = {
                        navHostController.navigateToSingleTop(
                            Route.SignUpScreen().name
                        )
                    }
                )
            }

            composable(route = Route.SignUpScreen().name) {
                SignUpScreen({
                    navHostController.navigate(
                        Route.HomeScreen().name
                    ) {
                        popUpTo (route = "loginFlow")
                    }
                }, {
                    navHostController.navigateToSingleTop(
                        Route.LoginScreen().name
                    )
                }, {
                    navHostController.navigate(
                        Route.PrivacyScreen().name
                    ) {
                        launchSingleTop = true
                    }
                }, {
                    navHostController.navigate(
                        Route.PolicyScreen().name
                    ) {
                        launchSingleTop = true
                    }
                })
            }

            composable(route = Route.PrivacyScreen().name) {
                PrivacyScreen{
                    navHostController.navigateUp()
                }
            }

            composable(route = Route.PolicyScreen().name) {
                PolicyScreen{
                    navHostController.navigateUp()
                }
            }
        }
        composable(route = Route.HomeScreen().name) {
            HomeScreen()
        }
    }
}

fun NavController.navigateToSingleTop(route: String) {
    navigate(route) {
        popUpTo (graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}