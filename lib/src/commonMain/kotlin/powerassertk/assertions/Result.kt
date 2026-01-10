package powerassertk

/**
 * Asserts the Result is a success and returns an Assert on the value.
 *
 * Note: This is a transformation method without a message parameter.
 */
fun <T> Assert<Result<T>>.isSuccess(): Assert<T> {
    if (actual.isFailure) {
        throw AssertionError("expected success but was failure:<${actual.exceptionOrNull()}>")
    }
    return Assert(actual.getOrThrow())
}

/**
 * Asserts the Result is a failure and returns an Assert on the exception.
 *
 * Note: This is a transformation method without a message parameter.
 */
fun <T> Assert<Result<T>>.isFailure(): Assert<Throwable> {
    if (actual.isSuccess) {
        throw AssertionError("expected failure but was success:<${actual.getOrNull()}>")
    }
    return Assert(actual.exceptionOrNull()!!)
}
