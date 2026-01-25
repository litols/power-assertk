@file:Suppress("MatchingDeclarationName")

package com.litols.power.assertk.assertions

/**
 * Formats a value for display in error messages (Native implementation).
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
