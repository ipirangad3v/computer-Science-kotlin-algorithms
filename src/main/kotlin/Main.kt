import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import kotlinx.coroutines.launch
import ordenationalgorithms.bubbleSort
import ordenationalgorithms.insertionSort
import ordenationalgorithms.ui.SortType
import ordenationalgorithms.ui.SortType.*
import searchalgorithms.binarySearch
import searchalgorithms.linearSearch
import searchalgorithms.ui.SearchType
import searchalgorithms.ui.SearchType.BINARY
import searchalgorithms.ui.SearchType.LINEAR
import utils.runBlockAndBenchmark

fun main() = application {

    val list = listOf(1, 2, 3, 4, 5)
    val unsortedList = listOf(9, 32, 44, 11344, 55, 102, 0, 8, 10, 30, 60)

    Window(onCloseRequest = ::exitApplication) {

        val coroutineScope = rememberCoroutineScope()

        var selectedSearchStyle by remember { mutableStateOf(LINEAR) }

        var selectedSortStyle by remember { mutableStateOf(BUBBLE) }

        var searchTerm by remember { mutableStateOf(TextFieldValue()) }

        var resultString by remember { mutableStateOf("") }

        LazyColumn(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize().padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Card {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp),
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
                            SearchType.entries.forEach {
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
                                    coroutineScope.launch {
                                        resultString = if (it.isDigit()) {
                                            runSearch(list, it.toInt(), selectedSearchStyle)
                                        } else {
                                            "$it is not a valid number"
                                        }
                                    }
                                }

                            },
                        ) {
                            Text("Search")
                        }
                    }
                }
            }
            item {
                Spacer(Modifier.size(8.dp))
            }
            item {
                Card {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxSize().padding(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("sort algorithms")
                        Text("unsorted list: $unsortedList")
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            SortType.entries.forEach {
                                Column(
                                    verticalArrangement = Arrangement.SpaceBetween,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(it.name)
                                    RadioButton(
                                        selected = selectedSortStyle == it,
                                        onClick = {
                                            selectedSortStyle = it
                                        }
                                    )
                                }
                            }
                        }
                        Button(
                            onClick = {
                                coroutineScope.launch {
                                    resultString = runSort(unsortedList, selectedSortStyle)
                                }
                            },
                        ) {
                            Text("Sort")
                        }
                    }
                }
            }
            item {
                Text(resultString)
            }
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

private fun runSearch(list: List<Int>, element: Int, selectedSearchStyle: SearchType) = runBlockAndBenchmark {
    when (selectedSearchStyle) {
        LINEAR -> {
            linearSearch(list, element)
        }

        BINARY -> {
            binarySearch(list, element)
        }
    }
}

private fun runSort(list: List<Int>, selectedSortStyle: SortType) = runBlockAndBenchmark {
    when (selectedSortStyle) {
        BUBBLE -> {
            bubbleSort(list)
        }

        INSERTION -> {
            insertionSort(list)
        }

        SELECTION -> TODO()
        QUICK -> TODO()
        MERGE -> TODO()
    }
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