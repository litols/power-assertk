package powerassertk

fun <T> Assert<T>.isEqualTo(expected: T, message: (() -> String)? = null) {
    if (actual != expected) {
        throw AssertionError(
            message?.invoke() ?: "expected:<$expected> but was:<$actual>"
        )
    }
}
