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
            "powerassertk.assertions.isEqualTo",
            "powerassertk.assertions.isNotEqualTo",
            "powerassertk.assertions.isNull",
            "powerassertk.assertions.isNotNull",
            "powerassertk.assertions.isSameInstanceAs",
            "powerassertk.assertions.isNotSameInstanceAs",
            "powerassertk.assertions.isSameAs",
            "powerassertk.assertions.isNotSameAs",
            "powerassertk.assertions.isIn",
            "powerassertk.assertions.isNotIn",
            "powerassertk.assertions.hasToString",
            "powerassertk.assertions.hasHashCode",
            "powerassertk.assertions.isInstanceOf",
            "powerassertk.assertions.isNotInstanceOf",
            // Wave 1.2: Boolean assertions
            "powerassertk.assertions.isTrue",
            "powerassertk.assertions.isFalse",
            // Wave 1.3: Comparable assertions
            "powerassertk.assertions.isGreaterThan",
            "powerassertk.assertions.isLessThan",
            "powerassertk.assertions.isGreaterThanOrEqualTo",
            "powerassertk.assertions.isLessThanOrEqualTo",
            "powerassertk.assertions.isBetween",
            "powerassertk.assertions.isStrictlyBetween",
            "powerassertk.assertions.isCloseTo",
            "powerassertk.assertions.isEqualByComparingTo",
            // Wave 1.4: Number assertions (Int, Long, Double, Float)
            "powerassertk.assertions.isZero",
            "powerassertk.assertions.isNotZero",
            "powerassertk.assertions.isPositive",
            "powerassertk.assertions.isNegative",
            // Wave 2.1: CharSequence assertions
            "powerassertk.assertions.isEmpty",
            "powerassertk.assertions.isNotEmpty",
            "powerassertk.assertions.isNullOrEmpty",
            "powerassertk.assertions.hasLength",
            "powerassertk.assertions.hasSameLengthAs",
            "powerassertk.assertions.hasLineCount",
            "powerassertk.assertions.contains",
            "powerassertk.assertions.doesNotContain",
            "powerassertk.assertions.startsWith",
            "powerassertk.assertions.endsWith",
            "powerassertk.assertions.matches",
            "powerassertk.assertions.containsMatch",
            // Wave 2.2: String assertions
            "powerassertk.assertions.isEqualTo",
            "powerassertk.assertions.isNotEqualTo",
            // Wave 2.3: Collection assertions
            "powerassertk.assertions.isEmpty",
            "powerassertk.assertions.isNotEmpty",
            "powerassertk.assertions.isNullOrEmpty",
            "powerassertk.assertions.hasSize",
            "powerassertk.assertions.hasSameSizeAs",
            // Wave 2.4: Map assertions
            // Temporarily disabled due to Power Assert transformation issues:
            // "powerassertk.assertions.contains",
            // "powerassertk.assertions.doesNotContain",
            "powerassertk.assertions.containsAll",
            "powerassertk.assertions.containsAtLeast",
            "powerassertk.assertions.containsOnly",
            "powerassertk.assertions.containsNone",
            "powerassertk.assertions.doesNotContainKey",
            // Wave 2.5: Array assertions (reuses Collection method names)
            // isEmpty, isNotEmpty, isNullOrEmpty, hasSize, hasSameSizeAs already listed above
            // Wave 3.1: Iterable assertions
            // Note: contains/doesNotContain temporarily disabled due to Power Assert issues (same as Map)
            // "powerassertk.assertions.contains",
            // "powerassertk.assertions.doesNotContain",
            "powerassertk.assertions.containsAll",
            "powerassertk.assertions.containsAtLeast",
            "powerassertk.assertions.containsOnly",
            "powerassertk.assertions.containsExactlyInAnyOrder",
            "powerassertk.assertions.containsNone",
            // isEmpty/isNotEmpty already listed above
            // Note: each, any, none, atLeast, atMost, exactly cannot use Power Assert
            // because they take both a function parameter and a message parameter,
            // causing the plugin to confuse which lambda is which
            // "powerassertk.assertions.each",
            // "powerassertk.assertions.any",
            // "powerassertk.assertions.none",
            // "powerassertk.assertions.atLeast",
            // "powerassertk.assertions.atMost",
            // "powerassertk.assertions.exactly",
            // Note: first(), single(), extracting() are transformation methods without message parameters
            // Note: length(), size(), and key() are transformation methods without message parameters
            // Note: toStringFun and hashCodeFun are transformation methods without message parameters
            // They should not be in the Power Assert functions list
            // "powerassertk.assertions.toStringFun",
            // "powerassertk.assertions.hashCodeFun",
            // Note: hasClass and doesNotHaveClass are not Power Assert compatible yet
            // "powerassertk.assertions.hasClass",
            // "powerassertk.assertions.doesNotHaveClass",
            // Wave 3.2: List assertions
            // Note: index() is a transformation method without message parameter
            "powerassertk.assertions.containsExactly",
            "powerassertk.assertions.containsSubList",
            "powerassertk.assertions.startsWith",
            "powerassertk.assertions.endsWith",
            // Wave 3.3: Sequence assertions
            // Note: contains/doesNotContain temporarily disabled due to Power Assert issues
            // "powerassertk.assertions.contains",  // Sequence version
            // "powerassertk.assertions.doesNotContain",  // Sequence version
            // Note: containsAll, containsAtLeast, containsOnly, containsExactly already listed above
            // "powerassertk.assertions.containsExactlyInAnyOrder",  // already listed for Iterable
            // "powerassertk.assertions.containsNone",  // already listed for Iterable
            // Note: isEmpty/isNotEmpty already listed above
            // Note: each, any, none, atLeast, atMost, exactly cannot use Power Assert (already documented above)
            // Note: first(), single(), extracting() are transformation methods without message parameters
            // Wave 3.4: Array assertions (extension to existing Wave 2.5 methods)
            // Note: contains/doesNotContain temporarily disabled due to Power Assert issues
            // "powerassertk.assertions.contains",  // Array version
            // "powerassertk.assertions.doesNotContain",  // Array version
            // Note: containsAll, containsAtLeast, containsOnly, containsExactly, containsExactlyInAnyOrder, containsNone already listed above
            // Note: isEmpty/isNotEmpty already listed above
            // Note: each, any, none, atLeast, atMost, exactly cannot use Power Assert (already documented above)
            // Note: first(), single(), index(), extracting() are transformation methods without message parameters
            // Wave 4.1: Throwable assertions
            // Note: message(), cause(), rootCause() are transformation methods without message parameters
            "powerassertk.assertions.hasMessage",
            "powerassertk.assertions.messageContains",
            "powerassertk.assertions.hasCause",
            "powerassertk.assertions.hasNoCause",
            "powerassertk.assertions.hasRootCause",
            // Wave 4.2: Result assertions
            "powerassertk.assertions.isSuccess",
            "powerassertk.assertions.isFailure",
            // Wave 4.3: Predicate assertions
            // Note: matchesPredicate() takes a function parameter, which may confuse Power Assert
            // "powerassertk.assertions.matchesPredicate",
            // Wave 5.1: File assertions
            // Note: name(), path(), parent(), extension(), text(), bytes() are transformation methods without message parameters
            "powerassertk.assertions.exists",
            "powerassertk.assertions.isDirectory",
            "powerassertk.assertions.isFile",
            "powerassertk.assertions.isHidden",
            "powerassertk.assertions.isNotHidden",
            "powerassertk.assertions.hasName",
            "powerassertk.assertions.hasPath",
            "powerassertk.assertions.hasParent",
            "powerassertk.assertions.hasExtension",
            "powerassertk.assertions.hasDirectChild",
            "powerassertk.assertions.hasText",
            // Wave 5.2: Path assertions
            // Note: bytes(), lines() are transformation methods without message parameters
            // "powerassertk.assertions.exists",  // Already added for File
            // "powerassertk.assertions.isDirectory",  // Already added for File
            "powerassertk.assertions.isRegularFile",
            "powerassertk.assertions.isSymbolicLink",
            // "powerassertk.assertions.isHidden",  // Already added for File
            "powerassertk.assertions.isReadable",
            "powerassertk.assertions.isWritable",
            "powerassertk.assertions.isExecutable",
            "powerassertk.assertions.isSameFileAs",
            // Wave 5.3: Optional assertions
            // Note: isPresent() is a transformation method without message parameter
            // "powerassertk.assertions.isEmpty",  // Already added for Collection
            "powerassertk.assertions.hasValue",
            // Wave 5.4: InputStream assertions
            "powerassertk.assertions.hasSameContentAs",
            "powerassertk.assertions.hasNotSameContentAs",
            // Wave 5.5: AnyJvm & ThrowableJvm assertions
            // Note: jClass(), stackTrace() are transformation methods without message parameters
            "powerassertk.assertions.isDataClassEqualTo",
            "powerassertk.assertions.isEqualToIgnoringGivenProperties",
        )
    includedSourceSets = listOf("commonTest", "jvmTest", "jsTest")
}

tasks.named<Test>("jvmTest") {
    useJUnitPlatform()
}
