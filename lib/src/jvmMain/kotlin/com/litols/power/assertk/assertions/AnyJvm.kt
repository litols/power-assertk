package com.litols.power.assertk.assertions

import com.litols.power.assertk.Assert
import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberProperties

/**
 * Returns an assertion on the object's Java class.
 */
fun <T : Any> Assert<T>.jClass(): Assert<Class<out T>> = Assert(actual.javaClass)

/**
 * Asserts the data class is equal to the expected by comparing all properties.
 * This provides more detailed error messages than simple equality.
 */
fun <T : Any> Assert<T>.isDataClassEqualTo(
    expected: T,
    message: (() -> String)? = null,
) {
    if (actual::class != expected::class) {
        throw AssertionError(
            message?.invoke() ?: "expected class:<${expected::class}> but was:<${actual::class}>",
        )
    }

    if (actual != expected) {
        throw AssertionError(
            message?.invoke() ?: "expected:<$expected> but was:<$actual>",
        )
    }
}

/**
 * Asserts the object is equal to expected, ignoring the given properties.
 */
@Suppress("UNCHECKED_CAST")
fun <T : Any> Assert<T>.isEqualToIgnoringGivenProperties(
    other: T,
    vararg properties: KProperty1<T, *>,
    message: (() -> String)? = null,
) {
    if (actual::class != other::class) {
        throw AssertionError(
            message?.invoke() ?: "expected class:<${other::class}> but was:<${actual::class}>",
        )
    }

    val propertiesToIgnore = properties.map { it.name }.toSet()

    // Get all properties using reflection
    val kClass = actual::class
    val memberProps = kClass.memberProperties

    val differences = mutableListOf<String>()
    for (property in memberProps) {
        if (property.name in propertiesToIgnore) continue

        try {
            @Suppress("UNCHECKED_CAST")
            val prop = property as KProperty1<T, Any?>
            val actualValue = prop.get(actual)
            val expectedValue = prop.get(other)
            if (actualValue != expectedValue) {
                differences.add("${property.name}: expected:<$expectedValue> but was:<$actualValue>")
            }
        } catch (_: Exception) {
            // Skip properties that can't be accessed
        }
    }

    if (differences.isNotEmpty()) {
        throw AssertionError(
            message?.invoke()
                ?: (
                    "expected objects to be equal ignoring $propertiesToIgnore, " +
                        "but found differences:\n${
                            differences.joinToString(
                                "\n",
                            )
                        }"
                ),
        )
    }
}
