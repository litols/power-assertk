package powerassertk

import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class ResultTest {
    // isSuccess tests
    @Test
    fun isSuccess_succeeds_when_result_is_success() {
        val result: Result<Int> = Result.success(42)
        assertThat(result).isSuccess().isEqualTo(42)
    }

    @Test
    fun isSuccess_fails_when_result_is_failure() {
        val result: Result<Int> = Result.failure(RuntimeException("error"))

        val error =
            assertFailsWith<AssertionError> {
                assertThat(result).isSuccess()
            }
        val message = error.message!!

        val expectedFormat =
            """
            assertThat(result).isSuccess()
            |          |
            |          ${resultString(result)}
            """.trimIndent()

        assertTrue(
            message.contains(expectedFormat),
            "Should show proper Power Assert diagram:\nExpected:\n$expectedFormat\nActual:\n$message",
        )
    }

    @Test
    fun isSuccess_allows_chaining() {
        val result: Result<String> = Result.success("test")
        assertThat(result).isSuccess().hasLength(4)
    }

    @Test
    fun isSuccess_shows_power_assert_with_property_chain() {
        data class Response(val result: Result<Int>)
        val response = Response(Result.failure(RuntimeException("error")))

        val error =
            assertFailsWith<AssertionError> {
                assertThat(response.result).isSuccess()
            }
        val message = error.message!!

        val expectedFormat =
            """
            assertThat(response.result).isSuccess()
            |          |        |
            |          |        ${resultString(response.result)}
            |          Response(result=${resultString(response.result)})
            """.trimIndent()

        assertTrue(
            message.contains(expectedFormat),
            "Should show proper Power Assert diagram:\nExpected:\n$expectedFormat\nActual:\n$message",
        )
    }

    // isFailure tests
    @Test
    fun isFailure_succeeds_when_result_is_failure() {
        val exception = RuntimeException("error message")
        val result: Result<Int> = Result.failure(exception)
        assertThat(result).isFailure().hasMessage("error message")
    }

    @Test
    fun isFailure_fails_when_result_is_success() {
        val result: Result<Int> = Result.success(42)

        val error =
            assertFailsWith<AssertionError> {
                assertThat(result).isFailure()
            }
        val message = error.message!!

        val expectedFormat =
            """
            assertThat(result).isFailure()
            |          |
            |          Success(42)
            """.trimIndent()

        assertTrue(
            message.contains(expectedFormat),
            "Should show proper Power Assert diagram:\nExpected:\n$expectedFormat\nActual:\n$message",
        )
    }

    @Test
    fun isFailure_allows_chaining() {
        val exception = IllegalArgumentException("invalid")
        val result: Result<Int> = Result.failure(exception)
        assertThat(result).isFailure().messageContains("invalid")
    }

    // Complex chaining tests
    @Test
    fun can_chain_through_success_value() {
        data class Person(val name: String, val age: Int)
        val result: Result<Person> = Result.success(Person("Alice", 30))
        assertThat(result).isSuccess().isEqualTo(Person("Alice", 30))
    }

    @Test
    fun can_chain_through_failure_exception() {
        val cause = IllegalStateException("cause")
        val exception = RuntimeException("wrapper", cause)
        val result: Result<Int> = Result.failure(exception)
        assertThat(result).isFailure().cause().isNotNull()
    }
}
