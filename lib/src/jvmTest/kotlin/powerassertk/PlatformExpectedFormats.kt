package powerassertk

/**
 * JVM implementation of platform-specific expected format strings.
 * JVM includes the full qualified class name (e.g., java.lang.RuntimeException).
 */

actual fun throwableString(exception: Throwable): String {
    return exception.toString() // Returns "java.lang.RuntimeException: message"
}

actual fun numberString(value: Number): String {
    return value.toString() // JVM preserves decimal point for 0.0, 0.0f
}

actual fun resultString(result: Result<*>): String =
    when {
        result.isSuccess -> "Success(${result.getOrNull()})"
        else -> {
            val exception = result.exceptionOrNull()!!
            "Failure(${throwableString(exception)})"
        }
    }

actual fun sequenceTypeCheck(): String {
    return "Sequence" // JVM shows proper type names
}

actual fun arrayContentsString(contents: String): String {
    return contents // JVM shows full array contents like [1, 2, 3]
}
