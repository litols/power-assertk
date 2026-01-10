package powerassertk

import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.createTempDirectory
import kotlin.io.path.createTempFile
import kotlin.io.path.writeText
import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class PathTest {
    // Helper to create temp file/directory
    private fun createTempFilePath(content: String = "test content"): Path {
        val path = createTempFile()
        path.toFile().deleteOnExit()
        path.writeText(content)
        return path
    }

    private fun createTempDirectoryPath(): Path {
        val path = createTempDirectory()
        path.toFile().deleteOnExit()
        return path
    }

    // exists tests
    @Test
    fun exists_succeeds_when_path_exists() {
        val path = createTempFilePath()
        assertThat(path).exists()
    }

    @Test
    fun exists_fails_when_path_does_not_exist() {
        val path = Path.of("/nonexistent/path/file.txt")
        val error =
            assertFailsWith<AssertionError> {
                assertThat(path).exists()
            }
        assertTrue(error.message!!.contains("exists") || error.message!!.contains("expected to exist"))
    }

    @Test
    fun exists_supports_custom_message() {
        val path = Path.of("/nonexistent/path/file.txt")
        val error =
            assertFailsWith<AssertionError> {
                assertThat(path).exists { "Custom: path should exist" }
            }
        assertTrue(error.message!!.contains("Custom: path should exist"))
    }

    // isDirectory tests
    @Test
    fun isDirectory_succeeds_when_is_directory() {
        val dir = createTempDirectoryPath()
        assertThat(dir).isDirectory()
    }

    @Test
    fun isDirectory_fails_when_is_file() {
        val file = createTempFilePath()
        val error =
            assertFailsWith<AssertionError> {
                assertThat(file).isDirectory()
            }
        assertTrue(error.message!!.contains("isDirectory") || error.message!!.contains("expected to be a directory"))
    }

    @Test
    fun isDirectory_supports_custom_message() {
        val file = createTempFilePath()
        val error =
            assertFailsWith<AssertionError> {
                assertThat(file).isDirectory { "Custom: should be directory" }
            }
        assertTrue(error.message!!.contains("Custom: should be directory"))
    }

    // isRegularFile tests
    @Test
    fun isRegularFile_succeeds_when_is_file() {
        val file = createTempFilePath()
        assertThat(file).isRegularFile()
    }

    @Test
    fun isRegularFile_fails_when_is_directory() {
        val dir = createTempDirectoryPath()
        val error =
            assertFailsWith<AssertionError> {
                assertThat(dir).isRegularFile()
            }
        assertTrue(error.message!!.contains("isRegularFile") || error.message!!.contains("expected to be a regular file"))
    }

    @Test
    fun isRegularFile_supports_custom_message() {
        val dir = createTempDirectoryPath()
        val error =
            assertFailsWith<AssertionError> {
                assertThat(dir).isRegularFile { "Custom: should be file" }
            }
        assertTrue(error.message!!.contains("Custom: should be file"))
    }

    // isSymbolicLink tests
    @Test
    fun isSymbolicLink_succeeds_when_is_symlink() {
        val file = createTempFilePath()
        val symlink =
            try {
                Files.createSymbolicLink(Path.of(file.parent.toString(), "symlink"), file)
            } catch (e: UnsupportedOperationException) {
                return // Skip test on systems that don't support symlinks
            }
        symlink.toFile().deleteOnExit()
        assertThat(symlink).isSymbolicLink()
    }

    @Test
    fun isSymbolicLink_fails_when_is_not_symlink() {
        val file = createTempFilePath()
        val error =
            assertFailsWith<AssertionError> {
                assertThat(file).isSymbolicLink()
            }
        assertTrue(error.message!!.contains("isSymbolicLink") || error.message!!.contains("expected to be a symbolic link"))
    }

    // isHidden tests
    @Test
    fun isHidden_succeeds_when_is_hidden() {
        val dir = createTempDirectoryPath()
        val hiddenFile = Path.of(dir.toString(), ".hidden")
        hiddenFile.toFile().createNewFile()
        hiddenFile.toFile().deleteOnExit()
        if (hiddenFile.toFile().isHidden) {
            assertThat(hiddenFile).isHidden()
        }
        // On some platforms, files starting with "." may not be considered hidden
    }

    @Test
    fun isHidden_fails_when_is_not_hidden() {
        val file = createTempFilePath()
        if (!file.toFile().isHidden) {
            val error =
                assertFailsWith<AssertionError> {
                    assertThat(file).isHidden()
                }
            assertTrue(error.message!!.contains("isHidden") || error.message!!.contains("expected to be hidden"))
        }
    }

    // isReadable tests
    @Test
    fun isReadable_succeeds_when_is_readable() {
        val file = createTempFilePath()
        assertThat(file).isReadable()
    }

    @Test
    fun isReadable_fails_when_is_not_readable() {
        val file = createTempFilePath()
        file.toFile().setReadable(false)
        if (!file.toFile().canRead()) {
            val error =
                assertFailsWith<AssertionError> {
                    assertThat(file).isReadable()
                }
            assertTrue(error.message!!.contains("isReadable") || error.message!!.contains("expected to be readable"))
        }
        file.toFile().setReadable(true) // Restore permissions
    }

    // isWritable tests
    @Test
    fun isWritable_succeeds_when_is_writable() {
        val file = createTempFilePath()
        assertThat(file).isWritable()
    }

    @Test
    fun isWritable_fails_when_is_not_writable() {
        val file = createTempFilePath()
        file.toFile().setWritable(false)
        if (!file.toFile().canWrite()) {
            val error =
                assertFailsWith<AssertionError> {
                    assertThat(file).isWritable()
                }
            assertTrue(error.message!!.contains("isWritable") || error.message!!.contains("expected to be writable"))
        }
        file.toFile().setWritable(true) // Restore permissions
    }

    // isExecutable tests
    @Test
    fun isExecutable_succeeds_when_is_executable() {
        val file = createTempFilePath()
        file.toFile().setExecutable(true)
        if (file.toFile().canExecute()) {
            assertThat(file).isExecutable()
        }
    }

    @Test
    fun isExecutable_fails_when_is_not_executable() {
        val file = createTempFilePath()
        file.toFile().setExecutable(false)
        if (!file.toFile().canExecute()) {
            val error =
                assertFailsWith<AssertionError> {
                    assertThat(file).isExecutable()
                }
            assertTrue(error.message!!.contains("isExecutable") || error.message!!.contains("expected to be executable"))
        }
    }

    // isSameFileAs tests
    @Test
    fun isSameFileAs_succeeds_when_same_file() {
        val file = createTempFilePath()
        assertThat(file).isSameFileAs(file)
    }

    @Test
    fun isSameFileAs_fails_when_different_file() {
        val file1 = createTempFilePath()
        val file2 = createTempFilePath()
        val error =
            assertFailsWith<AssertionError> {
                assertThat(file1).isSameFileAs(file2)
            }
        assertTrue(error.message!!.contains("isSameFileAs") || error.message!!.contains("expected to be same file"))
    }

    @Test
    fun isSameFileAs_supports_custom_message() {
        val file1 = createTempFilePath()
        val file2 = createTempFilePath()
        val error =
            assertFailsWith<AssertionError> {
                assertThat(file1).isSameFileAs(file2) { "Custom: should be same file" }
            }
        assertTrue(error.message!!.contains("Custom: should be same file"))
    }

    // bytes tests
    @Test
    fun bytes_returns_file_bytes() {
        val content = "Hello World"
        val file = createTempFilePath(content = content)
        val bytesAssert = assertThat(file).bytes()
        assertTrue(bytesAssert.actual.contentEquals(content.toByteArray()))
    }

    @Test
    fun bytes_fails_when_file_does_not_exist() {
        val file = Path.of("/nonexistent/file.txt")
        val error =
            assertFailsWith<AssertionError> {
                assertThat(file).bytes()
            }
        assertTrue(error.message!!.contains("expected file to exist") || error.message!!.contains("bytes"))
    }

    // lines tests
    @Test
    fun lines_returns_file_lines() {
        val content = "Line 1\nLine 2\nLine 3"
        val file = createTempFilePath(content = content)
        val linesAssert = assertThat(file).lines()
        assertTrue(linesAssert.actual.size == 3)
        assertTrue(linesAssert.actual[0] == "Line 1")
        assertTrue(linesAssert.actual[1] == "Line 2")
        assertTrue(linesAssert.actual[2] == "Line 3")
    }

    @Test
    fun lines_fails_when_file_does_not_exist() {
        val file = Path.of("/nonexistent/file.txt")
        val error =
            assertFailsWith<AssertionError> {
                assertThat(file).lines()
            }
        assertTrue(error.message!!.contains("expected file to exist") || error.message!!.contains("lines"))
    }
}
