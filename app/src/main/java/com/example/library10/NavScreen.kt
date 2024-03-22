package com.example.library10

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.library10.network.BookResponse
import com.example.library10.ui.BookViewModel
import com.example.library10.ui.Screens.DetailScreen
import com.example.library10.ui.Screens.ListScreen
import com.example.library10.ui.Screens.SearchScreen
import com.example.library10.ui._uiState

enum class Screen(val route:String){
    SearchScreen("search_screen"),
    ListScreen("list_screen"),
    DetailScreen("detail_screen/{id}")
}

@Composable
fun AppNavigation(uiState: _uiState, viewModel: BookViewModel){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.SearchScreen.route){
        composable(Screen.SearchScreen.route){
            SearchScreen(navController = navController, viewModel = viewModel)
        }
        composable(Screen.ListScreen.route){
            ListScreen(uiState = uiState, navController = navController)
        }
        composable(Screen.DetailScreen.route){navBackStackEntry ->
            val arguments = navBackStackEntry.arguments
            val itemID = arguments?.getString("id")
            DetailScreen(detailID = itemID, uiState = uiState )
        }
    }
}