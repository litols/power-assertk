package powerassertk

import java.io.File
import java.nio.charset.Charset

/**
 * Asserts the file exists.
 */
fun Assert<File>.exists(message: (() -> String)? = null) {
    if (!actual.exists()) {
        throw AssertionError(
            message?.invoke() ?: "expected to exist but was:<$actual>",
        )
    }
}

/**
 * Asserts the file is a directory.
 */
fun Assert<File>.isDirectory(message: (() -> String)? = null) {
    if (!actual.isDirectory) {
        throw AssertionError(
            message?.invoke() ?: "expected to be a directory but was:<$actual>",
        )
    }
}

/**
 * Asserts the file is a regular file.
 */
fun Assert<File>.isFile(message: (() -> String)? = null) {
    if (!actual.isFile) {
        throw AssertionError(
            message?.invoke() ?: "expected to be a file but was:<$actual>",
        )
    }
}

/**
 * Asserts the file is hidden.
 */
fun Assert<File>.isHidden(message: (() -> String)? = null) {
    if (!actual.isHidden) {
        throw AssertionError(
            message?.invoke() ?: "expected to be hidden but was:<$actual>",
        )
    }
}

/**
 * Asserts the file is not hidden.
 */
fun Assert<File>.isNotHidden(message: (() -> String)? = null) {
    if (actual.isHidden) {
        throw AssertionError(
            message?.invoke() ?: "expected to not be hidden but was:<$actual>",
        )
    }
}

/**
 * Asserts the file has the expected name.
 */
fun Assert<File>.hasName(
    expected: String,
    message: (() -> String)? = null,
) {
    if (actual.name != expected) {
        throw AssertionError(
            message?.invoke() ?: "expected name:<\"$expected\"> but was:<\"${actual.name}\">",
        )
    }
}

/**
 * Returns an assertion on the file's name.
 */
fun Assert<File>.name(): Assert<String> = Assert(actual.name)

/**
 * Asserts the file has the expected path.
 */
fun Assert<File>.hasPath(
    expected: String,
    message: (() -> String)? = null,
) {
    if (actual.path != expected) {
        throw AssertionError(
            message?.invoke() ?: "expected path:<\"$expected\"> but was:<\"${actual.path}\">",
        )
    }
}

/**
 * Returns an assertion on the file's path.
 */
fun Assert<File>.path(): Assert<String> = Assert(actual.path)

/**
 * Asserts the file has the expected parent path.
 */
fun Assert<File>.hasParent(
    expected: String,
    message: (() -> String)? = null,
) {
    val actualParent = actual.parent
    if (actualParent != expected) {
        throw AssertionError(
            message?.invoke() ?: "expected parent:<\"$expected\"> but was:<\"$actualParent\">",
        )
    }
}

/**
 * Returns an assertion on the file's parent path.
 */
fun Assert<File>.parent(): Assert<String?> = Assert(actual.parent)

/**
 * Asserts the file has the expected extension.
 */
fun Assert<File>.hasExtension(
    expected: String,
    message: (() -> String)? = null,
) {
    if (actual.extension != expected) {
        throw AssertionError(
            message?.invoke() ?: "expected extension:<\"$expected\"> but was:<\"${actual.extension}\">",
        )
    }
}

/**
 * Returns an assertion on the file's extension.
 */
fun Assert<File>.extension(): Assert<String> = Assert(actual.extension)

/**
 * Asserts the directory has the expected direct child.
 */
fun Assert<File>.hasDirectChild(
    expected: File,
    message: (() -> String)? = null,
) {
    if (!actual.isDirectory) {
        throw AssertionError(
            message?.invoke() ?: "expected to be a directory but was:<$actual>",
        )
    }
    val children = actual.listFiles()?.toList() ?: emptyList()
    if (!children.contains(expected)) {
        throw AssertionError(
            message?.invoke() ?: "expected to have direct child:<$expected> but was:<$children>",
        )
    }
}

/**
 * Returns an assertion on the file's text content.
 */
fun Assert<File>.text(charset: Charset = Charsets.UTF_8): Assert<String> {
    if (!actual.exists()) {
        throw AssertionError("expected file to exist but was:<$actual>")
    }
    return Assert(actual.readText(charset))
}

/**
 * Returns an assertion on the file's byte content.
 */
fun Assert<File>.bytes(): Assert<ByteArray> {
    if (!actual.exists()) {
        throw AssertionError("expected file to exist but was:<$actual>")
    }
    return Assert(actual.readBytes())
}

/**
 * Asserts the file has the expected text content.
 */
fun Assert<File>.hasText(
    expected: String,
    charset: Charset = Charsets.UTF_8,
    message: (() -> String)? = null,
) {
    if (!actual.exists()) {
        throw AssertionError(
            message?.invoke() ?: "expected file to exist but was:<$actual>",
        )
    }
    val actualText = actual.readText(charset)
    if (actualText != expected) {
        throw AssertionError(
            message?.invoke() ?: "expected text:<\"$expected\"> but was:<\"$actualText\">",
        )
    }
}
