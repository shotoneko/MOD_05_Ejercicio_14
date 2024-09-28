package modulo_05.ejercicio_10


import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import modulo_05.ejercicio_10.components.MyButton
import modulo_05.ejercicio_10.components.MyScaffold
import modulo_05.ejercicio_10.components.PatientCard
import modulo_05.ejercicio_10.navigation.NavigationItem
import modulo_05.ejercicio_10.viewmodel.IMCViewModel


@Composable
fun HomeView(navController: NavHostController, viewModel: IMCViewModel) {
   // val viewModel = sharedViewModel(navController)
    MyScaffold(
       navController = navController,
       navigationIcon =  {
                    IconButton(onClick = { }) {
                        Icon(Icons.Filled.Menu, contentDescription = "Localized description")
                    }
       }

    ) { innerPadding ->
        ContentHomeView(
            navController = navController, viewModel
        )
    }
}
@Composable
fun ContentHomeView(
    navController: NavHostController,
    viewModel: IMCViewModel
) {

    val statePatient = viewModel.statePatient
    var showModal by remember { mutableStateOf(false) }
    var name by remember { mutableStateOf("") }
    //var patientList = listOf("Juan", "Pedro", "Maria")

    Scaffold(
        floatingActionButton = {
            AddPatientFAB { showModal = true }
        }

    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .padding(
                    top = WindowInsets.statusBars
                        .asPaddingValues()
                        .calculateTopPadding()
                )
                .padding(horizontal = 8.dp),

            verticalArrangement = Arrangement.spacedBy(4.dp),

            ) {
            item {
                Spacer(
                    modifier = Modifier.height(
                        16.dp
                    )
                )
            }
            items(viewModel.patientList) { patient ->
                PatientCard(patient, navController)
            }
        }
        AddPatientModal(
            isOpen = showModal,
            onClose = { showModal = false },
            onConfirm = { patientName ->
                viewModel.addPatientName(patientName)
                navController.navigate(NavigationItem.Home.route)
            },
            //navController = navController
        )
    }
}


@Composable
fun AddPatientModal(
    isOpen: Boolean,
    onClose: () -> Unit,
    onConfirm: (String) -> Unit,
    //navController: NavHostController
) {
    var name by remember { mutableStateOf("") }
    if (isOpen) {
        AlertDialog(
            onDismissRequest = onClose,
            containerColor = Color(0xFF37474F),
            titleContentColor = Color.Magenta,
            title = { Text("Agregar Paciente") },
            text = {

                OutlinedTextField(
                    textStyle = TextStyle(fontSize = 20.sp, color = Color.White),
                    value = name,
                    onValueChange = { name = it },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Red,
                        focusedLabelColor = Color.White,
                        unfocusedLabelColor = Color.White
                    ),
                    label = { Text("Nombre del paciente") }
                )
            },
            confirmButton = {
                Button(onClick = { onConfirm(name) }) {
                    Text("Agregar")
                }

            },
            dismissButton = {
                Button(onClick = onClose) {
                    Text("Cancelar")
                }
            }
        )
    }
}
@Composable
fun AddPatientFAB(onClick: () -> Unit) {
    FloatingActionButton(
        onClick = onClick,
        modifier = Modifier.padding(16.dp)
    ) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = "Agregar paciente"
        )
    }
}
