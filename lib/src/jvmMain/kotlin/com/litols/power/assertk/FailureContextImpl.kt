@file:Suppress("MatchingDeclarationName", "Filename")

package com.litols.power.assertk

internal actual object FailureContext {
    private val threadLocal = ThreadLocal.withInitial<MutableList<Failure>> { mutableListOf() }

    actual fun current(): Failure {
        val stack = threadLocal.get()
        return stack.lastOrNull() ?: SimpleFailure
    }

    actual fun pushFailure(failure: Failure) {
        threadLocal.get().add(failure)
    }

    actual fun popFailure() {
        val stack = threadLocal.get()
        if (stack.isNotEmpty()) {
            stack.removeLast()
        }
    }
}
