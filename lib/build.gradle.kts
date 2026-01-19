import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.power.assert)
    alias(libs.plugins.detekt)
    alias(libs.plugins.ktlint)
    id("conventions.linting")
}

repositories {
    mavenCentral()
}

kotlin {
    jvmToolchain(21)

    jvm()

    js(IR) {
        browser()
        nodejs()
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }

        val jvmMain by getting {
            dependencies {
                implementation(kotlin("stdlib-jdk8"))
                implementation(kotlin("reflect"))
            }
        }

        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test-junit5"))
            }
        }

        val jsMain by getting {
            dependencies {
                implementation(kotlin("stdlib-js"))
            }
        }

        val jsTest by getting {
            dependencies {
                implementation(kotlin("test-js"))
            }
        }
    }
}

@OptIn(ExperimentalKotlinGradlePluginApi::class)
powerAssert {
    functions =
        listOf(
            "com.litols.power.assertk.assertions.isEqualTo",
            "com.litols.power.assertk.assertions.isNotEqualTo",
            "com.litols.power.assertk.assertions.isNull",
            "com.litols.power.assertk.assertions.isNotNull",
            "com.litols.power.assertk.assertions.isSameInstanceAs",
            "com.litols.power.assertk.assertions.isNotSameInstanceAs",
            "com.litols.power.assertk.assertions.isSameAs",
            "com.litols.power.assertk.assertions.isNotSameAs",
            "com.litols.power.assertk.assertions.isIn",
            "com.litols.power.assertk.assertions.isNotIn",
            "com.litols.power.assertk.assertions.hasToString",
            "com.litols.power.assertk.assertions.hasHashCode",
            "com.litols.power.assertk.assertions.isInstanceOf",
            "com.litols.power.assertk.assertions.isNotInstanceOf",
            // Wave 1.2: Boolean assertions
            "com.litols.power.assertk.assertions.isTrue",
            "com.litols.power.assertk.assertions.isFalse",
            // Wave 1.3: Comparable assertions
            "com.litols.power.assertk.assertions.isGreaterThan",
            "com.litols.power.assertk.assertions.isLessThan",
            "com.litols.power.assertk.assertions.isGreaterThanOrEqualTo",
            "com.litols.power.assertk.assertions.isLessThanOrEqualTo",
            "com.litols.power.assertk.assertions.isBetween",
            "com.litols.power.assertk.assertions.isStrictlyBetween",
            "com.litols.power.assertk.assertions.isCloseTo",
            "com.litols.power.assertk.assertions.isEqualByComparingTo",
            // Wave 1.4: Number assertions (Int, Long, Double, Float)
            "com.litols.power.assertk.assertions.isZero",
            "com.litols.power.assertk.assertions.isNotZero",
            "com.litols.power.assertk.assertions.isPositive",
            "com.litols.power.assertk.assertions.isNegative",
            // Wave 2.1: CharSequence assertions
            "com.litols.power.assertk.assertions.isEmpty",
            "com.litols.power.assertk.assertions.isNotEmpty",
            "com.litols.power.assertk.assertions.isNullOrEmpty",
            "com.litols.power.assertk.assertions.hasLength",
            "com.litols.power.assertk.assertions.hasSameLengthAs",
            "com.litols.power.assertk.assertions.hasLineCount",
            "com.litols.power.assertk.assertions.contains",
            "com.litols.power.assertk.assertions.doesNotContain",
            "com.litols.power.assertk.assertions.startsWith",
            "com.litols.power.assertk.assertions.endsWith",
            "com.litols.power.assertk.assertions.matches",
            "com.litols.power.assertk.assertions.containsMatch",
            // Wave 2.2: String assertions
            "com.litols.power.assertk.assertions.isEqualTo",
            "com.litols.power.assertk.assertions.isNotEqualTo",
            // Wave 2.3: Collection assertions
            "com.litols.power.assertk.assertions.isEmpty",
            "com.litols.power.assertk.assertions.isNotEmpty",
            "com.litols.power.assertk.assertions.isNullOrEmpty",
            "com.litols.power.assertk.assertions.hasSize",
            "com.litols.power.assertk.assertions.hasSameSizeAs",
            // Wave 2.4: Map assertions
            // Temporarily disabled due to Power Assert transformation issues:
            // "com.litols.power.assertk.assertions.contains",
            // "com.litols.power.assertk.assertions.doesNotContain",
            "com.litols.power.assertk.assertions.containsAll",
            "com.litols.power.assertk.assertions.containsAtLeast",
            "com.litols.power.assertk.assertions.containsOnly",
            "com.litols.power.assertk.assertions.containsNone",
            "com.litols.power.assertk.assertions.doesNotContainKey",
            // Wave 2.5: Array assertions (reuses Collection method names)
            // isEmpty, isNotEmpty, isNullOrEmpty, hasSize, hasSameSizeAs already listed above
            // Wave 3.1: Iterable assertions
            // Note: contains/doesNotContain temporarily disabled due to Power Assert issues (same as Map)
            // "com.litols.power.assertk.assertions.contains",
            // "com.litols.power.assertk.assertions.doesNotContain",
            "com.litols.power.assertk.assertions.containsAll",
            "com.litols.power.assertk.assertions.containsAtLeast",
            "com.litols.power.assertk.assertions.containsOnly",
            "com.litols.power.assertk.assertions.containsExactlyInAnyOrder",
            "com.litols.power.assertk.assertions.containsNone",
            // isEmpty/isNotEmpty already listed above
            // Note: each, any, none, atLeast, atMost, exactly cannot use Power Assert
            // because they take both a function parameter and a message parameter,
            // causing the plugin to confuse which lambda is which
            // "com.litols.power.assertk.assertions.each",
            // "com.litols.power.assertk.assertions.any",
            // "com.litols.power.assertk.assertions.none",
            // "com.litols.power.assertk.assertions.atLeast",
            // "com.litols.power.assertk.assertions.atMost",
            // "com.litols.power.assertk.assertions.exactly",
            // Note: first(), single(), extracting() are transformation methods without message parameters
            // Note: length(), size(), and key() are transformation methods without message parameters
            // Note: toStringFun and hashCodeFun are transformation methods without message parameters
            // They should not be in the Power Assert functions list
            // "com.litols.power.assertk.assertions.toStringFun",
            // "com.litols.power.assertk.assertions.hashCodeFun",
            // Note: hasClass and doesNotHaveClass are not Power Assert compatible yet
            // "com.litols.power.assertk.assertions.hasClass",
            // "com.litols.power.assertk.assertions.doesNotHaveClass",
            // Wave 3.2: List assertions
            // Note: index() is a transformation method without message parameter
            "com.litols.power.assertk.assertions.containsExactly",
            "com.litols.power.assertk.assertions.containsSubList",
            "com.litols.power.assertk.assertions.startsWith",
            "com.litols.power.assertk.assertions.endsWith",
            // Wave 3.3: Sequence assertions
            // Note: contains/doesNotContain temporarily disabled due to Power Assert issues
            // "com.litols.power.assertk.assertions.contains",  // Sequence version
            // "com.litols.power.assertk.assertions.doesNotContain",  // Sequence version
            // Note: containsAll, containsAtLeast, containsOnly, containsExactly already listed above
            // "com.litols.power.assertk.assertions.containsExactlyInAnyOrder",  // already listed for Iterable
            // "com.litols.power.assertk.assertions.containsNone",  // already listed for Iterable
            // Note: isEmpty/isNotEmpty already listed above
            // Note: each, any, none, atLeast, atMost, exactly cannot use Power Assert (already documented above)
            // Note: first(), single(), extracting() are transformation methods without message parameters
            // Wave 3.4: Array assertions (extension to existing Wave 2.5 methods)
            // Note: contains/doesNotContain temporarily disabled due to Power Assert issues
            // "com.litols.power.assertk.assertions.contains",  // Array version
            // "com.litols.power.assertk.assertions.doesNotContain",  // Array version
            // Note: containsAll, containsAtLeast, containsOnly, containsExactly, containsExactlyInAnyOrder, containsNone already listed above
            // Note: isEmpty/isNotEmpty already listed above
            // Note: each, any, none, atLeast, atMost, exactly cannot use Power Assert (already documented above)
            // Note: first(), single(), index(), extracting() are transformation methods without message parameters
            // Wave 4.1: Throwable assertions
            // Note: message(), cause(), rootCause() are transformation methods without message parameters
            "com.litols.power.assertk.assertions.hasMessage",
            "com.litols.power.assertk.assertions.messageContains",
            "com.litols.power.assertk.assertions.hasCause",
            "com.litols.power.assertk.assertions.hasNoCause",
            "com.litols.power.assertk.assertions.hasRootCause",
            // Wave 4.2: Result assertions
            "com.litols.power.assertk.assertions.isSuccess",
            "com.litols.power.assertk.assertions.isFailure",
            // Wave 4.3: Predicate assertions
            // Note: matchesPredicate() takes a function parameter, which may confuse Power Assert
            // "com.litols.power.assertk.assertions.matchesPredicate",
            // Wave 5.1: File assertions
            // Note: name(), path(), parent(), extension(), text(), bytes() are transformation methods without message parameters
            "com.litols.power.assertk.assertions.exists",
            "com.litols.power.assertk.assertions.isDirectory",
            "com.litols.power.assertk.assertions.isFile",
            "com.litols.power.assertk.assertions.isHidden",
            "com.litols.power.assertk.assertions.isNotHidden",
            "com.litols.power.assertk.assertions.hasName",
            "com.litols.power.assertk.assertions.hasPath",
            "com.litols.power.assertk.assertions.hasParent",
            "com.litols.power.assertk.assertions.hasExtension",
            "com.litols.power.assertk.assertions.hasDirectChild",
            "com.litols.power.assertk.assertions.hasText",
            // Wave 5.2: Path assertions
            // Note: bytes(), lines() are transformation methods without message parameters
            // "com.litols.power.assertk.assertions.exists",  // Already added for File
            // "com.litols.power.assertk.assertions.isDirectory",  // Already added for File
            "com.litols.power.assertk.assertions.isRegularFile",
            "com.litols.power.assertk.assertions.isSymbolicLink",
            // "com.litols.power.assertk.assertions.isHidden",  // Already added for File
            "com.litols.power.assertk.assertions.isReadable",
            "com.litols.power.assertk.assertions.isWritable",
            "com.litols.power.assertk.assertions.isExecutable",
            "com.litols.power.assertk.assertions.isSameFileAs",
            // Wave 5.3: Optional assertions
            // Note: isPresent() is a transformation method without message parameter
            // "com.litols.power.assertk.assertions.isEmpty",  // Already added for Collection
            "com.litols.power.assertk.assertions.hasValue",
            // Wave 5.4: InputStream assertions
            "com.litols.power.assertk.assertions.hasSameContentAs",
            "com.litols.power.assertk.assertions.hasNotSameContentAs",
            // Wave 5.5: AnyJvm & ThrowableJvm assertions
            // Note: jClass(), stackTrace() are transformation methods without message parameters
            "com.litols.power.assertk.assertions.isDataClassEqualTo",
            "com.litols.power.assertk.assertions.isEqualToIgnoringGivenProperties",
        )
    includedSourceSets = listOf("commonTest", "jvmTest", "jsTest")
}

tasks.named<Test>("jvmTest") {
    useJUnitPlatform()
}
