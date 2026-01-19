# power-assertk

[assertk](https://github.com/willowtreeapps/assertk)
ライクな流暢なAPI構文と、[Kotlin Power Assert](https://kotlinlang.org/docs/power-assert.html)
のリッチな失敗メッセージ表示を組み合わせたKotlin Multiplatformアサーションライブラリです。

[English README](README.md)

## 特徴

- **assertk互換のFluent API** - `assertThat(value).isEqualTo(expected)` 形式の直感的な構文
- **Power Assertによる詳細な失敗メッセージ** - 式の各部分の評価結果を視覚的に表示
- **Kotlin Multiplatform対応** - JVM、JS、Native（macOS、Linux、Windows、iOS）をサポート

## Before & After

### 従来のアサーションライブラリ

```kotlin
val person = Person("Alice", 10)
assertThat(person.name).startsWith("B")
// 出力:
// expected to start with:<"B"> but was:<"Alice">
```

### power-assertk

```kotlin
val person = Person("Alice", 10)
assertThat(person.name).startsWith("B")
// 出力:
// assertThat(person.name).startsWith("B")
//           |      |
//           |      Alice
//           Person(name=Alice, age=10)
// expected to start with:<"B"> but was:<"Alice">
```

長いプロパティチェーンでも、どの段階で期待と異なるかが一目瞭然です。

## インストール

### 1. 依存関係の追加

```kotlin
// build.gradle.kts
dependencies {
  testImplementation("io.github.xxx:power-assertk:1.0.0")  // TODO: 公開後に更新
}
```

### 2. Power Assertプラグインの設定

power-assertkの機能をフルに活用するには、Kotlin Power Assertコンパイラプラグインの設定が必要です。

```kotlin
// build.gradle.kts
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

plugins {
  kotlin("jvm") version "2.3.0"  // または kotlin("multiplatform")
  kotlin("plugin.power-assert") version "2.3.0"
}

@OptIn(ExperimentalKotlinGradlePluginApi::class)
powerAssert {
  functions = listOf(
    // 基本アサーション
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
    // その他必要なアサーションを追加
  )
  includedSourceSets = listOf("test", "commonTest", "jvmTest", "jsTest")
}
```

## 使用方法

```kotlin
import com.litols.power.assertk.assertThat
import com.litols.power.assertk.assertions.*

// 基本的な等価性チェック
assertThat(actual).isEqualTo(expected)
assertThat(value).isNotNull()

// 文字列アサーション
assertThat(name).startsWith("Hello")
assertThat(message).contains("error")
assertThat(text).hasLength(10)

// 数値アサーション
assertThat(count).isGreaterThan(0)
assertThat(score).isBetween(0, 100)

// コレクションアサーション
assertThat(list).hasSize(3)
assertThat(items).containsExactly("a", "b", "c")
assertThat(map).containsAll("key1" to "value1", "key2" to "value2")

// Boolean
assertThat(flag).isTrue()

// Nullable
assertThat(nullableValue).isNotNull().isEqualTo("expected")

// 型チェック
assertThat(obj).isInstanceOf<String>()
```

## 利用可能なアサーション

### Any（全ての型）

- `isEqualTo()` / `isNotEqualTo()`
- `isNull()` / `isNotNull()`
- `isSameInstanceAs()` / `isNotSameInstanceAs()`
- `isIn()` / `isNotIn()`
- `hasToString()` / `hasHashCode()`
- `isInstanceOf<T>()` / `isNotInstanceOf<T>()`

### Boolean

- `isTrue()` / `isFalse()`

### Comparable（数値など）

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

### JVM専用

- **File**: `exists()`, `isFile()`, `isDirectory()`, `hasName()`, `hasText()` など
- **Path**: `isRegularFile()`, `isSymbolicLink()`, `isReadable()`, `isWritable()` など
- **Optional**: `hasValue()`
- **InputStream**: `hasSameContentAs()`

## サポートプラットフォーム

| プラットフォーム                         | 状態   |
|----------------------------------|------|
| JVM (Java 11+)                   | サポート |
| JS (Browser, Node.js)            | サポート |
| macOS (x64, arm64)               | サポート |
| Linux (x64)                      | サポート |
| Windows (x64)                    | サポート |
| iOS (x64, arm64, simulatorArm64) | サポート |

## ビルド

```bash
# 全ターゲットのビルド
./gradlew build

# 全テストの実行
./gradlew allTests

# JVMテストのみ
./gradlew jvmTest

# コードスタイルチェック
./gradlew ktlintCheck
./gradlew detekt
```

## 設計思想

power-assertkは、Kotlin Power Assertコンパイラプラグインの要件に合わせて設計されています。

Power Assertプラグインが変換対象とできる関数は、**最後のパラメータが `String` または `() -> String` 型**である必要があります。そのため、各アサーションメソッドはオプショナルな
`message` パラメータを最後に持っています：

```kotlin
fun <T> Assert<T>.isEqualTo(expected: T, message: (() -> String)? = null)
```

これにより、Power Assertプラグインが呼び出し箇所で式を解析し、失敗時にダイアグラム形式のメッセージを自動生成できます。

## 関連リンク

- [Kotlin Power Assert Documentation](https://kotlinlang.org/docs/power-assert.html)
- [assertk GitHub Repository](https://github.com/willowtreeapps/assertk)

## ライセンス

MIT
