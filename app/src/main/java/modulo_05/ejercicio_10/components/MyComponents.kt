package modulo_05.ejercicio_10.components
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MultiChoiceSegmentedButtonRow
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import modulo_05.ejercicio_10.R
import modulo_05.ejercicio_10.viewmodel.IMCViewModel

@Composable
fun MyText(text: String){
    Text(
        text = text,
        modifier = Modifier
            .shadow(
                elevation = 2.dp,shape = RoundedCornerShape(8.dp),
                clip = true
            )
            .clip(RoundedCornerShape(8.dp))
            .background(Color.LightGray)
            .padding(8.dp)

    )
}

@Composable
fun MyTextField(
    text: String,
    onValueChange: (String) -> Unit,
    label: String
) {
   TextField(
        maxLines = 1,
        value = text,
        onValueChange = onValueChange,
        label = { Text(text = label) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .fillMaxWidth()
    )
}

@Composable
fun MyButton(
        text: String,
        onClick: () -> Unit)
    {
    Button(
        onClick = onClick,
        colors= ButtonDefaults.buttonColors(containerColor = Color.Blue.copy(alpha = 0.5f)),
        modifier = Modifier.fillMaxWidth()

    ) {
        Text( text = text, color = Color.White)

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MySegmentedButton(viewModel: IMCViewModel) {
    var checkedList by remember { mutableStateOf<List<Int>>(emptyList()) }
    val options = listOf("Hombre", "Mujer")

    MultiChoiceSegmentedButtonRow(
        modifier = Modifier.fillMaxWidth()
    ) {
        options.forEachIndexed { index, label ->
            SegmentedButton(
                shape = SegmentedButtonDefaults.itemShape(index = index, count = options.size),
                colors = SegmentedButtonDefaults.colors(
                    activeContainerColor = when {
                        label == "Hombre" -> Color.Green.copy(alpha = 0.5f)
                        label == "Mujer" -> Color.Red.copy(alpha = 0.5f)
                        else -> Color.LightGray
                    }
                ),
                onCheckedChange = { isChecked ->
                    checkedList = if (isChecked) listOf(index) else emptyList()
                    viewModel.updateSelectedGender(options[index])
                },
                checked = index in checkedList
            ) {
                Text(label)
            }
        }
    }
}

@Composable
fun MainButton(
    text: String,
    color: Color = MaterialTheme.colorScheme.primary,
    onClick: () -> Unit
) {
    OutlinedButton(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            contentColor = color,
            containerColor = Color.Transparent
        ),
        shape = RoundedCornerShape(5.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp)
    ) {
        Text(
            text = text,
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun Alert(
    title: String,
    msj: String,
    confirmText: String,
    onConfirmClick: () -> Unit,
    onDismissClick: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismissClick,
        title = { Text(text = title) },
        text = { Text(text = msj) },
        shape = CutCornerShape(10.dp),
        confirmButton = {
            Button(onClick = onConfirmClick) {
                Text(text = confirmText)
            }
        }
    )
}