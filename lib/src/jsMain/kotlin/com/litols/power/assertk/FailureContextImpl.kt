@file:Suppress("MatchingDeclarationName", "Filename")

package com.litols.power.assertk

internal actual object FailureContext {
    private val stack = mutableListOf<Failure>()

    actual fun current(): Failure = stack.lastOrNull() ?: SimpleFailure

    actual fun pushFailure(failure: Failure) {
        stack.add(failure)
    }

    actual fun popFailure() {
        if (stack.isNotEmpty()) {
            stack.removeLast()
        }
    }
}
