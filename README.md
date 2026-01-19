# power-assertk

A Kotlin Multiplatform assertion library that combines [assertk](https://github.com/willowtreeapps/assertk)-like fluent API syntax
with [Kotlin Power Assert](https://kotlinlang.org/docs/power-assert.html)'s rich failure message display.

[日本語版 README](README.ja.md)

## Features

- **assertk-compatible Fluent API** - Intuitive `assertThat(value).isEqualTo(expected)` syntax
- **Detailed failure messages with Power Assert** - Visual display of each expression's evaluation result
- **Kotlin Multiplatform support** - JVM, JS, and Native (macOS, Linux, Windows, iOS)

## Before & After

### Traditional assertion libraries

```kotlin
val person = Person("Alice", 10)
assertThat(person.name).startsWith("B")
// Output:
// expected to start with:<"B"> but was:<"Alice">
```

### power-assertk

```kotlin
val person = Person("Alice", 10)
assertThat(person.name).startsWith("B")
// Output:
// assertThat(person.name).startsWith("B")
//           |      |
//           |      Alice
//           Person(name=Alice, age=10)
// expected to start with:<"B"> but was:<"Alice">
```

Even with long property chains, you can immediately see where the actual value differs from expectations.

## Installation

### 1. Add dependency

```kotlin
// build.gradle.kts
dependencies {
  testImplementation("io.github.xxx:power-assertk:1.0.0")  // TODO: Update after publishing
}
```

### 2. Configure Power Assert plugin

To fully utilize power-assertk's features, you need to configure the Kotlin Power Assert compiler plugin.

```kotlin
// build.gradle.kts
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

plugins {
  kotlin("jvm") version "2.3.0"  // or kotlin("multiplatform")
  kotlin("plugin.power-assert") version "2.3.0"
}

@OptIn(ExperimentalKotlinGradlePluginApi::class)
powerAssert {
  functions = listOf(
    // Basic assertions
    "com.litols.power.assertk.assertions.isEqualTo",
    "com.litols.power.assertk.assertions.isNotEqualTo",
    "com.litols.power.assertk.assertions.isNull",
    "com.litols.power.assertk.assertions.isNotNull",
    // Boolean
    "com.litols.power.assertk.assertions.isTrue",
    "com.litols.power.assertk.assertions.isFalse",
    // Comparable
    "com.litols.power.assertk.assertions.isGreaterThan",
    "com.litols.power.assertk.assertions.isLessThan",
    "com.litols.power.assertk.assertions.isBetween",
    // CharSequence/String
    "com.litols.power.assertk.assertions.isEmpty",
    "com.litols.power.assertk.assertions.isNotEmpty",
    "com.litols.power.assertk.assertions.hasLength",
    "com.litols.power.assertk.assertions.contains",
    "com.litols.power.assertk.assertions.startsWith",
    "com.litols.power.assertk.assertions.endsWith",
    // Collection/Iterable
    "com.litols.power.assertk.assertions.hasSize",
    "com.litols.power.assertk.assertions.containsAll",
    "com.litols.power.assertk.assertions.containsExactly",
    // Add other assertions as needed
  )
  includedSourceSets = listOf("test", "commonTest", "jvmTest", "jsTest")
}
```

## Usage

```kotlin
import com.litols.power.assertk.assertThat
import com.litols.power.assertk.assertions.*

// Basic equality checks
assertThat(actual).isEqualTo(expected)
assertThat(value).isNotNull()

// String assertions
assertThat(name).startsWith("Hello")
assertThat(message).contains("error")
assertThat(text).hasLength(10)

// Numeric assertions
assertThat(count).isGreaterThan(0)
assertThat(score).isBetween(0, 100)

// Collection assertions
assertThat(list).hasSize(3)
assertThat(items).containsExactly("a", "b", "c")
assertThat(map).containsAll("key1" to "value1", "key2" to "value2")

// Boolean
assertThat(flag).isTrue()

// Nullable
assertThat(nullableValue).isNotNull().isEqualTo("expected")

// Type checking
assertThat(obj).isInstanceOf<String>()
```

## Available Assertions

### Any (all types)

- `isEqualTo()` / `isNotEqualTo()`
- `isNull()` / `isNotNull()`
- `isSameInstanceAs()` / `isNotSameInstanceAs()`
- `isIn()` / `isNotIn()`
- `hasToString()` / `hasHashCode()`
- `isInstanceOf<T>()` / `isNotInstanceOf<T>()`

### Boolean

- `isTrue()` / `isFalse()`

### Comparable (numbers, etc.)

- `isGreaterThan()` / `isLessThan()`
- `isGreaterThanOrEqualTo()` / `isLessThanOrEqualTo()`
- `isBetween()` / `isStrictlyBetween()`
- `isCloseTo()`

### Number

- `isZero()` / `isNotZero()`
- `isPositive()` / `isNegative()`

### CharSequence / String

- `isEmpty()` / `isNotEmpty()` / `isNullOrEmpty()`
- `hasLength()` / `hasSameLengthAs()` / `hasLineCount()`
- `contains()` / `doesNotContain()`
- `startsWith()` / `endsWith()`
- `matches()` / `containsMatch()`

### Collection / Iterable / Sequence

- `isEmpty()` / `isNotEmpty()`
- `hasSize()` / `hasSameSizeAs()`
- `containsAll()` / `containsAtLeast()` / `containsOnly()`
- `containsExactly()` / `containsExactlyInAnyOrder()`
- `containsNone()`

### List

- `containsSubList()`
- `startsWith()` / `endsWith()`

### Map

- `containsAll()` / `containsAtLeast()` / `containsOnly()`
- `containsNone()` / `doesNotContainKey()`

### Throwable

- `hasMessage()` / `messageContains()`
- `hasCause()` / `hasNoCause()` / `hasRootCause()`

### Result

- `isSuccess()` / `isFailure()`

### JVM-only

- **File**: `exists()`, `isFile()`, `isDirectory()`, `hasName()`, `hasText()`, etc.
- **Path**: `isRegularFile()`, `isSymbolicLink()`, `isReadable()`, `isWritable()`, etc.
- **Optional**: `hasValue()`
- **InputStream**: `hasSameContentAs()`

## Supported Platforms

| Platform                         | Status    |
|----------------------------------|-----------|
| JVM (Java 11+)                   | Supported |
| JS (Browser, Node.js)            | Supported |
| macOS (x64, arm64)               | Supported |
| Linux (x64)                      | Supported |
| Windows (x64)                    | Supported |
| iOS (x64, arm64, simulatorArm64) | Supported |

## Building

```bash
# Build all targets
./gradlew build

# Run all tests
./gradlew allTests

# JVM tests only
./gradlew jvmTest

# Code style checks
./gradlew ktlintCheck
./gradlew detekt
```

## Design Philosophy

power-assertk is designed to meet the requirements of the Kotlin Power Assert compiler plugin.

Functions that the Power Assert plugin can transform must have **the last parameter be `String` or `() -> String` type**. Therefore, each
assertion method has an optional `message` parameter at the end:

```kotlin
fun <T> Assert<T>.isEqualTo(expected: T, message: (() -> String)? = null)
```

This allows the Power Assert plugin to analyze expressions at call sites and automatically generate diagram-style messages on failure.

## Related Links

- [Kotlin Power Assert Documentation](https://kotlinlang.org/docs/power-assert.html)
- [assertk GitHub Repository](https://github.com/willowtreeapps/assertk)

## License

MIT
