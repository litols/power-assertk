package powerassertk

/**
 * Asserts the String is equal to the expected String, optionally ignoring case.
 */
fun Assert<String>.isEqualTo(
    other: String,
    ignoreCase: Boolean = false,
    message: (() -> String)? = null,
) {
    if (!actual.equals(other, ignoreCase)) {
        throw AssertionError(
            message?.invoke() ?: "expected:<\"$other\"> but was:<\"$actual\">",
        )
    }
}

/**
 * Asserts the nullable String is equal to the expected String, optionally ignoring case.
 */
@JvmName("isEqualToNullable")
fun Assert<String?>.isEqualTo(
    other: String?,
    ignoreCase: Boolean = false,
    message: (() -> String)? = null,
) {
    if (!actual.equals(other, ignoreCase)) {
        throw AssertionError(
            message?.invoke() ?: "expected:<\"$other\"> but was:<\"$actual\">",
        )
    }
}

/**
 * Asserts the String is not equal to the expected String, optionally ignoring case.
 */
fun Assert<String>.isNotEqualTo(
    other: String,
    ignoreCase: Boolean = false,
    message: (() -> String)? = null,
) {
    if (actual.equals(other, ignoreCase)) {
        throw AssertionError(
            message?.invoke() ?: "expected not to be equal to:<\"$other\">",
        )
    }
}

/**
 * Asserts the nullable String is not equal to the expected String, optionally ignoring case.
 */
@JvmName("isNotEqualToNullable")
fun Assert<String?>.isNotEqualTo(
    other: String?,
    ignoreCase: Boolean = false,
    message: (() -> String)? = null,
) {
    if (actual.equals(other, ignoreCase)) {
        throw AssertionError(
            message?.invoke() ?: "expected not to be equal to:<\"$other\">",
        )
    }
}
