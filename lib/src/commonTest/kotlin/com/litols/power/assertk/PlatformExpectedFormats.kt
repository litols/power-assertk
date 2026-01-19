package com.litols.power.assertk

/**
 * Returns the platform-specific string representation of a Throwable.
 * JVM: "java.lang.RuntimeException: message"
 * JS: "RuntimeException: message"
 */
expect fun throwableString(exception: Throwable): String

/**
 * Returns the platform-specific string representation of a number.
 * JVM: 0.0 for Double/Float
 * JS: 0 for all numeric types (may lose decimal point)
 */
expect fun numberString(value: Number): String

/**
 * Returns the platform-specific string representation of a Result.
 * Handles the Throwable inside Result.Failure.
 */
expect fun resultString(result: Result<*>): String

/**
 * Returns a string to check for in Sequence/Array toString() output.
 * JVM: "Sequence" or similar type name
 * JS: "object" or "Object" (since JS toString() returns [object Object])
 */
expect fun sequenceTypeCheck(): String

/**
 * Returns the expected array contents string in data class toString().
 * JVM: "[1, 2, 3]"
 * JS: "[...]"
 */
expect fun arrayContentsString(contents: String): String
