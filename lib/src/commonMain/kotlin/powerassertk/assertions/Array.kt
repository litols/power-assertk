package powerassertk

/**
 * Returns an Assert on the size for chaining.
 *
 * Note: This is a transformation method without a message parameter and
 * should NOT be added to the Power Assert functions list.
 */
fun <T> Assert<Array<T>>.size(): Assert<Int> {
    return Assert(actual.size)
}

/**
 * Asserts the Array is empty.
 */
fun <T> Assert<Array<T>>.isEmpty(message: (() -> String)? = null) {
    if (actual.isNotEmpty()) {
        throw AssertionError(
            message?.invoke() ?: "expected to be empty but was:<${actual.contentToString()}>",
        )
    }
}

/**
 * Asserts the Array is not empty.
 */
fun <T> Assert<Array<T>>.isNotEmpty(message: (() -> String)? = null) {
    if (actual.isEmpty()) {
        throw AssertionError(
            message?.invoke() ?: "expected not to be empty",
        )
    }
}

/**
 * Asserts the Array? is null or empty.
 */
@JvmName("isNullOrEmptyArrayNullable")
fun <T> Assert<Array<T>?>.isNullOrEmpty(message: (() -> String)? = null) {
    if (actual != null && actual.isNotEmpty()) {
        throw AssertionError(
            message?.invoke() ?: "expected to be null or empty but was:<${actual.contentToString()}>",
        )
    }
}

/**
 * Asserts the Array has the expected size.
 */
fun <T> Assert<Array<T>>.hasSize(
    size: Int,
    message: (() -> String)? = null,
) {
    if (actual.size != size) {
        throw AssertionError(
            message?.invoke() ?: "expected to have size:<$size> but was:<${actual.size}>",
        )
    }
}

/**
 * Asserts the Array has the same size as the other Array.
 */
fun <T> Assert<Array<T>>.hasSameSizeAs(
    other: Array<*>,
    message: (() -> String)? = null,
) {
    if (actual.size != other.size) {
        throw AssertionError(
            message?.invoke() ?: "expected to have same size as:<${other.contentToString()}> (${other.size}) but was:<${actual.size}>",
        )
    }
}
