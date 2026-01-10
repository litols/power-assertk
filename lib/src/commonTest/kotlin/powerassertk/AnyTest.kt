package powerassertk

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class AnyTest {
    // isNotEqualTo tests
    @Test
    fun isNotEqualTo_succeeds_when_values_are_not_equal() {
        assertThat(42).isNotEqualTo(100)
        assertThat("hello").isNotEqualTo("world")
        assertThat(null).isNotEqualTo("something")
    }

    @Test
    fun isNotEqualTo_fails_when_values_are_equal() {
        val error =
            assertFailsWith<AssertionError> {
                assertThat(42).isNotEqualTo(42)
            }
        val message = error.message!!

        val expectedFormat =
            """
            assertThat(42).isNotEqualTo(42)
            |
            """.trimIndent()

        assertTrue(
            message.contains(expectedFormat),
            "Should show proper Power Assert format:\nExpected:\n$expectedFormat\nActual:\n$message",
        )
    }

    @Test
    fun isNotEqualTo_supports_custom_message() {
        val error =
            assertFailsWith<AssertionError> {
                assertThat(5).isNotEqualTo(5) { "Custom: should not match" }
            }
        val message = error.message!!

        val expectedFormat =
            """
            Custom: should not match
            assertThat(5).isNotEqualTo(5) { "Custom: should not match" }
            |
            """.trimIndent()

        assertTrue(
            message.contains(expectedFormat),
            "Should show proper custom message format:\nExpected:\n$expectedFormat\nActual:\n$message",
        )
    }

    // isNull tests
    @Test
    fun isNull_succeeds_when_value_is_null() {
        assertThat(null).isNull()
        assertThat<String?>(null).isNull()
    }

    @Test
    fun isNull_fails_when_value_is_not_null() {
        val error =
            assertFailsWith<AssertionError> {
                assertThat("hello").isNull()
            }
        val message = error.message!!

        val expectedFormat =
            """
            assertThat("hello").isNull()
            |
            """.trimIndent()

        assertTrue(
            message.contains(expectedFormat),
            "Should show proper Power Assert format:\nExpected:\n$expectedFormat\nActual:\n$message",
        )
    }

    @Test
    fun isNull_supports_custom_message() {
        val error =
            assertFailsWith<AssertionError> {
                assertThat(42).isNull() { "Custom: expected null" }
            }
        val message = error.message!!

        val expectedFormat =
            """
            Custom: expected null
            assertThat(42).isNull() { "Custom: expected null" }
            |
            """.trimIndent()

        assertTrue(
            message.contains(expectedFormat),
            "Should show proper custom message format:\nExpected:\n$expectedFormat\nActual:\n$message",
        )
    }

    // isNotNull tests
    @Test
    fun isNotNull_succeeds_when_value_is_not_null() {
        val result: Assert<String> = assertThat<String?>("hello").isNotNull()
        result.isEqualTo("hello")
    }

    @Test
    fun isNotNull_fails_when_value_is_null() {
        val error =
            assertFailsWith<AssertionError> {
                assertThat<String?>(null).isNotNull()
            }
        val message = error.message!!

        val expectedFormat =
            """
            assertThat<String?>(null).isNotNull()
            |
            """.trimIndent()

        assertTrue(
            message.contains(expectedFormat),
            "Should show proper Power Assert format:\nExpected:\n$expectedFormat\nActual:\n$message",
        )
    }

    @Test
    fun isNotNull_supports_custom_message() {
        val error =
            assertFailsWith<AssertionError> {
                assertThat<Int?>(null).isNotNull() { "Custom: must be non-null" }
            }
        val message = error.message!!

        val expectedFormat =
            """
            Custom: must be non-null
            assertThat<Int?>(null).isNotNull() { "Custom: must be non-null" }
            |
            """.trimIndent()

        assertTrue(
            message.contains(expectedFormat),
            "Should show proper custom message format:\nExpected:\n$expectedFormat\nActual:\n$message",
        )
    }

    // isSameInstanceAs tests
    @Test
    fun isSameInstanceAs_succeeds_when_instances_are_same() {
        val obj = "hello"
        assertThat(obj).isSameInstanceAs(obj)
    }

    @Test
    fun isSameInstanceAs_fails_when_instances_are_different() {
        val obj1 = Person("Alice", 30)
        val obj2 = Person("Alice", 30)
        val error =
            assertFailsWith<AssertionError> {
                assertThat(obj1).isSameInstanceAs(obj2)
            }
        val message = error.message!!
        assertTrue(message.contains("assertThat(obj1).isSameInstanceAs(obj2)"))
    }

    @Test
    fun isSameInstanceAs_supports_custom_message() {
        val obj1 = Person("Alice", 30)
        val obj2 = Person("Bob", 40)
        val error =
            assertFailsWith<AssertionError> {
                assertThat(obj1).isSameInstanceAs(obj2) { "Custom: not same instance" }
            }
        val message = error.message!!
        assertTrue(message.contains("Custom: not same instance"))
        assertTrue(message.contains("assertThat(obj1).isSameInstanceAs(obj2)"))
    }

    // isNotSameInstanceAs tests
    @Test
    fun isNotSameInstanceAs_succeeds_when_instances_are_different() {
        val obj1 = Person("Alice", 30)
        val obj2 = Person("Alice", 30)
        assertThat(obj1).isNotSameInstanceAs(obj2)
    }

    @Test
    fun isNotSameInstanceAs_fails_when_instances_are_same() {
        val obj = "hello"
        val error =
            assertFailsWith<AssertionError> {
                assertThat(obj).isNotSameInstanceAs(obj)
            }
        val message = error.message!!
        assertTrue(message.contains("assertThat(obj).isNotSameInstanceAs(obj)"))
    }

    @Test
    fun isNotSameInstanceAs_supports_custom_message() {
        val obj = 42
        val error =
            assertFailsWith<AssertionError> {
                assertThat(obj).isNotSameInstanceAs(obj) { "Custom: should be different instance" }
            }
        val message = error.message!!
        assertTrue(message.contains("Custom: should be different instance"))
        assertTrue(message.contains("assertThat(obj).isNotSameInstanceAs(obj)"))
    }

    // isSameAs tests (alias)
    @Test
    fun isSameAs_succeeds_when_instances_are_same() {
        val obj = Person("Alice", 30)
        assertThat(obj).isSameAs(obj)
    }

    @Test
    fun isSameAs_fails_when_instances_are_different() {
        val obj1 = Person("Alice", 30)
        val obj2 = Person("Alice", 30)
        val error =
            assertFailsWith<AssertionError> {
                assertThat(obj1).isSameAs(obj2)
            }
        assertTrue(error.message!!.contains("assertThat(obj1).isSameAs(obj2)"))
    }

    @Test
    fun isSameAs_supports_custom_message() {
        val obj1 = Person("Alice", 30)
        val obj2 = Person("Bob", 40)
        val error =
            assertFailsWith<AssertionError> {
                assertThat(obj1).isSameAs(obj2) { "Custom message" }
            }
        assertTrue(error.message!!.contains("Custom message"))
    }

    // isNotSameAs tests (alias)
    @Test
    fun isNotSameAs_succeeds_when_instances_are_different() {
        val obj1 = Person("Alice", 30)
        val obj2 = Person("Alice", 30)
        assertThat(obj1).isNotSameAs(obj2)
    }

    @Test
    fun isNotSameAs_fails_when_instances_are_same() {
        val obj = "hello"
        val error =
            assertFailsWith<AssertionError> {
                assertThat(obj).isNotSameAs(obj)
            }
        assertTrue(error.message!!.contains("assertThat(obj).isNotSameAs(obj)"))
    }

    @Test
    fun isNotSameAs_supports_custom_message() {
        val obj = 42
        val error =
            assertFailsWith<AssertionError> {
                assertThat(obj).isNotSameAs(obj) { "Custom message" }
            }
        assertTrue(error.message!!.contains("Custom message"))
    }

    // isIn tests
    @Test
    fun isIn_succeeds_when_value_is_in_list() {
        assertThat(2).isIn(1, 2, 3)
        assertThat("b").isIn("a", "b", "c")
    }

    @Test
    fun isIn_fails_when_value_is_not_in_list() {
        val error =
            assertFailsWith<AssertionError> {
                assertThat(5).isIn(1, 2, 3)
            }
        val message = error.message!!

        val expectedFormat =
            """
            assertThat(5).isIn(1, 2, 3)
            |
            """.trimIndent()

        assertTrue(
            message.contains(expectedFormat),
            "Should show proper Power Assert format:\nExpected:\n$expectedFormat\nActual:\n$message",
        )
    }

    @Test
    fun isIn_supports_custom_message() {
        val error =
            assertFailsWith<AssertionError> {
                assertThat(10).isIn(1, 2, 3) { "Custom: not in range" }
            }
        val message = error.message!!

        val expectedFormat =
            """
            Custom: not in range
            assertThat(10).isIn(1, 2, 3) { "Custom: not in range" }
            |
            """.trimIndent()

        assertTrue(
            message.contains(expectedFormat),
            "Should show proper custom message format:\nExpected:\n$expectedFormat\nActual:\n$message",
        )
    }

    // isNotIn tests
    @Test
    fun isNotIn_succeeds_when_value_is_not_in_list() {
        assertThat(5).isNotIn(1, 2, 3)
        assertThat("d").isNotIn("a", "b", "c")
    }

    @Test
    fun isNotIn_fails_when_value_is_in_list() {
        val error =
            assertFailsWith<AssertionError> {
                assertThat(2).isNotIn(1, 2, 3)
            }
        val message = error.message!!

        val expectedFormat =
            """
            assertThat(2).isNotIn(1, 2, 3)
            |
            """.trimIndent()

        assertTrue(
            message.contains(expectedFormat),
            "Should show proper Power Assert format:\nExpected:\n$expectedFormat\nActual:\n$message",
        )
    }

    @Test
    fun isNotIn_supports_custom_message() {
        val error =
            assertFailsWith<AssertionError> {
                assertThat(1).isNotIn(1, 2, 3) { "Custom: should not be in list" }
            }
        val message = error.message!!

        val expectedFormat =
            """
            Custom: should not be in list
            assertThat(1).isNotIn(1, 2, 3) { "Custom: should not be in list" }
            |
            """.trimIndent()

        assertTrue(
            message.contains(expectedFormat),
            "Should show proper custom message format:\nExpected:\n$expectedFormat\nActual:\n$message",
        )
    }

    // hasToString tests
    @Test
    fun hasToString_succeeds_when_toString_matches() {
        assertThat(42).hasToString("42")
        assertThat(Person("Alice", 30)).hasToString("Person(name=Alice, age=30)")
    }

    @Test
    fun hasToString_fails_when_toString_does_not_match() {
        val error =
            assertFailsWith<AssertionError> {
                assertThat(42).hasToString("100")
            }
        val message = error.message!!

        val expectedFormat =
            """
            assertThat(42).hasToString("100")
            |
            """.trimIndent()

        assertTrue(
            message.contains(expectedFormat),
            "Should show proper Power Assert format:\nExpected:\n$expectedFormat\nActual:\n$message",
        )
    }

    @Test
    fun hasToString_supports_custom_message() {
        val error =
            assertFailsWith<AssertionError> {
                assertThat(5).hasToString("10") { "Custom: toString mismatch" }
            }
        val message = error.message!!

        val expectedFormat =
            """
            Custom: toString mismatch
            assertThat(5).hasToString("10") { "Custom: toString mismatch" }
            |
            """.trimIndent()

        assertTrue(
            message.contains(expectedFormat),
            "Should show proper custom message format:\nExpected:\n$expectedFormat\nActual:\n$message",
        )
    }

    // toStringFun tests
    @Test
    fun toStringFun_returns_assert_on_toString_result() {
        val result: Assert<String> = assertThat(42).toStringFun()
        result.isEqualTo("42")
    }

    @Test
    fun toStringFun_allows_chaining() {
        assertThat(Person("Alice", 30)).toStringFun().isEqualTo("Person(name=Alice, age=30)")
    }

    @Test
    fun toStringFun_shows_power_assert_in_chain() {
        val person = Person("Alice", 30)
        val error =
            assertFailsWith<AssertionError> {
                assertThat(person).toStringFun().isEqualTo("wrong")
            }
        val message = error.message!!
        assertTrue(message.contains("assertThat(person).toStringFun().isEqualTo(\"wrong\")"))
    }

    // hasHashCode tests
    @Test
    fun hasHashCode_succeeds_when_hashCode_matches() {
        val value = "hello"
        assertThat(value).hasHashCode(value.hashCode())
    }

    @Test
    fun hasHashCode_fails_when_hashCode_does_not_match() {
        val error =
            assertFailsWith<AssertionError> {
                assertThat("hello").hasHashCode(12345)
            }
        val message = error.message!!

        val expectedFormat =
            """
            assertThat("hello").hasHashCode(12345)
            |
            """.trimIndent()

        assertTrue(
            message.contains(expectedFormat),
            "Should show proper Power Assert format:\nExpected:\n$expectedFormat\nActual:\n$message",
        )
    }

    @Test
    fun hasHashCode_supports_custom_message() {
        val error =
            assertFailsWith<AssertionError> {
                assertThat(42).hasHashCode(999) { "Custom: hashCode mismatch" }
            }
        val message = error.message!!

        val expectedFormat =
            """
            Custom: hashCode mismatch
            assertThat(42).hasHashCode(999) { "Custom: hashCode mismatch" }
            |
            """.trimIndent()

        assertTrue(
            message.contains(expectedFormat),
            "Should show proper custom message format:\nExpected:\n$expectedFormat\nActual:\n$message",
        )
    }

    // hashCodeFun tests
    @Test
    fun hashCodeFun_returns_assert_on_hashCode_result() {
        val value = "hello"
        val result: Assert<Int> = assertThat(value).hashCodeFun()
        result.isEqualTo(value.hashCode())
    }

    @Test
    fun hashCodeFun_allows_chaining() {
        val value = 42
        assertThat(value).hashCodeFun().isEqualTo(value.hashCode())
    }

    @Test
    fun hashCodeFun_shows_power_assert_in_chain() {
        val value = "test"
        val error =
            assertFailsWith<AssertionError> {
                assertThat(value).hashCodeFun().isEqualTo(12345)
            }
        val message = error.message!!
        assertTrue(message.contains("assertThat(value).hashCodeFun().isEqualTo(12345)"))
    }

    // isInstanceOf tests
    @Test
    fun isInstanceOf_succeeds_when_type_matches() {
        val result: Assert<String> = assertThat<Any>("hello").isInstanceOf<String>()
        result.isEqualTo("hello")
    }

    @Test
    fun isInstanceOf_fails_when_type_does_not_match() {
        val error =
            assertFailsWith<AssertionError> {
                assertThat<Any>(42).isInstanceOf<String>()
            }
        val message = error.message!!

        val expectedFormat =
            """
            assertThat<Any>(42).isInstanceOf<String>()
            |
            """.trimIndent()

        assertTrue(
            message.contains(expectedFormat),
            "Should show proper Power Assert format:\nExpected:\n$expectedFormat\nActual:\n$message",
        )
    }

    @Test
    fun isInstanceOf_supports_custom_message() {
        val error =
            assertFailsWith<AssertionError> {
                assertThat<Any>(42).isInstanceOf<String>() { "Custom: type mismatch" }
            }
        val message = error.message!!

        val expectedFormat =
            """
            Custom: type mismatch
            assertThat<Any>(42).isInstanceOf<String>() { "Custom: type mismatch" }
            |
            """.trimIndent()

        assertTrue(
            message.contains(expectedFormat),
            "Should show proper custom message format:\nExpected:\n$expectedFormat\nActual:\n$message",
        )
    }

    // isNotInstanceOf tests
    @Test
    fun isNotInstanceOf_succeeds_when_type_does_not_match() {
        assertThat<Any>(42).isNotInstanceOf<String>()
    }

    @Test
    fun isNotInstanceOf_fails_when_type_matches() {
        val error =
            assertFailsWith<AssertionError> {
                assertThat<Any>("hello").isNotInstanceOf<String>()
            }
        val message = error.message!!

        val expectedFormat =
            """
            assertThat<Any>("hello").isNotInstanceOf<String>()
            |
            """.trimIndent()

        assertTrue(
            message.contains(expectedFormat),
            "Should show proper Power Assert format:\nExpected:\n$expectedFormat\nActual:\n$message",
        )
    }

    @Test
    fun isNotInstanceOf_supports_custom_message() {
        val error =
            assertFailsWith<AssertionError> {
                assertThat<Any>("hello").isNotInstanceOf<String>() { "Custom: should not be String" }
            }
        val message = error.message!!

        val expectedFormat =
            """
            Custom: should not be String
            assertThat<Any>("hello").isNotInstanceOf<String>() { "Custom: should not be String" }
            |
            """.trimIndent()

        assertTrue(
            message.contains(expectedFormat),
            "Should show proper custom message format:\nExpected:\n$expectedFormat\nActual:\n$message",
        )
    }
}
