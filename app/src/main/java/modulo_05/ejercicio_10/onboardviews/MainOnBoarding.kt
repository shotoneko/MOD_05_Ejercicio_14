package modulo_05.ejercicio_10.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import modulo_05.ejercicio_10.R

import androidx.compose.foundation.pager.rememberPagerState
import androidx.navigation.NavController


@Composable
fun MainOnBoarding(navController: NavController, booleanIntro: StoreBoarding) {
    val items = ArrayList<PagerData>()
    items.add(
        PagerData(
            image = R.raw.page1,
            title = "Bienvenido",
            description = "Esta es la descripcion de la pagina 1"
        )
    )
    items.add(
        PagerData(
            image = R.raw.page2,
            title = "Guenas",
            description = "Esta es la descripcion de la pagina 2"
        )
    )
    items.add(
        PagerData(
            image = R.raw.page3,
            title = "Hola",
            description = "Esta es la descripcion de la pagina 3"
        )
    )
    val pagerState = rememberPagerState(initialPage = 0) { 3 }
    OnBoardingPager(
        item = items,
        pagerState = pagerState,
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Color.White
            ),
        navController = navController,
        booleanIntro = booleanIntro
    )

}