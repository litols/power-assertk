package powerassertk

/**
 * Returns an Assert on the element at the given index for chaining.
 *
 * Note: This is a transformation method without a message parameter.
 */
fun <T> Assert<List<T>>.index(index: Int): Assert<T> {
    if (index < 0 || index >= actual.size) {
        throw AssertionError("expected to have element at index $index but size was ${actual.size}")
    }
    return Assert(actual[index])
}

/**
 * Asserts the List contains exactly the expected elements in the same order.
 */
fun <T> Assert<List<T>>.containsExactly(
    vararg elements: T,
    message: (() -> String)? = null,
) {
    if (actual.size != elements.size) {
        throw AssertionError(
            message?.invoke() ?: "expected to contain exactly:<${elements.toList()}> but was:<$actual>",
        )
    }
    actual.forEachIndexed { index, element ->
        if (element != elements[index]) {
            throw AssertionError(
                message?.invoke() ?: "expected to contain exactly:<${elements.toList()}> but was:<$actual>",
            )
        }
    }
}

/**
 * Asserts the List contains the given sublist.
 */
fun <T> Assert<List<T>>.containsSubList(
    sublist: List<T>,
    message: (() -> String)? = null,
) {
    if (sublist.isEmpty()) {
        return
    }

    for (i in 0..(actual.size - sublist.size)) {
        var found = true
        for (j in sublist.indices) {
            if (actual[i + j] != sublist[j]) {
                found = false
                break
            }
        }
        if (found) {
            return
        }
    }

    throw AssertionError(
        message?.invoke() ?: "expected to contain sublist:<$sublist> but was:<$actual>",
    )
}

/**
 * Asserts the List starts with the expected elements.
 */
fun <T> Assert<List<T>>.startsWith(
    vararg elements: T,
    message: (() -> String)? = null,
) {
    if (actual.size < elements.size) {
        throw AssertionError(
            message?.invoke() ?: "expected to start with:<${elements.toList()}> but was:<$actual>",
        )
    }
    elements.forEachIndexed { index, element ->
        if (actual[index] != element) {
            throw AssertionError(
                message?.invoke() ?: "expected to start with:<${elements.toList()}> but was:<$actual>",
            )
        }
    }
}

/**
 * Asserts the List ends with the expected elements.
 */
fun <T> Assert<List<T>>.endsWith(
    vararg elements: T,
    message: (() -> String)? = null,
) {
    if (actual.size < elements.size) {
        throw AssertionError(
            message?.invoke() ?: "expected to end with:<${elements.toList()}> but was:<$actual>",
        )
    }
    elements.forEachIndexed { index, element ->
        val actualIndex = actual.size - elements.size + index
        if (actual[actualIndex] != element) {
            throw AssertionError(
                message?.invoke() ?: "expected to end with:<${elements.toList()}> but was:<$actual>",
            )
        }
    }
}
