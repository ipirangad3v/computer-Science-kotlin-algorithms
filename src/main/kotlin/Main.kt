import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import searchalgorithms.binarySearch
import searchalgorithms.linearSearch
import searchalgorithms.ui.SearchType


fun main() = application {

    val list = listOf(1, 2, 3, 4, 5)

    Window(onCloseRequest = ::exitApplication) {

        var selectedSearchStyle by remember { mutableStateOf(SearchType.LINEAR) }

        var searchTerm by remember { mutableStateOf(TextFieldValue()) }

        var resultString by remember { mutableStateOf("") }

        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxSize().padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("search algorithms")
            Text("list: $list")
            IntOnlyTextField(searchTerm) {
                searchTerm = it
            }
            Text("Search type")
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                SearchType.values().forEach {
                    Column(
                        verticalArrangement = Arrangement.SpaceBetween,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(it.name)
                        RadioButton(
                            selected = selectedSearchStyle == it,
                            onClick = {
                                selectedSearchStyle = it
                            }
                        )
                    }
                }
            }
            Button(
                onClick = {
                    searchTerm.text.let {
                        resultString = if (it.isDigit()) {
                            runSearch(list, it.toInt(), selectedSearchStyle)
                        } else {
                            "$it is not a valid number"
                        }
                    }

                },
            ) {
                Text("Search")
            }
            Text(resultString)
        }
    }
}

private fun String.isDigit(): Boolean {

    if (this.isEmpty()) {
        return false
    }

    return try {
        this.toInt()
        true
    } catch (e: Exception) {
        false
    }
}

fun runSearch(list: List<Int>, element: Int, selectedSearchStyle: SearchType): String {
    val startTime = System.currentTimeMillis()


    val result = when (selectedSearchStyle) {
        SearchType.LINEAR -> {
            linearSearch(list, element)
        }

        SearchType.BINARY -> {
            binarySearch(list, element)
        }
    }
    val endTime = System.currentTimeMillis()

    val executionTime = endTime - startTime

    return "$result - fetch time: $executionTime milis"
}


@Composable
fun IntOnlyTextField(value: TextFieldValue, onValueChange: (TextFieldValue) -> Unit) {
    val keyboardOptions =
        remember { KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number) }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        keyboardOptions = keyboardOptions,
        keyboardActions = KeyboardActions.Default,
        textStyle = TextStyle.Default,
        singleLine = true,
        modifier = Modifier.fillMaxWidth(),
    )
}