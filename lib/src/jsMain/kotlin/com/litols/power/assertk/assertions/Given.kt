@file:Suppress("MatchingDeclarationName")

package com.litols.power.assertk.assertions

/**
 * Formats a value for display in error messages (JS implementation).
 * Note: In Kotlin/JS, all numeric types are represented as JavaScript Number,
 * so type-specific formatting (like "42L" for Long) is not possible.
 */
actual fun show(value: Any?): String =
    when (value) {
        null -> "null"
        is String -> "\"$value\""
        is Char -> "'$value'"
        else -> value.toString()
    }
