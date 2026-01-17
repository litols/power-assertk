package powerassertk

/**
 * JS implementation of platform-specific expected format strings.
 * JS uses simple class names without package prefix (e.g., RuntimeException).
 * JS also formats numbers differently (0.0 becomes 0).
 */

actual fun throwableString(exception: Throwable): String {
    // JS toString() returns "RuntimeException: message" without java.lang prefix
    return exception.toString()
}

actual fun numberString(value: Number): String {
    // JS may format 0.0 as "0" and -10.0 as "-10"
    return value.toString()
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
    return "object" // JS shows [object Object] for Sequences/Arrays
}

actual fun arrayContentsString(contents: String): String {
    return "[...]" // JS shows [...] for array contents in data class toString()
}
