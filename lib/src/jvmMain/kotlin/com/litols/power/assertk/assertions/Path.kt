@file:Suppress("TooManyFunctions")

package com.litols.power.assertk.assertions

import com.litols.power.assertk.Assert
import java.nio.charset.Charset
import java.nio.file.LinkOption
import java.nio.file.Path
import kotlin.io.path.exists
import kotlin.io.path.isDirectory
import kotlin.io.path.isExecutable
import kotlin.io.path.isHidden
import kotlin.io.path.isReadable
import kotlin.io.path.isRegularFile
import kotlin.io.path.isSameFileAs
import kotlin.io.path.isSymbolicLink
import kotlin.io.path.isWritable
import kotlin.io.path.readBytes
import kotlin.io.path.readLines

/**
 * Asserts the path exists.
 */
fun Assert<Path>.exists(
    vararg options: LinkOption,
    message: (() -> String)? = null,
) {
    if (!actual.exists(*options)) {
        throw AssertionError(
            message?.invoke() ?: "expected to exist but was:<$actual>",
        )
    }
}

/**
 * Asserts the path is a directory.
 */
fun Assert<Path>.isDirectory(
    vararg options: LinkOption,
    message: (() -> String)? = null,
) {
    if (!actual.isDirectory(*options)) {
        throw AssertionError(
            message?.invoke() ?: "expected to be a directory but was:<$actual>",
        )
    }
}

/**
 * Asserts the path is a regular file.
 */
fun Assert<Path>.isRegularFile(
    vararg options: LinkOption,
    message: (() -> String)? = null,
) {
    if (!actual.isRegularFile(*options)) {
        throw AssertionError(
            message?.invoke() ?: "expected to be a regular file but was:<$actual>",
        )
    }
}

/**
 * Asserts the path is a symbolic link.
 */
fun Assert<Path>.isSymbolicLink(message: (() -> String)? = null) {
    if (!actual.isSymbolicLink()) {
        throw AssertionError(
            message?.invoke() ?: "expected to be a symbolic link but was:<$actual>",
        )
    }
}

/**
 * Asserts the path is hidden.
 */
fun Assert<Path>.isHidden(message: (() -> String)? = null) {
    if (!actual.isHidden()) {
        throw AssertionError(
            message?.invoke() ?: "expected to be hidden but was:<$actual>",
        )
    }
}

/**
 * Asserts the path is readable.
 */
fun Assert<Path>.isReadable(message: (() -> String)? = null) {
    if (!actual.isReadable()) {
        throw AssertionError(
            message?.invoke() ?: "expected to be readable but was:<$actual>",
        )
    }
}

/**
 * Asserts the path is writable.
 */
fun Assert<Path>.isWritable(message: (() -> String)? = null) {
    if (!actual.isWritable()) {
        throw AssertionError(
            message?.invoke() ?: "expected to be writable but was:<$actual>",
        )
    }
}

/**
 * Asserts the path is executable.
 */
fun Assert<Path>.isExecutable(message: (() -> String)? = null) {
    if (!actual.isExecutable()) {
        throw AssertionError(
            message?.invoke() ?: "expected to be executable but was:<$actual>",
        )
    }
}

/**
 * Asserts the path points to the same file as the expected path.
 */
fun Assert<Path>.isSameFileAs(
    expected: Path,
    message: (() -> String)? = null,
) {
    if (!actual.isSameFileAs(expected)) {
        throw AssertionError(
            message?.invoke() ?: "expected to be same file as:<$expected> but was:<$actual>",
        )
    }
}

/**
 * Returns an assertion on the path's byte content.
 */
fun Assert<Path>.bytes(): Assert<ByteArray> {
    if (!actual.exists()) {
        throw AssertionError("expected file to exist but was:<$actual>")
    }
    return Assert(actual.readBytes())
}

/**
 * Returns an assertion on the path's lines of text.
 */
fun Assert<Path>.lines(charset: Charset = Charsets.UTF_8): Assert<List<String>> {
    if (!actual.exists()) {
        throw AssertionError("expected file to exist but was:<$actual>")
    }
    return Assert(actual.readLines(charset))
}
