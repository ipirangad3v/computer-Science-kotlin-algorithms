package searchalgorithms

fun binarySearch(list: List<Int>, item: Int): String {
    var firstIndex = 0
    var lastIndex = list.size - 1

    while (lastIndex > firstIndex) {

        val midIndex = (firstIndex + lastIndex) / 2
        if (list[midIndex] == item)
            return "binary search - $item found at $midIndex"
        else
            if (item < list[midIndex])
                lastIndex = midIndex - 1
            else firstIndex = midIndex + 1


    }
    return "binary search - item not found at the list"

}