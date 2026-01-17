package powerassertk.assertions

import powerassertk.Assert

/**
 * Returns an assertion on the throwable's stack trace as a list of strings.
 */
fun <T : Throwable> Assert<T>.stackTrace(): Assert<List<String>> {
    val stackTraceStrings = actual.stackTrace.map { it.toString() }
    return Assert(stackTraceStrings)
}
