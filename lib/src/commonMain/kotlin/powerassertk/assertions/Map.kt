@file:Suppress("TooManyFunctions")

package powerassertk.assertions

import powerassertk.Assert
import kotlin.jvm.JvmName

// Helper function to check if map contains key-value pair (not transformed by Power Assert)
private fun <K, V> Map<K, V>.containsEntry(
    key: K,
    value: V,
): Boolean {
    @Suppress("SENSELESS_COMPARISON")
    return containsKey(key) && this[key] != null && this[key] == value
}

/**
 * Returns an Assert on the size for chaining.
 *
 * Note: This is a transformation method without a message parameter and
 * should NOT be added to the Power Assert functions list.
 */
fun <K, V> Assert<Map<K, V>>.size(): Assert<Int> = Assert(actual.size)

/**
 * Asserts the Map is empty.
 */
fun <K, V> Assert<Map<K, V>>.isEmpty(message: (() -> String)? = null) {
    if (actual.isNotEmpty()) {
        throw AssertionError(
            message?.invoke() ?: "expected to be empty but was:<$actual>",
        )
    }
}

/**
 * Asserts the Map is not empty.
 */
fun <K, V> Assert<Map<K, V>>.isNotEmpty(message: (() -> String)? = null) {
    if (actual.isEmpty()) {
        throw AssertionError(
            message?.invoke() ?: "expected not to be empty",
        )
    }
}

/**
 * Asserts the Map? is null or empty.
 */
@JvmName("isNullOrEmptyMapNullable")
fun <K, V> Assert<Map<K, V>?>.isNullOrEmpty(message: (() -> String)? = null) {
    if (actual != null && actual.isNotEmpty()) {
        throw AssertionError(
            message?.invoke() ?: "expected to be null or empty but was:<$actual>",
        )
    }
}

/**
 * Asserts the Map has the expected size.
 */
fun <K, V> Assert<Map<K, V>>.hasSize(
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
 * Asserts the Map has the same size as the other Map.
 */
fun <K, V> Assert<Map<K, V>>.hasSameSizeAs(
    other: Map<*, *>,
    message: (() -> String)? = null,
) {
    if (actual.size != other.size) {
        throw AssertionError(
            message?.invoke() ?: "expected to have same size as:<$other> (${other.size}) but was:<${actual.size}>",
        )
    }
}

/**
 * Asserts the Map contains the key-value pair.
 */
fun <K, V> Assert<Map<K, V>>.contains(
    key: K,
    value: V,
    message: (() -> String)? = null,
) {
    if (!actual.containsEntry(key, value)) {
        throw AssertionError(
            message?.invoke() ?: "expected to contain:<$key=$value> but was:<$actual>",
        )
    }
}

/**
 * Asserts the Map contains the element pair.
 */
fun <K, V> Assert<Map<K, V>>.contains(
    element: Pair<K, V>,
    message: (() -> String)? = null,
) {
    if (!actual.containsEntry(element.first, element.second)) {
        throw AssertionError(
            message?.invoke() ?: "expected to contain:<${element.first}=${element.second}> but was:<$actual>",
        )
    }
}

/**
 * Asserts the Map contains all the expected key-value pairs.
 */
fun <K, V> Assert<Map<K, V>>.containsAll(
    vararg elements: Pair<K, V>,
    message: (() -> String)? = null,
) {
    val notFound = elements.filter { actual[it.first] != it.second }
    if (notFound.isNotEmpty()) {
        throw AssertionError(
            message?.invoke()
                ?: "expected to contain all:<${
                    notFound.joinToString {
                        "${it.first}=${it.second}"
                    }
                }> but was:<$actual>",
        )
    }
}

/**
 * Asserts the Map contains at least the expected key-value pairs.
 */
fun <K, V> Assert<Map<K, V>>.containsAtLeast(
    vararg elements: Pair<K, V>,
    message: (() -> String)? = null,
) {
    val notFound = elements.filter { actual[it.first] != it.second }
    if (notFound.isNotEmpty()) {
        throw AssertionError(
            message?.invoke()
                ?: "expected to contain at least:<${
                    notFound.joinToString {
                        "${it.first}=${it.second}"
                    }
                }> but was:<$actual>",
        )
    }
}

/**
 * Asserts the Map contains only the expected key-value pairs.
 */
fun <K, V> Assert<Map<K, V>>.containsOnly(
    vararg elements: Pair<K, V>,
    message: (() -> String)? = null,
) {
    val expected = elements.toMap()
    if (actual != expected) {
        throw AssertionError(
            message?.invoke() ?: "expected to contain only:<$expected> but was:<$actual>",
        )
    }
}

/**
 * Asserts the Map contains none of the expected key-value pairs.
 */
fun <K, V> Assert<Map<K, V>>.containsNone(
    vararg elements: Pair<K, V>,
    message: (() -> String)? = null,
) {
    val found = elements.filter { actual[it.first] == it.second }
    if (found.isNotEmpty()) {
        throw AssertionError(
            message?.invoke()
                ?: "expected to contain none of:<${
                    found.joinToString {
                        "${it.first}=${it.second}"
                    }
                }> but was:<$actual>",
        )
    }
}

/**
 * Asserts the Map does not contain the key-value pair.
 */
fun <K, V> Assert<Map<K, V>>.doesNotContain(
    key: K,
    value: V,
    message: (() -> String)? = null,
) {
    if (actual.containsEntry(key, value)) {
        throw AssertionError(
            message?.invoke() ?: "expected not to contain:<$key=$value> but was:<$actual>",
        )
    }
}

/**
 * Asserts the Map does not contain the element pair.
 */
fun <K, V> Assert<Map<K, V>>.doesNotContain(
    element: Pair<K, V>,
    message: (() -> String)? = null,
) {
    if (actual.containsEntry(element.first, element.second)) {
        throw AssertionError(
            message?.invoke() ?: "expected not to contain:<${element.first}=${element.second}> but was:<$actual>",
        )
    }
}

/**
 * Asserts the Map does not contain the key.
 */
fun <K, V> Assert<Map<K, V>>.doesNotContainKey(
    key: K,
    message: (() -> String)? = null,
) {
    if (actual.containsKey(key)) {
        throw AssertionError(
            message?.invoke() ?: "expected not to contain key:<$key> but was:<$actual>",
        )
    }
}

/**
 * Returns an Assert on the value for the given key.
 *
 * Note: This is a transformation method without a message parameter and
 * should NOT be added to the Power Assert functions list.
 */
fun <K, V> Assert<Map<K, V>>.key(key: K): Assert<V> {
    val value =
        actual[key]
            ?: throw AssertionError("expected to contain key:<$key> but was:<$actual>")
    @Suppress("UNCHECKED_CAST")
    return Assert(value as V)
}
