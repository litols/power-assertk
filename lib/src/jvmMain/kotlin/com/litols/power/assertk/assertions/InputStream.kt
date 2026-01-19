package com.litols.power.assertk.assertions

import com.litols.power.assertk.Assert
import java.io.InputStream

/**
 * Asserts the input stream has the same content as the expected stream.
 * Both streams will be closed after comparison.
 */
fun Assert<InputStream>.hasSameContentAs(
    expected: InputStream,
    message: (() -> String)? = null,
) {
    actual.use { actualStream ->
        expected.use { expectedStream ->
            val actualBytes = actualStream.readBytes()
            val expectedBytes = expectedStream.readBytes()
            if (!actualBytes.contentEquals(expectedBytes)) {
                throw AssertionError(
                    message?.invoke() ?: "expected streams to have same content but they differ",
                )
            }
        }
    }
}

/**
 * Asserts the input stream does not have the same content as the expected stream.
 * Both streams will be closed after comparison.
 */
fun Assert<InputStream>.hasNotSameContentAs(
    expected: InputStream,
    message: (() -> String)? = null,
) {
    actual.use { actualStream ->
        expected.use { expectedStream ->
            val actualBytes = actualStream.readBytes()
            val expectedBytes = expectedStream.readBytes()
            if (actualBytes.contentEquals(expectedBytes)) {
                throw AssertionError(
                    message?.invoke() ?: "expected streams to have different content but they are the same",
                )
            }
        }
    }
}
