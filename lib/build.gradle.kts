import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.DetektCreateBaselineTask
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.power.assert)
    alias(libs.plugins.detekt)
    alias(libs.plugins.ktlint)
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
    functions = listOf(
        "powerassertk.isEqualTo",
        "powerassertk.isNotEqualTo",
        "powerassertk.isNull",
        "powerassertk.isNotNull",
        "powerassertk.isSameInstanceAs",
        "powerassertk.isNotSameInstanceAs",
        "powerassertk.isSameAs",
        "powerassertk.isNotSameAs",
        "powerassertk.isIn",
        "powerassertk.isNotIn",
        "powerassertk.hasToString",
        "powerassertk.hasHashCode",
        "powerassertk.isInstanceOf",
        "powerassertk.isNotInstanceOf",
        // Wave 1.2: Boolean assertions
        "powerassertk.isTrue",
        "powerassertk.isFalse",
        // Wave 1.3: Comparable assertions
        "powerassertk.isGreaterThan",
        "powerassertk.isLessThan",
        "powerassertk.isGreaterThanOrEqualTo",
        "powerassertk.isLessThanOrEqualTo",
        "powerassertk.isBetween",
        "powerassertk.isStrictlyBetween",
        "powerassertk.isCloseTo",
        "powerassertk.isEqualByComparingTo",
        // Wave 1.4: Number assertions (Int, Long, Double, Float)
        "powerassertk.isZero",
        "powerassertk.isNotZero",
        "powerassertk.isPositive",
        "powerassertk.isNegative",
        // Wave 2.1: CharSequence assertions
        "powerassertk.isEmpty",
        "powerassertk.isNotEmpty",
        "powerassertk.isNullOrEmpty",
        "powerassertk.hasLength",
        "powerassertk.hasSameLengthAs",
        "powerassertk.hasLineCount",
        "powerassertk.contains",
        "powerassertk.doesNotContain",
        "powerassertk.startsWith",
        "powerassertk.endsWith",
        "powerassertk.matches",
        "powerassertk.containsMatch",
        // Wave 2.2: String assertions
        "powerassertk.isEqualTo",
        "powerassertk.isNotEqualTo",
        // Wave 2.3: Collection assertions
        "powerassertk.isEmpty",
        "powerassertk.isNotEmpty",
        "powerassertk.isNullOrEmpty",
        "powerassertk.hasSize",
        "powerassertk.hasSameSizeAs",
        // Wave 2.4: Map assertions
        // Temporarily disabled due to Power Assert transformation issues:
        // "powerassertk.contains",
        // "powerassertk.doesNotContain",
        "powerassertk.containsAll",
        "powerassertk.containsAtLeast",
        "powerassertk.containsOnly",
        "powerassertk.containsNone",
        "powerassertk.doesNotContainKey",
        // Wave 2.5: Array assertions (reuses Collection method names)
        // isEmpty, isNotEmpty, isNullOrEmpty, hasSize, hasSameSizeAs already listed above
        // Wave 3.1: Iterable assertions
        // Note: contains/doesNotContain temporarily disabled due to Power Assert issues (same as Map)
        // "powerassertk.contains",
        // "powerassertk.doesNotContain",
        "powerassertk.containsAll",
        "powerassertk.containsAtLeast",
        "powerassertk.containsOnly",
        "powerassertk.containsExactlyInAnyOrder",
        "powerassertk.containsNone",
        // isEmpty/isNotEmpty already listed above
        // Note: each, any, none, atLeast, atMost, exactly cannot use Power Assert
        // because they take both a function parameter and a message parameter,
        // causing the plugin to confuse which lambda is which
        // "powerassertk.each",
        // "powerassertk.any",
        // "powerassertk.none",
        // "powerassertk.atLeast",
        // "powerassertk.atMost",
        // "powerassertk.exactly",
        // Note: first(), single(), extracting() are transformation methods without message parameters
        // Note: length(), size(), and key() are transformation methods without message parameters
        // Note: toStringFun and hashCodeFun are transformation methods without message parameters
        // They should not be in the Power Assert functions list
        // "powerassertk.toStringFun",
        // "powerassertk.hashCodeFun",
        // Note: hasClass and doesNotHaveClass are not Power Assert compatible yet
        // "powerassertk.hasClass",
        // "powerassertk.doesNotHaveClass",
        // Wave 3.2: List assertions
        // Note: index() is a transformation method without message parameter
        "powerassertk.containsExactly",
        "powerassertk.containsSubList",
        "powerassertk.startsWith",
        "powerassertk.endsWith",
        // Wave 3.3: Sequence assertions
        // Note: contains/doesNotContain temporarily disabled due to Power Assert issues
        // "powerassertk.contains",  // Sequence version
        // "powerassertk.doesNotContain",  // Sequence version
        // Note: containsAll, containsAtLeast, containsOnly, containsExactly already listed above
        // "powerassertk.containsExactlyInAnyOrder",  // already listed for Iterable
        // "powerassertk.containsNone",  // already listed for Iterable
        // Note: isEmpty/isNotEmpty already listed above
        // Note: each, any, none, atLeast, atMost, exactly cannot use Power Assert (already documented above)
        // Note: first(), single(), extracting() are transformation methods without message parameters
        // Wave 3.4: Array assertions (extension to existing Wave 2.5 methods)
        // Note: contains/doesNotContain temporarily disabled due to Power Assert issues
        // "powerassertk.contains",  // Array version
        // "powerassertk.doesNotContain",  // Array version
        // Note: containsAll, containsAtLeast, containsOnly, containsExactly, containsExactlyInAnyOrder, containsNone already listed above
        // Note: isEmpty/isNotEmpty already listed above
        // Note: each, any, none, atLeast, atMost, exactly cannot use Power Assert (already documented above)
        // Note: first(), single(), index(), extracting() are transformation methods without message parameters
        // Wave 4.1: Throwable assertions
        // Note: message(), cause(), rootCause() are transformation methods without message parameters
        "powerassertk.hasMessage",
        "powerassertk.messageContains",
        "powerassertk.hasCause",
        "powerassertk.hasNoCause",
        "powerassertk.hasRootCause",
        // Wave 4.2: Result assertions
        "powerassertk.isSuccess",
        "powerassertk.isFailure",
        // Wave 4.3: Predicate assertions
        // Note: matchesPredicate() takes a function parameter, which may confuse Power Assert
        // "powerassertk.matchesPredicate",
        // Wave 5.1: File assertions
        // Note: name(), path(), parent(), extension(), text(), bytes() are transformation methods without message parameters
        "powerassertk.exists",
        "powerassertk.isDirectory",
        "powerassertk.isFile",
        "powerassertk.isHidden",
        "powerassertk.isNotHidden",
        "powerassertk.hasName",
        "powerassertk.hasPath",
        "powerassertk.hasParent",
        "powerassertk.hasExtension",
        "powerassertk.hasDirectChild",
        "powerassertk.hasText",
        // Wave 5.2: Path assertions
        // Note: bytes(), lines() are transformation methods without message parameters
        // "powerassertk.exists",  // Already added for File
        // "powerassertk.isDirectory",  // Already added for File
        "powerassertk.isRegularFile",
        "powerassertk.isSymbolicLink",
        // "powerassertk.isHidden",  // Already added for File
        "powerassertk.isReadable",
        "powerassertk.isWritable",
        "powerassertk.isExecutable",
        "powerassertk.isSameFileAs",
        // Wave 5.3: Optional assertions
        // Note: isPresent() is a transformation method without message parameter
        // "powerassertk.isEmpty",  // Already added for Collection
        "powerassertk.hasValue",
        // Wave 5.4: InputStream assertions
        "powerassertk.hasSameContentAs",
        "powerassertk.hasNotSameContentAs",
        // Wave 5.5: AnyJvm & ThrowableJvm assertions
        // Note: jClass(), stackTrace() are transformation methods without message parameters
        "powerassertk.isDataClassEqualTo",
        "powerassertk.isEqualToIgnoringGivenProperties",
    )
    includedSourceSets = listOf("commonTest", "jvmTest", "jsTest")
}

tasks.named<Test>("jvmTest") {
    useJUnitPlatform()
}

// detekt configuration
detekt {
    buildUponDefaultConfig = true
    allRules = false
    config.setFrom(files("$rootDir/config/detekt/detekt.yml"))
    baseline = file("$rootDir/config/detekt/baseline.xml")
    source.setFrom(
        files(
            "src/commonMain/kotlin",
            "src/commonTest/kotlin",
            "src/jvmMain/kotlin",
            "src/jvmTest/kotlin",
            "src/jsMain/kotlin",
            "src/jsTest/kotlin",
            "src/nativeMain/kotlin",
            "src/nativeTest/kotlin"
        ).filter { it.exists() }
    )
}

dependencies {
    detektPlugins(libs.detekt.formatting)
}

// ktlint configuration
configure<org.jlleitschuh.gradle.ktlint.KtlintExtension> {
    version.set("1.4.1")
    verbose.set(true)
    android.set(false)
    outputToConsole.set(true)
    outputColorName.set("RED")
    ignoreFailures.set(false)
    filter {
        exclude("**/build/**")
        exclude("**/generated/**")
    }
}

// Detekt tasks configuration
tasks.withType<Detekt>().configureEach {
    jvmTarget = "21"
    reports {
        html.required.set(true)
        xml.required.set(true)
        txt.required.set(false)
        sarif.required.set(false)
        md.required.set(false)
    }
}

tasks.withType<DetektCreateBaselineTask>().configureEach {
    jvmTarget = "21"
}
