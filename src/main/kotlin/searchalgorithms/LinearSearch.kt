package searchalgorithms

fun <T> linearSearch(list: List<T>, item: T): String {
    for ((index, value) in list.withIndex()) {
        if (value == item) return "linear search - $item found at index $index"
    }
    return "linear search - item was not found in the list"
}