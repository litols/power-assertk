package powerassertk.assertions

import powerassertk.Assert

/**
 * Asserts the Result is a success and returns an Assert on the value.
 */
fun <T> Assert<Result<T>>.isSuccess(message: (() -> String)? = null): Assert<T> {
    if (actual.isFailure) {
        throw AssertionError(message?.invoke() ?: "expected success but was failure:<${actual.exceptionOrNull()}>")
    }
    return Assert(actual.getOrThrow())
}

/**
 * Asserts the Result is a failure and returns an Assert on the exception.
 */
fun <T> Assert<Result<T>>.isFailure(message: (() -> String)? = null): Assert<Throwable> {
    if (actual.isSuccess) {
        throw AssertionError(message?.invoke() ?: "expected failure but was success:<${actual.getOrNull()}>")
    }
    return Assert(actual.exceptionOrNull()!!)
}
