package modulo_05.ejercicio_10.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import modulo_05.ejercicio_10.R
import modulo_05.ejercicio_10.model.PatientState
import modulo_05.ejercicio_10.navigation.NavigationItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyScaffold(
    myviewModel: ViewModel? = null,
    navController: NavController,
    navigationIcon: @Composable () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Blue,
                    navigationIconContentColor = Color.White,
                ),
                title = {
                    Text(
                        text = stringResource(id = R.string.app_name),
                        color = Color.White
                    )
                },
                navigationIcon = navigationIcon
            )
        },
    )
    { innerPadding ->
        content(innerPadding)
    }
}


@Composable
fun PatientCard(
    patient : PatientState,
    navController: NavController
) {
    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = patient.name,
                onValueChange = { },
                modifier = Modifier.fillMaxWidth(),
                textStyle = TextStyle(color = Color.Red),
                label = { Text("Nombre paciente") },
                readOnly = true,
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Blue,
                    //focusedTextColor =  Color.Green
                ),
            )
            Spacer(
                modifier = Modifier.height(
                    16.dp
                )
            )
            Text(text="Edad: ${patient.age}")
            Text(text="Sexo: ${patient.gender}")
            Text("IMC: ${patient.imc}")
            Text(text= "Condicion Salud: ${patient.healthStatus}")
            Button(onClick = {
                navController.navigate("IMCView/${patient.id}/${patient.name}")
            },
                modifier = Modifier.align(Alignment.End)) {
                Text("Calcular IMC")
            }
        }
    }
}

