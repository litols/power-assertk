package powerassertk

/**
 * Returns an Assert on the message for chaining.
 *
 * Note: This is a transformation method without a message parameter.
 */
fun <T : Throwable> Assert<T>.message(): Assert<String?> = Assert(actual.message)

/**
 * Asserts the Throwable has the expected message.
 */
fun <T : Throwable> Assert<T>.hasMessage(
    message: String?,
    messageParam: (() -> String)? = null,
) {
    if (actual.message != message) {
        throw AssertionError(
            messageParam?.invoke() ?: "expected message:<\"$message\"> but was:<\"${actual.message}\">",
        )
    }
}

/**
 * Asserts the Throwable's message contains the expected text.
 */
fun <T : Throwable> Assert<T>.messageContains(
    text: String,
    message: (() -> String)? = null,
) {
    val actualMessage = actual.message
    if (actualMessage == null || !actualMessage.contains(text)) {
        throw AssertionError(
            message?.invoke() ?: "expected message to contain:<\"$text\"> but was:<\"$actualMessage\">",
        )
    }
}

/**
 * Returns an Assert on the cause for chaining.
 *
 * Note: This is a transformation method without a message parameter.
 */
fun <T : Throwable> Assert<T>.cause(): Assert<Throwable?> = Assert(actual.cause)

/**
 * Asserts the Throwable has a cause with the same type and message.
 */
fun <T : Throwable> Assert<T>.hasCause(
    cause: Throwable,
    message: (() -> String)? = null,
) {
    val actualCause = actual.cause
    if (actualCause == null) {
        throw AssertionError(
            message?.invoke() ?: "expected to have cause:<$cause> but had no cause",
        )
    }
    if (actualCause::class != cause::class) {
        throw AssertionError(
            message?.invoke() ?: "expected cause type:<${cause::class}> but was:<${actualCause::class}>",
        )
    }
    if (actualCause.message != cause.message) {
        throw AssertionError(
            message?.invoke() ?: "expected cause message:<\"${cause.message}\"> but was:<\"${actualCause.message}\">",
        )
    }
}

/**
 * Asserts the Throwable has no cause.
 */
fun <T : Throwable> Assert<T>.hasNoCause(message: (() -> String)? = null) {
    if (actual.cause != null) {
        throw AssertionError(
            message?.invoke() ?: "expected to have no cause but had:<${actual.cause}>",
        )
    }
}

/**
 * Returns an Assert on the root cause for chaining.
 *
 * Note: This is a transformation method without a message parameter.
 */
fun <T : Throwable> Assert<T>.rootCause(): Assert<Throwable> {
    var current: Throwable = actual
    while (current.cause != null) {
        current = current.cause!!
    }
    if (current === actual) {
        throw AssertionError("expected to have a root cause but had no cause")
    }
    return Assert(current)
}

/**
 * Asserts the Throwable has a root cause with the same type and message.
 */
fun <T : Throwable> Assert<T>.hasRootCause(
    cause: Throwable,
    message: (() -> String)? = null,
) {
    var current: Throwable = actual
    while (current.cause != null) {
        current = current.cause!!
    }

    if (current === actual) {
        throw AssertionError(
            message?.invoke() ?: "expected to have a root cause but had no cause",
        )
    }

    if (current::class != cause::class) {
        throw AssertionError(
            message?.invoke() ?: "expected root cause type:<${cause::class}> but was:<${current::class}>",
        )
    }
    if (current.message != cause.message) {
        throw AssertionError(
            message?.invoke() ?: "expected root cause message:<\"${cause.message}\"> but was:<\"${current.message}\">",
        )
    }
}
