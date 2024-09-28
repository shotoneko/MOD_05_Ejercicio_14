package modulo_05.ejercicio_10.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import modulo_05.ejercicio_10.HomeView
import modulo_05.ejercicio_10.ContentIMCView
import modulo_05.ejercicio_10.IMCView
import modulo_05.ejercicio_10.viewmodel.IMCViewModel

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = NavigationItem.Home.route,
    dataIntroduction: StoreBoarding,
    viewModel: IMCViewModel
    ) {

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination,

    ) {
        composable(NavigationItem.Home.route) {
            HomeView(navController, viewModel)
        }

        composable("IMCView/{id}/{name}", arguments = listOf(
            navArgument("id") { type = NavType.IntType } ,
            navArgument("name") { type = NavType.StringType }


        )) {
            val id = it.arguments?.getInt("id") ?: 0
            val name = it.arguments?.getString("name") ?: ""
            IMCView(navController, viewModel, id, name )
        }

//        composable(NavigationItem.IMCView.route) {
//            IMCView(navController=navController)
//        }
        composable(NavigationItem.MainOnBoarding.route) {
            MainOnBoarding(navController = navController, dataIntroduction)
        }
    }
}
