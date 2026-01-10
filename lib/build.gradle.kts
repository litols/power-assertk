import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.power.assert)
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
        // Note: toStringFun and hashCodeFun are transformation methods without message parameters
        // They should not be in the Power Assert functions list
        // "powerassertk.toStringFun",
        // "powerassertk.hashCodeFun",
        // Note: hasClass and doesNotHaveClass are not Power Assert compatible yet
        // "powerassertk.hasClass",
        // "powerassertk.doesNotHaveClass",
    )
    includedSourceSets = listOf("commonTest", "jvmTest", "jsTest")
}

tasks.named<Test>("jvmTest") {
    useJUnitPlatform()
}
