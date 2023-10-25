package ordenationalgorithms

fun bubbleSort(list: List<Int>): String {

    val newList: MutableList<Int> = list.toMutableList()

    val listLength = list.size - 1

    for (indice in 0..<listLength) {
        for (internalIndice in 0..<listLength) {
            if (newList[internalIndice] > newList[internalIndice + 1]) {
                val alx = newList[internalIndice]
                newList[internalIndice] = newList[internalIndice + 1]
                newList[internalIndice + 1] = alx
            }
        }
    }
    return newList.toString()
}