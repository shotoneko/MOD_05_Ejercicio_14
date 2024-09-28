package modulo_05.ejercicio_10

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import modulo_05.ejercicio_10.navigation.AppNavHost
import modulo_05.ejercicio_10.navigation.NavigationItem
import modulo_05.ejercicio_10.navigation.StoreBoarding
import modulo_05.ejercicio_10.ui.theme.Ejercicio_10Theme
import modulo_05.ejercicio_10.viewmodel.IMCViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel: IMCViewModel by viewModels()
        enableEdgeToEdge()
        setContent {
            Ejercicio_10Theme {
                val context = LocalContext.current
                val dataIntroduction = StoreBoarding(context)
                val booleanIntro = dataIntroduction.getBoarding.collectAsState(initial = false)
               AppNavHost(
                   navController = rememberNavController(),
                   startDestination = if (booleanIntro.value) NavigationItem.Home.route else NavigationItem.MainOnBoarding.route,
                   dataIntroduction = dataIntroduction ,
                   viewModel = viewModel)
               //HomeView()

            }
        }
    }
}
