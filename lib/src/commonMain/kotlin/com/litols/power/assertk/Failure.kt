package com.litols.power.assertk

/**
 * Failure interface for handling assertion failures.
 */
internal sealed interface Failure {
    fun notify(error: AssertionError)

    companion object {
        fun soft(message: String = "The following assertions failed"): SoftFailure = SoftFailure(message)
    }
}

/**
 * Simple failure that immediately throws the error.
 */
internal object SimpleFailure : Failure {
    override fun notify(error: AssertionError): Unit = throw error
}

/**
 * Soft failure that collects errors for batch reporting.
 */
internal class SoftFailure(
    private val message: String,
) : Failure,
    AutoCloseable {
    private val failures = mutableListOf<AssertionError>()

    override fun notify(error: AssertionError) {
        failures.add(error)
    }

    inline operator fun <T> invoke(block: () -> T): T {
        FailureContext.pushFailure(this)
        try {
            val result = block()
            return result
        } finally {
            FailureContext.popFailure()
            if (failures.isNotEmpty()) {
                val count = failures.size
                val errorMessages = failures.joinToString("\n") { "  - ${it.message}" }
                throw AssertionError("$message ($count failures):\n$errorMessages")
            }
        }
    }

    override fun close() {
        FailureContext.popFailure()
    }
}

/**
 * Thread-local context for managing failure handlers.
 */
internal expect object FailureContext {
    fun current(): Failure

    fun pushFailure(failure: Failure)

    fun popFailure()
}

/**
 * Notify the current failure context of an assertion error.
 * In SimpleFailure mode, the error is thrown immediately.
 * In SoftFailure mode, the error is collected and execution continues.
 */
fun notifyFailure(error: AssertionError) {
    FailureContext.current().notify(error)
}
