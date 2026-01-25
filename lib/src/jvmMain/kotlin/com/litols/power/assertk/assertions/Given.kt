@file:JvmName("ShowJvm")

package com.litols.power.assertk.assertions

/**
 * Formats a value for display in error messages (JVM implementation).
 */
actual fun show(value: Any?): String =
    when (value) {
        null -> "null"
        is String -> "\"$value\""
        is Char -> "'$value'"
        is Long -> "${value}L"
        is Float -> "${value}f"
        is Double -> "${value}d"
        else -> value.toString()
    }
