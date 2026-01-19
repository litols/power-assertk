package com.litols.power.assertk.assertions

import com.litols.power.assertk.assertThat
import java.io.ByteArrayInputStream
import java.io.InputStream
import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class InputStreamTest {
    // hasSameContentAs tests
    @Test
    fun hasSameContentAs_succeeds_when_content_is_same() {
        val content = "Hello World"
        val stream1: InputStream = ByteArrayInputStream(content.toByteArray())
        val stream2: InputStream = ByteArrayInputStream(content.toByteArray())
        assertThat(stream1).hasSameContentAs(stream2)
    }

    @Test
    fun hasSameContentAs_fails_when_content_differs() {
        data class StreamContainer(
            val stream: InputStream,
        )

        val stream1: InputStream = ByteArrayInputStream("Hello".toByteArray())
        val stream2: InputStream = ByteArrayInputStream("World".toByteArray())
        val container = StreamContainer(stream1)

        val error =
            assertFailsWith<AssertionError> {
                assertThat(container.stream).hasSameContentAs(stream2)
            }
        val message = error.message!!

        // InputStream toString() shows object addresses, so we verify structure
        assertTrue(
            message.contains("assertThat(container.stream).hasSameContentAs"),
            "Should show assertion expression",
        )
        assertTrue(
            message.contains("StreamContainer"),
            "Should show StreamContainer",
        )
    }

    @Test
    fun hasSameContentAs_supports_custom_message() {
        val stream1: InputStream = ByteArrayInputStream("Hello".toByteArray())
        val stream2: InputStream = ByteArrayInputStream("World".toByteArray())
        val error =
            assertFailsWith<AssertionError> {
                assertThat(stream1).hasSameContentAs(stream2) { "Custom: content should match" }
            }
        assertTrue(error.message!!.contains("Custom: content should match"))
    }

    @Test
    fun hasSameContentAs_works_with_empty_streams() {
        val stream1: InputStream = ByteArrayInputStream(ByteArray(0))
        val stream2: InputStream = ByteArrayInputStream(ByteArray(0))
        assertThat(stream1).hasSameContentAs(stream2)
    }

    // hasNotSameContentAs tests
    @Test
    fun hasNotSameContentAs_succeeds_when_content_differs() {
        val stream1: InputStream = ByteArrayInputStream("Hello".toByteArray())
        val stream2: InputStream = ByteArrayInputStream("World".toByteArray())
        assertThat(stream1).hasNotSameContentAs(stream2)
    }

    @Test
    fun hasNotSameContentAs_fails_when_content_is_same() {
        data class StreamContainer(
            val stream: InputStream,
        )

        val content = "Hello World"
        val stream1: InputStream = ByteArrayInputStream(content.toByteArray())
        val stream2: InputStream = ByteArrayInputStream(content.toByteArray())
        val container = StreamContainer(stream1)

        val error =
            assertFailsWith<AssertionError> {
                assertThat(container.stream).hasNotSameContentAs(stream2)
            }
        val message = error.message!!

        // InputStream toString() shows object addresses, so we verify structure
        assertTrue(
            message.contains("assertThat(container.stream).hasNotSameContentAs"),
            "Should show assertion expression",
        )
        assertTrue(
            message.contains("StreamContainer"),
            "Should show StreamContainer",
        )
    }

    @Test
    fun hasNotSameContentAs_supports_custom_message() {
        val content = "Hello World"
        val stream1: InputStream = ByteArrayInputStream(content.toByteArray())
        val stream2: InputStream = ByteArrayInputStream(content.toByteArray())
        val error =
            assertFailsWith<AssertionError> {
                assertThat(stream1).hasNotSameContentAs(stream2) { "Custom: content should differ" }
            }
        assertTrue(error.message!!.contains("Custom: content should differ"))
    }

    @Test
    fun hasNotSameContentAs_works_with_different_lengths() {
        val stream1: InputStream = ByteArrayInputStream("Hello".toByteArray())
        val stream2: InputStream = ByteArrayInputStream("Hello World".toByteArray())
        assertThat(stream1).hasNotSameContentAs(stream2)
    }
}
