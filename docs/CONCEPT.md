# power-assertk コンセプトドキュメント

## 概要

**power-assertk**は、[assertk](https://github.com/assertk-org/assertk)の流暢なAPI構文と、[Kotlin Power Assert](https://kotlinlang.org/docs/power-assert.html)のリッチな失敗メッセージ表示を組み合わせたKotlin用アサーションライブラリです。

## 実現イメージ

### Before: 従来のassertk

```kotlin
val person = Person("Alice", 10)

assertThat(person.name).startsWith("B")
// 出力:
// expected to start with:<"B"> but was:<"Alice">
```

### After: power-assertk

```kotlin
val person = Person("Alice", 10)

assertThat(person.name).startsWith("B")
// 出力:
// assertThat(person.name).startsWith("B")
//           |      |
//           |      "Alice"
//           Person(name=Alice, age=10)
// expected to start with:<"B"> but was:<"Alice">
```

### 複合条件の例

```kotlin
val person = Person("Alice", 10)

assertThat(person).all {
    prop(Person::name).hasLength(10)
    prop(Person::age).isGreaterThan(20)
}
// 出力:
// assertThat(person).all { ... }
//           |
//           Person(name=Alice, age=10)
// The following assertions failed (2 failures):
//   - prop(Person::name).hasLength(10)
//           |              |
//           "Alice"        expected length:<10> but was:<5>
//   - prop(Person::age).isGreaterThan(20)
//           |              |
//           10             expected to be greater than:<20> but was:<10>
```

## 設計方針

### 1. assertk互換のAPI

- **パッケージの置き換えで移行可能**: `import assertk.assertThat` → `import powerassertk.assertThat`
- **同じシグネチャ**: `assertThat()`, `assertAll()`, `assertFailure`などの主要関数を同一シグネチャで提供
- **同じアサーションメソッド**: `isEqualTo()`, `isNotNull()`, `startsWith()`, `contains()`などを完全互換で提供

### 2. Kotlin Power Assertコンパイラプラグインの活用

- JetBrainsの公式Power Assertコンパイラプラグインをバックエンドとして利用
- コンパイル時に式ツリーを解析し、中間値をキャプチャするコードを自動挿入
- ランタイムへのパフォーマンス影響を最小化

### 3. リッチなエラー出力

```
assertThat(hello.length == world.substring(1, 4).length)
           |     |        |     |               |
           |     5        |     "orl"           3
           "Hello"        "world!"
```

- 各部分式の評価結果を縦方向に整列表示
- 式の位置に対応する中間値を視覚的にマッピング
- 最終的なアサーション失敗メッセージもassertk互換形式で出力

## アーキテクチャ

```
┌─────────────────────────────────────────────────────────────────┐
│                        power-assertk                            │
├─────────────────────────────────────────────────────────────────┤
│  ┌─────────────────────────────────────────────────────────┐    │
│  │                 power-assertk-api                       │    │
│  │  - assertThat(), Assert<T>, アサーションメソッド群      │    │
│  │  - assertk互換のpublic API                              │    │
│  └─────────────────────────────────────────────────────────┘    │
│                              ↓                                  │
│  ┌─────────────────────────────────────────────────────────┐    │
│  │              power-assertk-runtime                      │    │
│  │  - 式ツリー情報の保持                                   │    │
│  │  - 中間値のキャプチャ・フォーマット                     │    │
│  │  - エラーメッセージの組み立て                           │    │
│  └─────────────────────────────────────────────────────────┘    │
│                              ↑                                  │
│  ┌─────────────────────────────────────────────────────────┐    │
│  │        Kotlin Power Assert Compiler Plugin              │    │
│  │  - コンパイル時の式解析                                 │    │
│  │  - 中間値キャプチャコードの自動挿入                     │    │
│  └─────────────────────────────────────────────────────────┘    │
└─────────────────────────────────────────────────────────────────┘
```

## やるべきこと

### Phase 1: 基盤構築

1. **プロジェクト構造のセットアップ**
   - マルチモジュールGradleプロジェクト構成
   - Kotlin Multiplatform対応の検討（JVM優先）

2. **Power Assertプラグイン統合調査**
   - Kotlin Power Assertプラグインのカスタム関数登録方法の調査
   - `powerAssert { functions = listOf("powerassertk.assertThat", ...) }` の実現可能性確認

3. **コアAPI設計**
   - `Assert<T>`クラスの実装
   - `assertThat()`エントリーポイントの実装
   - Power Assertが解釈可能な形式でのアサーション定義

### Phase 2: 基本アサーション実装

4. **共通アサーションメソッド**
   - `isEqualTo()`, `isNotEqualTo()`
   - `isNull()`, `isNotNull()`
   - `isTrue()`, `isFalse()`
   - `isSameInstanceAs()`, `isNotSameInstanceAs()`

5. **文字列アサーション**
   - `startsWith()`, `endsWith()`, `contains()`
   - `matches()`, `hasLength()`
   - `isEmpty()`, `isNotEmpty()`, `isBlank()`, `isNotBlank()`

6. **数値アサーション**
   - `isGreaterThan()`, `isLessThan()`
   - `isGreaterThanOrEqualTo()`, `isLessThanOrEqualTo()`
   - `isBetween()`, `isCloseTo()`

### Phase 3: コレクション・高度な機能

7. **コレクションアサーション**
   - `contains()`, `containsExactly()`, `containsOnly()`
   - `containsAtLeast()`, `containsNone()`
   - `hasSize()`, `isEmpty()`, `isNotEmpty()`

8. **プロパティ抽出**
   - `prop()` - プロパティ参照によるアサーション
   - `transform()` - 値変換によるアサーション
   - `index()`, `key()` - コレクション/マップ要素アクセス
   - `extracting()` - コレクションからのプロパティ抽出

9. **例外アサーション**
   - `assertFailure {}` - 例外発生の検証
   - `hasMessage()`, `hasCause()`

10. **複合アサーション**
    - `all {}` - 全アサーション実行（ソフトアサーション）
    - `assertAll()` - 複数独立アサーションの同時実行

### Phase 4: メッセージフォーマット

11. **Power Assert形式の出力生成**
    - 式ツリーの視覚化ロジック
    - 中間値の位置合わせアルゴリズム
    - assertkスタイルのエラーメッセージとの統合

12. **カスタムメッセージ対応**
    - `assertThat(value, name = "customName")`
    - ラムダによるカスタムメッセージ

### Phase 5: 品質・互換性

13. **テスト整備**
    - 各アサーションメソッドの単体テスト
    - Power Assert出力形式の検証テスト
    - assertkからの移行テスト

14. **ドキュメント**
    - API リファレンス（KDoc）
    - 移行ガイド（assertk → power-assertk）
    - 利用例・サンプルコード

## 提供する体験

### 開発者体験の向上

1. **直感的なAPIはそのまま**
   ```kotlin
   assertThat(result).isNotNull().isEqualTo(expected)
   ```
   assertkユーザーは学習コストなしで利用開始可能

2. **失敗時のデバッグ効率化**
   ```
   assertThat(user.profile.settings.theme).isEqualTo("dark")
             |    |       |        |
             |    |       |        "light"
             |    |       Settings(theme=light, ...)
             |    Profile(settings=..., ...)
             User(name=Alice, profile=...)
   ```
   長いプロパティチェーンでも、どの段階で期待と異なるか一目瞭然

3. **ゼロコンフィグ（理想）**
   ```kotlin
   // build.gradle.kts
   plugins {
       kotlin("jvm") version "2.2.0"
       kotlin("plugin.power-assert") version "2.2.0"
   }

   dependencies {
       testImplementation("io.github.xxx:power-assertk:1.0.0")
   }

   powerAssert {
       functions = listOf("powerassertk.assertThat")
   }
   ```

### 移行パス

```kotlin
// Before
import assertk.assertThat
import assertk.assertions.*

// After（パッケージ置換のみ）
import powerassertk.assertThat
import powerassertk.assertions.*
```

IDE の一括置換で即座に移行可能。

## 技術的課題・検討事項

### 1. Power Assertプラグインとの統合方法

Kotlin Power Assertプラグインは `kotlin.assert`、`kotlin.require` などの標準関数を対象としている。
カスタムアサーション関数（`assertThat`など）を対象にできるか要調査。

**オプションA**: プラグイン設定でカスタム関数を追加
```kotlin
powerAssert {
    functions = listOf(
        "powerassertk.assertThat",
        "powerassertk.assertions.isEqualTo"
    )
}
```

**オプションB**: 内部で`assert()`を呼び出す形式に変換
```kotlin
fun <T> assertThat(actual: T): Assert<T> {
    // Power Assertがキャプチャできる形式で内部処理
}
```

### 2. 式ツリー情報の取得

Power Assertはコンパイル時に式を解析するが、その情報をランタイムでどう取得するか。
- コンパイラプラグインが生成するコードの構造調査が必要
- カスタムのメッセージフォーマッタを差し込めるか確認

### 3. マルチプラットフォーム対応

assertkはKotlin Multiplatformをサポート。power-assertkも同様に対応すべきか。
- JVMを最優先とし、段階的に対応範囲を拡大

---

## 実装アプローチの調査結果

### Power Assertプラグインの制約

調査の結果、Kotlin Power Assertプラグインには以下の**重要な制約**があることが判明した。

#### 対象関数の条件

Power Assertプラグインが変換対象とできる関数は、以下の条件を満たす必要がある：

```
最後のパラメータが String または () -> String 型であること
```

**対応パターン例：**

```kotlin
// パターンA: オーバーロード形式
fun assert(value: Boolean)
fun assert(value: Boolean, lazyMessage: () -> Any)

// パターンB: デフォルト値付き
fun myAssert(assertion: Boolean, message: String = "")

// パターンC: ラムダ形式
fun check(condition: Boolean, lazyMessage: () -> String)
```

プラグインは最初の形式（メッセージなし）で呼び出された場合、自動的に2番目の形式に変換し、ダイアグラムメッセージを最後のパラメータとして追加する。

#### assertkのFluent APIとの根本的な不整合

assertkのFluent API（チェインAPI）は以下の形式：

```kotlin
assertThat(value).isEqualTo(expected)
//        ↑ ここで Assert<T> を返す
//                 ↑ ここでアサーション実行
```

この形式では：
1. `assertThat()` は `Assert<T>` を返すだけで、アサーション自体は行わない
2. `isEqualTo()` などのアサーションメソッドにはメッセージパラメータがない
3. Power Assertプラグインが要求する「最後のパラメータがString」の条件を満たせない

### 実現可能なアプローチ

調査結果を基に、以下の4つのアプローチを検討した。

---

### アプローチA: Boolean式ラッパー方式（推奨）

**概要**: assertkスタイルのAPIを提供しつつ、内部でBoolean式に変換してPower Assertを活用する。

```kotlin
// ユーザーが書くコード
assertThat(person.name).isEqualTo("Bob")

// 内部で展開されるコード（コンパイラプラグイン or KSP）
powerAssert(person.name == "Bob") {
    "expected:<\"Bob\"> but was:<\"${person.name}\">"
}
```

**実装イメージ:**

```kotlin
// Power Assert対応のベース関数
fun powerAssert(condition: Boolean, lazyMessage: () -> String = { "" }) {
    if (!condition) {
        throw AssertionError(lazyMessage())
    }
}

// assertkスタイルのラッパー（内部でpowerAssertを呼ぶ）
inline fun <T> assertThat(actual: T): PowerAssert<T> = PowerAssert(actual)

class PowerAssert<T>(val actual: T) {
    // 各メソッドが内部でpowerAssertを呼び出す
    inline fun isEqualTo(expected: T) {
        powerAssert(actual == expected) {
            "expected:<$expected> but was:<$actual>"
        }
    }
}
```

**Gradle設定:**

```kotlin
powerAssert {
    functions = listOf("powerassertk.powerAssert")
}
```

**メリット:**
- assertkに近いAPIを維持できる
- Power Assertの恩恵を受けられる

**デメリット:**
- Power Assertのダイアグラム表示は `powerAssert()` の引数部分のみ
- チェイン部分（`.isEqualTo("Bob")`）は表示されない

**期待される出力:**

```
powerAssert(person.name == "Bob")
            |      |
            |      "Alice"
            Person(name=Alice, age=10)
expected:<"Bob"> but was:<"Alice">
```

---

### アプローチB: AssertScope方式

**概要**: Soft Assertionsパターンを採用し、スコープ内でPower Assert対応の`assert`を使用する。

```kotlin
// ユーザーが書くコード
assertThat(person) {
    assert(name == "Bob")
    assert(age > 20)
}

// または
assertScope {
    assert(person.name == "Bob")
    assert(person.age > 20)
}
```

**実装イメージ:**

```kotlin
interface AssertScope {
    fun assert(condition: Boolean, lazyMessage: (() -> String)? = null)
}

class PowerAssertScope : AssertScope {
    private val failures = mutableListOf<Throwable>()

    override fun assert(condition: Boolean, lazyMessage: (() -> String)?) {
        if (!condition) {
            failures.add(AssertionError(lazyMessage?.invoke() ?: "Assertion failed"))
        }
    }

    fun throwIfFailed() {
        if (failures.isNotEmpty()) {
            throw MultipleFailuresError(failures)
        }
    }
}

inline fun <T> assertThat(actual: T, block: AssertScope.(T) -> Unit) {
    val scope = PowerAssertScope()
    scope.block(actual)
    scope.throwIfFailed()
}
```

**Gradle設定:**

```kotlin
powerAssert {
    functions = listOf("powerassertk.AssertScope.assert")
}
```

**メリット:**
- Power Assertのダイアグラムが完全に動作
- 複数アサーションをまとめて実行可能（Soft Assertions）

**デメリット:**
- assertkのFluent APIとは大きく異なる構文
- 移行コストが高い

**期待される出力:**

```
assertThat(person) {
    assert(name == "Bob")
           |    |
           |    false
           "Alice"
    assert(age > 20)
           |   |
           10  false
}
Multiple assertion failures (2 failures)
```

---

### アプローチC: 独自コンパイラプラグイン方式

**概要**: Kotlin Power Assertプラグインを参考に、assertk形式のAPIを直接サポートする独自のコンパイラプラグインを作成する。

**実装概要:**

```
1. K2コンパイラのIR変換フェーズにフック
2. assertThat(...).isEqualTo(...) 形式の呼び出しを検出
3. 各部分式の中間値をキャプチャするコードを挿入
4. 失敗時にダイアグラム形式のメッセージを生成
```

**メリット:**
- assertkと完全互換のAPIを維持
- 最もリッチなPower Assert体験を提供可能

**デメリット:**
- 実装コストが非常に高い
- Kotlinコンパイラのバージョンアップへの追従が必要
- メンテナンス負荷が大きい

---

### アプローチD: KSP（Kotlin Symbol Processing）方式

**概要**: KSPを使用して、assertk形式のコードをPower Assert対応形式に変換するコード生成を行う。

**実装概要:**

```kotlin
// ユーザーが書くコード
@PowerAssertEnabled
fun myTest() {
    assertThat(person.name).isEqualTo("Bob")
}

// KSPが生成するコード
fun myTest() {
    val __pa_actual = person.name
    powerAssert(__pa_actual == "Bob") {
        buildDiagram(
            "assertThat(person.name).isEqualTo(\"Bob\")",
            mapOf(
                "person" to person,
                "person.name" to __pa_actual
            )
        )
    }
}
```

**メリット:**
- コンパイラプラグインより実装が容易
- assertkに近いAPIを維持

**デメリット:**
- アノテーションが必要（または全テストファイルを処理）
- 生成コードのデバッグが困難
- 複雑な式の変換が難しい

---

### 推奨アプローチ

調査結果を踏まえ、以下の段階的アプローチを推奨する。

#### Phase 1: アプローチA（Boolean式ラッパー方式）で開始

最小限の実装コストで、Power Assertの恩恵を受けられる形を実現する。

```kotlin
// API例
assertThat(person.name).isEqualTo("Bob")

// 内部実装
inline fun <T> Assert<T>.isEqualTo(expected: T) {
    powerAssert(actual == expected) {
        "expected:<${show(expected)}> but was:<${show(actual)}>"
    }
}
```

#### Phase 2: アプローチBの要素を追加

Soft Assertions対応として、AssertScope方式も並行して提供する。

```kotlin
// 追加API
assertScope {
    assert(person.name == "Bob")
    assert(person.age > 20)
}

// all {} ブロック内でも使用可能
assertThat(person).all {
    assert(name.length > 3)
    assert(age >= 18)
}
```

#### Phase 3: 将来の拡張（オプション）

ユーザーからのフィードバックを基に、アプローチCまたはDの検討を行う。

---

## assertkの内部実装（参考）

調査で判明したassertkの主要な実装パターン。

### Assert<T>クラスの構造

```kotlin
@AssertkDsl
sealed class Assert<out T>(
    val name: String?,
    internal val context: AssertingContext
) {
    abstract fun <R> assertThat(actual: R, name: String? = this.name): Assert<R>
}

// 成功状態
internal class ValueAssert<T>(
    val value: T,
    name: String?,
    context: AssertingContext
) : Assert<T>(name, context)

// 失敗状態
internal class FailingAssert<T>(
    val error: Throwable,
    name: String?,
    context: AssertingContext
) : Assert<T>(name, context)
```

### アサーションメソッドの実装パターン

```kotlin
// given()ブロックを使用した実装
fun <T> Assert<T>.isEqualTo(expected: T) = given { actual ->
    if (actual == expected) return
    expected(show(expected), actual)
}

fun <T> Assert<T?>.isNotNull(): Assert<T> = transform { actual ->
    actual ?: expected("to not be null")
}
```

### 失敗メッセージ生成

```kotlin
// expected()関数
fun <T> Assert<T>.expected(message: String, expected: Any? = NONE, actual: Any? = NONE): Nothing

// show()関数 - 値の視覚的強調
fun show(value: Any?, wrap: String = "<>"): String {
    return wrap[0] + display(value) + wrap[1]
}

// display()関数 - 型に応じたフォーマット
fun display(value: Any?): String = when (value) {
    null -> "null"
    is String -> "\"$value\""
    is Char -> "'$value'"
    is Array<*> -> "[${value.joinToString(", ") { display(it) }}]"
    // ...
}
```

### FailureContext - 失敗処理の管理

```kotlin
object FailureContext {
    private val failureHandler = ThreadLocalRef { Stack<Failure>() }

    fun currentFailure(): Failure
    fun push(failure: Failure)
    fun pop(): Failure
}

interface Failure {
    fun fail(message: String, cause: Throwable? = null): Nothing
    fun invoke(error: Throwable): Nothing
}

// 即座にスロー
object SimpleFailure : Failure

// 複数の失敗を収集（Soft Assertions用）
class SoftFailure : Failure
```

---

## 具体的な実装計画

### モジュール構成

```
power-assertk/
├── power-assertk-core/          # コアAPI・共通ロジック
│   ├── Assert.kt                # Assert<T>クラス
│   ├── assertions/              # 各種アサーションメソッド
│   └── failure/                 # 失敗処理
├── power-assertk-plugin/        # Gradle Plugin
│   └── PowerAssertKPlugin.kt    # プラグイン設定
└── power-assertk-test/          # テスト用ユーティリティ
```

### コアAPI実装

```kotlin
// power-assertk-core/src/main/kotlin/powerassertk/assert.kt

package powerassertk

/**
 * Power Assert対応のベース関数
 * Kotlin Power Assertプラグインの変換対象
 */
fun powerAssert(condition: Boolean, lazyMessage: () -> String = { "Assertion failed" }) {
    if (!condition) {
        throw AssertionError(lazyMessage())
    }
}

/**
 * assertk互換のエントリーポイント
 */
inline fun <T> assertThat(actual: T, name: String? = null): Assert<T> {
    return ValueAssert(actual, name, AssertingContext.default())
}
```

### Gradle設定例

```kotlin
// ユーザーのbuild.gradle.kts
plugins {
    kotlin("jvm") version "2.2.0"
    kotlin("plugin.power-assert") version "2.2.0"
}

dependencies {
    testImplementation("io.github.xxx:power-assertk-core:1.0.0")
}

@OptIn(ExperimentalKotlinGradlePluginApi::class)
powerAssert {
    functions = listOf(
        "powerassertk.powerAssert",
        "powerassertk.AssertScope.assert"
    )
    includedSourceSets = listOf("test")
}
```

## 参考リンク

- [Kotlin Power Assert Documentation](https://kotlinlang.org/docs/power-assert.html)
- [assertk GitHub Repository](https://github.com/assertk-org/assertk)
- [Kotlin Power Assert Plugin Source](https://github.com/JetBrains/kotlin/tree/master/plugins/power-assert)
