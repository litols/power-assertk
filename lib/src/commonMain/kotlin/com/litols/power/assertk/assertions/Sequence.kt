@file:Suppress("TooManyFunctions")

package com.litols.power.assertk.assertions

import com.litols.power.assertk.Assert
import com.litols.power.assertk.notifyFailure

/**
 * Asserts the Sequence contains the expected element.
 */
fun <E> Assert<Sequence<E>>.contains(
    element: E,
    message: (() -> String)? = null,
) {
    if (element !in actual) {
        notifyFailure(
            AssertionError(
                message?.invoke() ?: "expected to contain:<$element> but was:<${actual.toList()}>",
            ),
        )
    }
}

/**
 * Asserts the Sequence does not contain the expected element.
 */
fun <E> Assert<Sequence<E>>.doesNotContain(
    element: E,
    message: (() -> String)? = null,
) {
    if (element in actual) {
        notifyFailure(
            AssertionError(
                message?.invoke() ?: "expected not to contain:<$element> but was:<${actual.toList()}>",
            ),
        )
    }
}

/**
 * Asserts the Sequence contains all the expected elements.
 */
fun <E> Assert<Sequence<E>>.containsAll(
    vararg elements: E,
    message: (() -> String)? = null,
) {
    val actualList = actual.toList()
    val notFound = elements.filter { it !in actualList }
    if (notFound.isNotEmpty()) {
        notifyFailure(
            AssertionError(
                message?.invoke() ?: "expected to contain all:<$notFound> but was:<$actualList>",
            ),
        )
    }
}

/**
 * Asserts the Sequence contains at least the expected elements.
 */
fun <E> Assert<Sequence<E>>.containsAtLeast(
    vararg elements: E,
    message: (() -> String)? = null,
) {
    val actualList = actual.toList()
    val notFound = elements.filter { it !in actualList }
    if (notFound.isNotEmpty()) {
        notifyFailure(
            AssertionError(
                message?.invoke() ?: "expected to contain at least:<$notFound> but was:<$actualList>",
            ),
        )
    }
}

/**
 * Asserts the Sequence contains only the expected elements (in any order).
 */
fun <E> Assert<Sequence<E>>.containsOnly(
    vararg elements: E,
    message: (() -> String)? = null,
) {
    val actualList = actual.toList()
    val expectedList = elements.toList()
    if (actualList.toSet() != expectedList.toSet() || actualList.size != expectedList.size) {
        notifyFailure(
            AssertionError(
                message?.invoke() ?: "expected to contain only:<$expectedList> but was:<$actualList>",
            ),
        )
    }
}

/**
 * Asserts the Sequence contains exactly the expected elements in the same order.
 */
fun <E> Assert<Sequence<E>>.containsExactly(
    vararg elements: E,
    message: (() -> String)? = null,
) {
    val actualList = actual.toList()
    if (actualList.size != elements.size) {
        notifyFailure(
            AssertionError(
                message?.invoke() ?: "expected to contain exactly:<${elements.toList()}> but was:<$actualList>",
            ),
        )
    }
    actualList.forEachIndexed { index, element ->
        if (element != elements[index]) {
            notifyFailure(
                AssertionError(
                    message?.invoke() ?: "expected to contain exactly:<${elements.toList()}> but was:<$actualList>",
                ),
            )
        }
    }
}

/**
 * Asserts the Sequence contains exactly the expected elements in any order.
 */
fun <E> Assert<Sequence<E>>.containsExactlyInAnyOrder(
    vararg elements: E,
    message: (() -> String)? = null,
) {
    val actualList = actual.toList()
    val expectedList = elements.toList()

    // Check sizes match
    if (actualList.size != expectedList.size) {
        notifyFailure(
            AssertionError(
                message?.invoke() ?: "expected to contain exactly in any order:<$expectedList> but was:<$actualList>",
            ),
        )
    }

    // Check each element in expected exists in actual
    val actualMutable = actualList.toMutableList()
    for (element in expectedList) {
        if (!actualMutable.remove(element)) {
            notifyFailure(
                AssertionError(
                    message?.invoke()
                        ?: "expected to contain exactly in any order:<$expectedList> but was:<$actualList>",
                ),
            )
        }
    }
}

/**
 * Asserts the Sequence contains none of the expected elements.
 */
fun <E> Assert<Sequence<E>>.containsNone(
    vararg elements: E,
    message: (() -> String)? = null,
) {
    val actualList = actual.toList()
    val found = elements.filter { it in actualList }
    if (found.isNotEmpty()) {
        notifyFailure(
            AssertionError(
                message?.invoke() ?: "expected to contain none of:<$found> but was:<$actualList>",
            ),
        )
    }
}

/**
 * Asserts the Sequence is empty.
 */
fun <E> Assert<Sequence<E>>.isEmpty(message: (() -> String)? = null) {
    if (actual.iterator().hasNext()) {
        notifyFailure(
            AssertionError(
                message?.invoke() ?: "expected to be empty but was:<${actual.toList()}>",
            ),
        )
    }
}

/**
 * Asserts the Sequence is not empty.
 */
fun <E> Assert<Sequence<E>>.isNotEmpty(message: (() -> String)? = null) {
    if (!actual.iterator().hasNext()) {
        notifyFailure(
            AssertionError(
                message?.invoke() ?: "expected not to be empty",
            ),
        )
    }
}

/**
 * Asserts all elements satisfy the given assertion.
 */
fun <E> Assert<Sequence<E>>.each(
    message: (() -> String)? = null,
    f: (Assert<E>) -> Unit,
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
fun <E> Assert<Sequence<E>>.any(
    message: (() -> String)? = null,
    f: (Assert<E>) -> Unit,
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
fun <E> Assert<Sequence<E>>.none(
    message: (() -> String)? = null,
    f: (Assert<E>) -> Unit,
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
fun <E> Assert<Sequence<E>>.atLeast(
    times: Int,
    message: (() -> String)? = null,
    f: (Assert<E>) -> Unit,
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
fun <E> Assert<Sequence<E>>.atMost(
    times: Int,
    message: (() -> String)? = null,
    f: (Assert<E>) -> Unit,
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
fun <E> Assert<Sequence<E>>.exactly(
    times: Int,
    message: (() -> String)? = null,
    f: (Assert<E>) -> Unit,
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
fun <E> Assert<Sequence<E>>.first(): Assert<E> {
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
fun <E> Assert<Sequence<E>>.single(): Assert<E> {
    val list = actual.toList()
    if (list.size != 1) {
        notifyFailure(
            AssertionError("expected to have exactly one element but had ${list.size}"),
        )
        // In soft failure mode, return a dummy value
        @Suppress("UNCHECKED_CAST")
        return Assert(null as E)
    }
    return Assert(list.first())
}

/**
 * Extracts values using the given function and returns an Assert on the resulting list.
 *
 * Note: This is a transformation method without a message parameter.
 */
fun <E, R> Assert<Sequence<E>>.extracting(f: (E) -> R): Assert<List<R>> = Assert(actual.map(f).toList())

/**
 * Extracts values using two functions and returns an Assert on the resulting list of pairs.
 *
 * Note: This is a transformation method without a message parameter.
 */
fun <E, R1, R2> Assert<Sequence<E>>.extracting(
    f1: (E) -> R1,
    f2: (E) -> R2,
): Assert<List<Pair<R1, R2>>> = Assert(actual.map { f1(it) to f2(it) }.toList())

/**
 * Extracts values using three functions and returns an Assert on the resulting list of triples.
 *
 * Note: This is a transformation method without a message parameter.
 */
fun <E, R1, R2, R3> Assert<Sequence<E>>.extracting(
    f1: (E) -> R1,
    f2: (E) -> R2,
    f3: (E) -> R3,
): Assert<List<Triple<R1, R2, R3>>> = Assert(actual.map { Triple(f1(it), f2(it), f3(it)) }.toList())
