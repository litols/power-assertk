@file:Suppress("TooManyFunctions")

package com.litols.power.assertk.assertions

import com.litols.power.assertk.Assert
import com.litols.power.assertk.notifyFailure
import kotlin.jvm.JvmName

/**
 * Returns an Assert on the size for chaining.
 *
 * Note: This is a transformation method without a message parameter and
 * should NOT be added to the Power Assert functions list.
 */
fun <T> Assert<Array<T>>.size(): Assert<Int> = Assert(actual.size)

/**
 * Asserts the Array is empty.
 */
fun <T> Assert<Array<T>>.isEmpty(message: (() -> String)? = null) {
    if (actual.isNotEmpty()) {
        notifyFailure(
            AssertionError(
                message?.invoke() ?: "expected to be empty but was:<${actual.contentToString()}>",
            ),
        )
    }
}

/**
 * Asserts the Array is not empty.
 */
fun <T> Assert<Array<T>>.isNotEmpty(message: (() -> String)? = null) {
    if (actual.isEmpty()) {
        notifyFailure(
            AssertionError(
                message?.invoke() ?: "expected not to be empty",
            ),
        )
    }
}

/**
 * Asserts the Array? is null or empty.
 */
@JvmName("isNullOrEmptyArrayNullable")
fun <T> Assert<Array<T>?>.isNullOrEmpty(message: (() -> String)? = null) {
    if (actual != null && actual.isNotEmpty()) {
        notifyFailure(
            AssertionError(
                message?.invoke() ?: "expected to be null or empty but was:<${actual.contentToString()}>",
            ),
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
        notifyFailure(
            AssertionError(
                message?.invoke() ?: "expected to have size:<$size> but was:<${actual.size}>",
            ),
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
        notifyFailure(
            AssertionError(
                message?.invoke()
                    ?: (
                        "expected to have same size as:<${other.contentToString()}> (${other.size}) " +
                            "but was:<${actual.size}>"
                    ),
            ),
        )
    }
}

/**
 * Asserts the Array contains the expected element.
 */
fun <T> Assert<Array<T>>.contains(
    element: T,
    message: (() -> String)? = null,
) {
    if (element !in actual) {
        notifyFailure(
            AssertionError(
                message?.invoke() ?: "expected to contain:<$element> but was:<${actual.contentToString()}>",
            ),
        )
    }
}

/**
 * Asserts the Array does not contain the expected element.
 */
fun <T> Assert<Array<T>>.doesNotContain(
    element: T,
    message: (() -> String)? = null,
) {
    if (element in actual) {
        notifyFailure(
            AssertionError(
                message?.invoke() ?: "expected not to contain:<$element> but was:<${actual.contentToString()}>",
            ),
        )
    }
}

/**
 * Asserts the Array contains all the expected elements.
 */
fun <T> Assert<Array<T>>.containsAll(
    vararg elements: T,
    message: (() -> String)? = null,
) {
    val notFound = elements.filter { it !in actual }
    if (notFound.isNotEmpty()) {
        notifyFailure(
            AssertionError(
                message?.invoke() ?: "expected to contain all:<$notFound> but was:<${actual.contentToString()}>",
            ),
        )
    }
}

/**
 * Asserts the Array contains at least the expected elements.
 */
fun <T> Assert<Array<T>>.containsAtLeast(
    vararg elements: T,
    message: (() -> String)? = null,
) {
    val notFound = elements.filter { it !in actual }
    if (notFound.isNotEmpty()) {
        notifyFailure(
            AssertionError(
                message?.invoke() ?: "expected to contain at least:<$notFound> but was:<${actual.contentToString()}>",
            ),
        )
    }
}

/**
 * Asserts the Array contains only the expected elements (in any order).
 */
fun <T> Assert<Array<T>>.containsOnly(
    vararg elements: T,
    message: (() -> String)? = null,
) {
    if (actual.toSet() != elements.toSet() || actual.size != elements.size) {
        notifyFailure(
            AssertionError(
                message?.invoke()
                    ?: "expected to contain only:<${elements.contentToString()}> but was:<${actual.contentToString()}>",
            ),
        )
    }
}

/**
 * Asserts the Array contains exactly the expected elements in the same order.
 */
fun <T> Assert<Array<T>>.containsExactly(
    vararg elements: T,
    message: (() -> String)? = null,
) {
    if (!actual.contentEquals(elements)) {
        notifyFailure(
            AssertionError(
                message?.invoke()
                    ?: "expected to contain exactly:<${elements.contentToString()}> but was:<${actual.contentToString()}>",
            ),
        )
    }
}

/**
 * Asserts the Array contains exactly the expected elements in any order.
 */
fun <T> Assert<Array<T>>.containsExactlyInAnyOrder(
    vararg elements: T,
    message: (() -> String)? = null,
) {
    // Check sizes match
    if (actual.size != elements.size) {
        notifyFailure(
            AssertionError(
                message?.invoke()
                    ?: (
                        "expected to contain exactly in any order:<${elements.contentToString()}>" +
                            " but was:<${actual.contentToString()}>"
                    ),
            ),
        )
    }

    // Check each element in expected exists in actual
    val actualMutable = actual.toMutableList()
    for (element in elements) {
        if (!actualMutable.remove(element)) {
            notifyFailure(
                AssertionError(
                    message?.invoke()
                        ?: (
                            "expected to contain exactly in any order:<${elements.contentToString()}>" +
                                " but was:<${actual.contentToString()}>"
                        ),
                ),
            )
        }
    }
}

/**
 * Asserts the Array contains none of the expected elements.
 */
fun <T> Assert<Array<T>>.containsNone(
    vararg elements: T,
    message: (() -> String)? = null,
) {
    val found = elements.filter { it in actual }
    if (found.isNotEmpty()) {
        notifyFailure(
            AssertionError(
                message?.invoke() ?: "expected to contain none of:<$found> but was:<${actual.contentToString()}>",
            ),
        )
    }
}

/**
 * Asserts all elements satisfy the given assertion.
 */
fun <T> Assert<Array<T>>.each(
    message: (() -> String)? = null,
    f: (Assert<T>) -> Unit,
) {
    actual.forEachIndexed { index, element ->
        try {
            f(
                Assert(element),
            )
        } catch (e: AssertionError) {
            notifyFailure(
                AssertionError(
                    message?.invoke() ?: "element at index $index failed assertion: ${e.message}",
                ),
            )
        }
    }
}

/**
 * Asserts at least one element satisfies the given assertion.
 */
fun <T> Assert<Array<T>>.any(
    message: (() -> String)? = null,
    f: (Assert<T>) -> Unit,
) {
    val passed =
        actual.any { element ->
            try {
                f(
                    Assert(element),
                )
                true
            } catch (e: AssertionError) {
                false
            }
        }
    if (!passed) {
        notifyFailure(
            AssertionError(
                message?.invoke() ?: "expected at least one element to satisfy the assertion",
            ),
        )
    }
}

/**
 * Asserts no elements satisfy the given assertion.
 */
fun <T> Assert<Array<T>>.none(
    message: (() -> String)? = null,
    f: (Assert<T>) -> Unit,
) {
    actual.forEachIndexed { index, element ->
        try {
            f(
                Assert(element),
            )
            notifyFailure(
                AssertionError(
                    message?.invoke() ?: "element at index $index unexpectedly satisfied the assertion",
                ),
            )
        } catch (e: AssertionError) {
            // Expected to fail
        }
    }
}

/**
 * Asserts at least the specified number of elements satisfy the given assertion.
 */
fun <T> Assert<Array<T>>.atLeast(
    times: Int,
    message: (() -> String)? = null,
    f: (Assert<T>) -> Unit,
) {
    val count =
        actual.count { element ->
            try {
                f(
                    Assert(element),
                )
                true
            } catch (e: AssertionError) {
                false
            }
        }
    if (count < times) {
        notifyFailure(
            AssertionError(
                message?.invoke() ?: "expected at least $times elements to satisfy the assertion but $count did",
            ),
        )
    }
}

/**
 * Asserts at most the specified number of elements satisfy the given assertion.
 */
fun <T> Assert<Array<T>>.atMost(
    times: Int,
    message: (() -> String)? = null,
    f: (Assert<T>) -> Unit,
) {
    val count =
        actual.count { element ->
            try {
                f(
                    Assert(element),
                )
                true
            } catch (e: AssertionError) {
                false
            }
        }
    if (count > times) {
        notifyFailure(
            AssertionError(
                message?.invoke() ?: "expected at most $times elements to satisfy the assertion but $count did",
            ),
        )
    }
}

/**
 * Asserts exactly the specified number of elements satisfy the given assertion.
 */
fun <T> Assert<Array<T>>.exactly(
    times: Int,
    message: (() -> String)? = null,
    f: (Assert<T>) -> Unit,
) {
    val count =
        actual.count { element ->
            try {
                f(
                    Assert(element),
                )
                true
            } catch (e: AssertionError) {
                false
            }
        }
    if (count != times) {
        notifyFailure(
            AssertionError(
                message?.invoke() ?: "expected exactly $times elements to satisfy the assertion but $count did",
            ),
        )
    }
}

/**
 * Returns an Assert on the first element for chaining.
 *
 * Note: This is a transformation method without a message parameter.
 */
fun <T> Assert<Array<T>>.first(): Assert<T> {
    val element =
        actual.firstOrNull()
            ?: throw AssertionError("expected to have at least one element but was empty")
    return Assert(element)
}

/**
 * Returns an Assert on the single element for chaining.
 *
 * Note: This is a transformation method without a message parameter.
 */
fun <T> Assert<Array<T>>.single(): Assert<T> {
    if (actual.size != 1) {
        notifyFailure(
            AssertionError("expected to have exactly one element but had ${actual.size}"),
        )
        // In soft failure mode, return a dummy value
        @Suppress("UNCHECKED_CAST")
        return Assert(null as T)
    }
    return Assert(actual.first())
}

/**
 * Returns an Assert on the element at the given index for chaining.
 *
 * Note: This is a transformation method without a message parameter.
 */
fun <T> Assert<Array<T>>.index(index: Int): Assert<T> {
    if (index < 0 || index >= actual.size) {
        notifyFailure(
            AssertionError("expected to have element at index $index but size was ${actual.size}"),
        )
        // In soft failure mode, return a dummy value
        @Suppress("UNCHECKED_CAST")
        return Assert(null as T)
    }
    return Assert(actual[index])
}

/**
 * Extracts values using the given function and returns an Assert on the resulting list.
 *
 * Note: This is a transformation method without a message parameter.
 */
fun <T, R> Assert<Array<T>>.extracting(f: (T) -> R): Assert<List<R>> = Assert(actual.map(f))

/**
 * Extracts values using two functions and returns an Assert on the resulting list of pairs.
 *
 * Note: This is a transformation method without a message parameter.
 */
fun <T, R1, R2> Assert<Array<T>>.extracting(
    f1: (T) -> R1,
    f2: (T) -> R2,
): Assert<List<Pair<R1, R2>>> = Assert(actual.map { f1(it) to f2(it) })

/**
 * Extracts values using three functions and returns an Assert on the resulting list of triples.
 *
 * Note: This is a transformation method without a message parameter.
 */
fun <T, R1, R2, R3> Assert<Array<T>>.extracting(
    f1: (T) -> R1,
    f2: (T) -> R2,
    f3: (T) -> R3,
): Assert<List<Triple<R1, R2, R3>>> = Assert(actual.map { Triple(f1(it), f2(it), f3(it)) })
