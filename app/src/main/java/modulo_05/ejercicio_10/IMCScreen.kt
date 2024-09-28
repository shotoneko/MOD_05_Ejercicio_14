package modulo_05.ejercicio_10
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import modulo_05.ejercicio_10.components.MainButton
import modulo_05.ejercicio_10.components.MyScaffold
import modulo_05.ejercicio_10.components.MySegmentedButton
import modulo_05.ejercicio_10.components.MyText
import modulo_05.ejercicio_10.components.MyTextField
import modulo_05.ejercicio_10.model.PatientState

import modulo_05.ejercicio_10.viewmodel.IMCViewModel


@Composable
fun IMCView(navController: NavHostController, viewModel:IMCViewModel, id:Int, name:String) {
   //val myviewModel = IMCViewModel()
   // val viewModel = sharedViewModel(navController)
    MyScaffold(
        myviewModel = viewModel,
        navController = navController,
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Localized description"
                )
            }
        }
    ) { innerPadding ->
        ContentIMCView(
            paddingValues = innerPadding,
            viewModel = viewModel,
            navController = navController,
            id=id,
            name=name
        )
    }
}

@Composable
fun ContentIMCView(
    id:Int,
    name:String,
    paddingValues: PaddingValues,
    viewModel: IMCViewModel,
    navController: NavController
) {
    Log.d("ContentIMCView", "id: $id")
    Log.d("ContentIMCView", "name: $name")

    //val statusBarPadding = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()
    Column(
        modifier = Modifier
            .padding(paddingValues)
            .padding(top = 26.dp)
            .padding(horizontal = 24.dp)
            .fillMaxSize(),// Padding del Scaffold

        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val edad by viewModel.edad.collectAsState(initial = "")
        val peso by viewModel.peso.collectAsState(initial = "")
        val altura by viewModel.altura.collectAsState(initial = "")
        val imc by viewModel.imc.collectAsState(initial = "")
        val healthStatus by viewModel.healthStatus.collectAsState(initial = "")


        val showAlert by viewModel.showAlert.collectAsState(initial = false)
        val s_peso = peso.toString()
        val s_altura = altura.toString()

        MyText(text = stringResource(id = R.string.tituloApp))
        MySegmentedButton(viewModel = viewModel)
        Column(
            modifier = Modifier.padding(top = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally

        )
        {
            MyTextField(
                text = edad,
                onValueChange = {
                    viewModel.onMainScreenChanged(
                        edad = it,
                        peso = s_peso,
                        altura = s_altura

                    )
                },
                label = stringResource(id = R.string.edad)
            )
            MyTextField(
                text = peso.toString(),
                onValueChange = { peso ->
                    viewModel.onMainScreenChanged(
                        edad = edad,
                        peso = peso,
                        altura = s_altura
                    )
                },
                label = stringResource(id = R.string.peso)
            )
            MyTextField(
                text = altura.toString(),
                onValueChange = {
                    viewModel.onMainScreenChanged(
                        edad = edad,
                        peso = s_peso,
                        altura = it
                    )
                },
                label = stringResource(id = R.string.altura)
            )

            Text(text = stringResource(id = R.string.calcular))

            Text(text = imc.toString(),
                fontSize = 50.sp,
                fontWeight = FontWeight.SemiBold)
            Log.d("ContentIMCView", "status: $healthStatus")

            Text(text = healthStatus,
                fontSize = 40.sp,
                fontWeight = FontWeight.SemiBold)

            MainButton(
                text = "Limpiar pantalla",
                color = MaterialTheme.colorScheme.error
            ) {
                viewModel.limpiarPantalla()
            }

            MainButton(text ="Guardar datos", color = MaterialTheme.colorScheme.primary) {
                viewModel.addPatientIMCCalculator(id, name)
                navController.navigate("HomeView")
            }

            if (showAlert) {
                AlertDialog(
                    onDismissRequest = { viewModel.upDateShowAlert(false) },
                    title = { Text("Alerta") },
                    text = { Text("La edad debe estar entre 1 y 120") },
                    confirmButton = {
                        Button(onClick = { viewModel.upDateShowAlert(false) }) {
                            Text("Aceptar")
                        }
                    }
                )
            }
        }
    }
}

