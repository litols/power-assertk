# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

**power-assertk** is a Kotlin Multiplatform assertion library that combines assertk's fluent API syntax with Kotlin Power Assert's rich
failure message display. The library provides assertk-compatible APIs with message parameters that enable the Kotlin Power Assert compiler
plugin (configured by the user) to show intermediate expression values in failure messages.

## Build Commands

```bash
# Build all targets
./gradlew build

# Run all tests
./gradlew allTests

# Run JVM tests only
./gradlew jvmTest

# Run JS tests only
./gradlew jsTest

# Run Native tests (macOS)
./gradlew macosArm64Test   # Apple Silicon
./gradlew macosX64Test     # Intel Mac

# Run a single test class (JVM)
./gradlew jvmTest --tests "com.litols.power.assertk.AssertTest"

# Run detekt static analysis
./gradlew detekt

# Run ktlint code style check
./gradlew ktlintCheck

# Auto-format code with ktlint
./gradlew ktlintFormat

# Clean build
./gradlew clean build

# Publish to local Maven
./gradlew publishToMavenLocal
```

## Architecture

### Source Set Structure (Kotlin Multiplatform)

```
power-assertk/
├── src/
│   ├── commonMain/kotlin/     # Shared API (Assert<T>, assertion methods)
│   ├── commonTest/kotlin/     # Shared tests
│   ├── jvmMain/kotlin/        # JVM-specific implementations
│   ├── jvmTest/kotlin/        # JVM-specific tests
│   ├── jsMain/kotlin/         # JS-specific implementations
│   ├── jsTest/kotlin/         # JS-specific tests
│   ├── nativeMain/kotlin/     # Native-specific implementations
│   └── nativeTest/kotlin/     # Native-specific tests
```

### Supported Targets

- **JVM**: Java 11+
- **JS**: Browser, Node.js
- **Native**: macosX64, macosArm64, linuxX64, mingwX64, iosX64, iosArm64, iosSimulatorArm64

### Power Assert Integration Design

The Kotlin Power Assert compiler plugin requires target functions to have **the last parameter be `String` or `() -> String`**. This library
provides assertion methods with an optional message parameter to enable Power Assert integration.

**Important**: The Power Assert compiler plugin must be configured by the **user of this library**, not by the library itself. The plugin
performs compile-time transformation, so it only works when applied to the user's compilation.

### Library Implementation

The library provides assertion methods with `message: (() -> String)? = null` as the last parameter:

```kotlin
// commonMain/kotlin/com.litols.power.assertk/Assert.kt
package com.litols.power.assertk

class Assert<T>(val actual: T) {
  fun isEqualTo(expected: T, message: (() -> String)? = null) {
    if (actual != expected) {
      throw AssertionError(message?.invoke() ?: "expected:<$expected> but was:<$actual>")
    }
  }

  fun startsWith(prefix: String, message: (() -> String)? = null) {
    val str = actual as? String ?: throw AssertionError("Expected String")
    if (!str.startsWith(prefix)) {
      throw AssertionError(message?.invoke() ?: "expected to start with:<\"$prefix\"> but was:<\"$str\">")
    }
  }
}

fun <T> assertThat(actual: T): Assert<T> = Assert(actual)
```

### User-Side Configuration

Users of this library need to configure the Power Assert plugin in their project:

```kotlin
// User's build.gradle.kts
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

plugins {
  kotlin("multiplatform") version "2.3.0"  // or kotlin("jvm")
  kotlin("plugin.power-assert") version "2.3.0"
}

dependencies {
  // Add power-assertk library
  commonTestImplementation("io.github.xxx:power-assertk:1.0.0")
}

@OptIn(ExperimentalKotlinGradlePluginApi::class)
powerAssert {
  functions = listOf(
    "com.litols.power.assertk.Assert.isEqualTo",
    "com.litols.power.assertk.Assert.startsWith",
    "com.litols.power.assertk.Assert.isNotNull",
    // ... other assertion methods
  )
  includedSourceSets = listOf("commonTest", "jvmTest", "jsTest")
}
```

### Library's Own Test Configuration

For testing the library itself, the Power Assert plugin is configured in this project's build file:

```kotlin
// This project's build.gradle.kts
@OptIn(ExperimentalKotlinGradlePluginApi::class)
powerAssert {
  functions = listOf(
    "com.litols.power.assertk.Assert.isEqualTo",
    "com.litols.power.assertk.Assert.startsWith",
    // ...
  )
  includedSourceSets = listOf("commonTest", "jvmTest", "jsTest", "nativeTest")
}
```

### Usage Example

```kotlin
// In user's test code
val person = Person("Alice", 10)
assertThat(person.name).startsWith("B")

// Output (when Power Assert plugin is configured):
// assertThat(person.name).startsWith("B")
//           |      |
//           |      "Alice"
//           Person(name=Alice, age=10)
// expected to start with:<"B"> but was:<"Alice">
```

## Testing Best Practices

### Power Assert Diagram Verification

Tests should verify the complete Power Assert diagram format, including the position of lines and intermediate values. This ensures that the
Power Assert plugin is working correctly and displaying helpful debug information.

**Recommended Test Pattern:**

```kotlin
@Test
fun assertion_shows_power_assert_diagram() {
  data class Person(val name: String, val age: Int)

  val person = Person("Alice", 30)

  val error = assertFailsWith<AssertionError> {
    assertThat(person.name).isEqualTo("Bob")
  }
  val message = error.message!!

  // Verify the complete Power Assert diagram format
  val expectedFormat = """
        assertThat(person.name).isEqualTo("Bob")
        |          |      |
        |          |      Alice
        |          Person(name=Alice, age=30)
        """.trimIndent()

  assertTrue(
    message.contains(expectedFormat),
    "Should show proper Power Assert diagram:\nExpected:\n$expectedFormat\nActual:\n$message"
  )
}
```

**What to verify:**

1. The assertion expression (e.g., `assertThat(person.name).isEqualTo("Bob")`)
2. The vertical lines (`|`) showing the evaluation tree
3. Intermediate values (e.g., `Alice`, `Person(name=Alice, age=30)`)
4. Proper alignment of lines and values

**Examples from CharSequenceTest.kt:**

```kotlin
// Property chain verification
val expectedFormat = """
    assertThat(msg.text).contains("xyz")
    |          |   |
    |          |   hello world
    |          Message(text=hello world)
    """.trimIndent()

// Variable expansion verification
val expectedFormat = """
    assertThat(text).startsWith(prefix)
    |          |                |
    |          |                world
    |          hello world
    """.trimIndent()

// Nested property verification
val expectedFormat = """
    assertThat(user.name).hasLength(10)
    |          |    |
    |          |    Alice
    |          User(name=Alice)
    """.trimIndent()
```

**Note:** The exact spacing and line positions depend on the expression structure. Always run the test first to see the actual output, then
update the expected format accordingly.

### Test Structure

Each assertion method should have at least three types of tests:

1. **Success case**: Verify the assertion passes when the condition is met
2. **Power Assert diagram verification**: Verify the complete diagram format on failure
3. **Custom message support**: Verify custom messages work correctly

```kotlin
// 1. Success case
@Test
fun isEmpty_succeeds_when_empty() {
  assertThat("").isEmpty()
}

// 2. Power Assert diagram verification
@Test
fun isEmpty_shows_power_assert_diagram() {
  val text = "hello"
  val error = assertFailsWith<AssertionError> {
    assertThat(text).isEmpty()
  }
  val expectedFormat = """
        assertThat(text).isEmpty()
        |          |
        |          hello
        """.trimIndent()
  assertTrue(error.message!!.contains(expectedFormat))
}

// 3. Custom message support
@Test
fun isEmpty_supports_custom_message() {
  val error = assertFailsWith<AssertionError> {
    assertThat("hello").isEmpty { "Custom: should be empty" }
  }
  assertTrue(error.message!!.contains("Custom: should be empty"))
}
```

## Current State

This is an early-stage project with initial Gradle setup. The `lib` module contains placeholder code. Development should follow the phases
outlined in `docs/CONCEPT.md`.

## Key Dependencies

- Kotlin 2.3.0 (Multiplatform)
- Kotlin Power Assert compiler plugin (user-side configuration)
