package modulo_05.ejercicio_10.viewmodel

import android.icu.text.DecimalFormat
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import modulo_05.ejercicio_10.model.PatientState

class IMCViewModel : ViewModel() {

    var statePatient by mutableStateOf(PatientState())
        private set
    var patientList by mutableStateOf(listOf<PatientState>())
    //  val patientList = MutableStateFlow<List<PatientState>>(emptyList())

    private val _showAlert = MutableStateFlow<Boolean>(false)
    var showAlert: StateFlow<Boolean> = _showAlert

    private val _selectedGender = MutableStateFlow("")
    val selectedGender: StateFlow<String> = _selectedGender.asStateFlow()

    private val _edad = MutableStateFlow("")
    val edad: StateFlow<String> = _edad.asStateFlow()

    private val _peso = MutableStateFlow(0.0)
    val peso: StateFlow<Double> = _peso.asStateFlow()

    private val _altura = MutableStateFlow(0)
    val altura: StateFlow<Int> = _altura.asStateFlow()

    private var _imc = MutableStateFlow(0.0)
    var imc: StateFlow<Double> = _imc.asStateFlow()

    private val _healthStatus = MutableStateFlow("")
    var healthStatus: StateFlow<String> = _healthStatus.asStateFlow()


    fun onMainScreenChanged(edad: String, peso: String, altura: String) {
        _edad.value = edad
        validaEdad(edad)
        try {
            _peso.value = peso.toDouble()
            _altura.value = altura.toInt()

            if (!peso.isBlank() && !altura.isBlank() && altura.toInt() != 0) {
                _imc.value = calcularIMC(_peso.value, _altura.value)
                updateHealthStatus(_imc.value)
            }
        } catch (e: NumberFormatException) {

        }
    }

    fun updateHealthStatus(imc: Double):String {
        _healthStatus.value  = when {
            imc <= 18.5 -> "Bajo peso"
            imc <= 24.9 -> "Peso normal"
            imc <= 29.9 -> "Sobrepeso"
            imc <= 34.9-> "Obesidad grado 1"
            imc <= 39.9 -> "Obesidad grado 2"
            else -> "Obesidad grado 3"
        }
        return _healthStatus.value
    }

    fun updateSelectedGender(gender: String) {
        _selectedGender.value = gender
    }

    fun upDateShowAlert(value: Boolean) {
        _showAlert.value = value
    }

    fun calcularIMC(peso: Double, altura: Int): Double {
        val imc = peso / (altura * altura) * 10000
        val df = DecimalFormat("#.##")
        val imcFormateado = df.format(imc)
        return imcFormateado.toDouble()
    }

    fun limpiarPantalla() {
        _edad.value = ""
        _peso.value = 0.0
        _altura.value = 0
        _imc.value = 0.0
    }

    fun validaEdad(edad: String) {
        if (edad.length >= 4) {
            _edad.value = edad.substring(0, 3)
        } else {

            try {
                if (edad.toInt() in 1..120) {
                    _showAlert.value = false
                } else {
                    _showAlert.value = true
                }
            } catch (e: NumberFormatException) {
            }
        }
    }

    fun addPatientName(name: String) {
        val newPatient = statePatient.copy(
            id = patientList.size + 1,
            name = name
        )
        patientList += newPatient
    }

    fun addPatientIMCCalculator(id: Int, name: String) {
        val imc = _imc.value
        patientList = patientList.map {
            if (it.id == id) {
                it.copy(
                  //  id = patientList.size + 1,
                    name = name,
                    age = _edad.value.toInt(),
                    gender = _selectedGender.value,
                    imc = _imc.value,
                    healthStatus =  updateHealthStatus(imc),
                )
            } else {
                it
            }
        }
        limpiarPantalla()
    }
}



