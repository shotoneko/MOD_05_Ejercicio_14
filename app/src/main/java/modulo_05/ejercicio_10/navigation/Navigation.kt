package modulo_05.ejercicio_10.navigation

enum class Screen {
    HomeView,
    IMCView,
    MainOnBoarding,
}
sealed class NavigationItem(val route: String) {
    object Home : NavigationItem(Screen.HomeView.name)
    object IMCView : NavigationItem(Screen.IMCView.name)
    object MainOnBoarding : NavigationItem(Screen.MainOnBoarding.name)

}