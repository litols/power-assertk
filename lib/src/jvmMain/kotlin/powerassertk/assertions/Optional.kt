package powerassertk

import java.util.Optional

/**
 * Asserts the optional is present and returns an assertion on the value.
 */
fun <T> Assert<Optional<T>>.isPresent(): Assert<T> {
    if (!actual.isPresent) {
        throw AssertionError("expected to be present but was empty")
    }
    return Assert(actual.get())
}

/**
 * Asserts the optional is empty.
 */
fun <T> Assert<Optional<T>>.isEmpty(message: (() -> String)? = null) {
    if (actual.isPresent) {
        throw AssertionError(
            message?.invoke() ?: "expected to be empty but was:<${actual.get()}>",
        )
    }
}

/**
 * Asserts the optional has the expected value.
 */
fun <T> Assert<Optional<T>>.hasValue(
    expected: T,
    message: (() -> String)? = null,
) {
    if (!actual.isPresent) {
        throw AssertionError(
            message?.invoke() ?: "expected to have value:<$expected> but was empty",
        )
    }
    if (actual.get() != expected) {
        throw AssertionError(
            message?.invoke() ?: "expected:<$expected> but was:<${actual.get()}>",
        )
    }
}
