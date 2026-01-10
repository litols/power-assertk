package powerassertk

import java.io.File
import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class FileTest {
    // Helper to create temp file/directory
    private fun createTempFile(
        content: String = "test content",
        prefix: String = "test",
        suffix: String = ".txt",
    ): File {
        val file = File.createTempFile(prefix, suffix)
        file.deleteOnExit()
        file.writeText(content)
        return file
    }

    private fun createTempDirectory(prefix: String = "testdir"): File {
        val dir = kotlin.io.path.createTempDirectory(prefix).toFile()
        dir.deleteOnExit()
        return dir
    }

    // exists tests
    @Test
    fun exists_succeeds_when_file_exists() {
        val file = createTempFile()
        assertThat(file).exists()
    }

    @Test
    fun exists_fails_when_file_does_not_exist() {
        data class FileContainer(val file: File)
        val container = FileContainer(File("/nonexistent/path/file.txt"))

        val error =
            assertFailsWith<AssertionError> {
                assertThat(container.file).exists()
            }
        val message = error.message!!

        // File toString() is platform-dependent, so we verify key parts
        assertTrue(
            message.contains("assertThat(container.file).exists()"),
            "Should show assertion expression"
        )
        assertTrue(
            message.contains("FileContainer"),
            "Should show FileContainer"
        )
        assertTrue(
            message.contains("nonexistent"),
            "Should show the file path"
        )
    }

    @Test
    fun exists_supports_custom_message() {
        val file = File("/nonexistent/path/file.txt")
        val error =
            assertFailsWith<AssertionError> {
                assertThat(file).exists { "Custom: file should exist" }
            }
        assertTrue(error.message!!.contains("Custom: file should exist"))
    }

    // isDirectory tests
    @Test
    fun isDirectory_succeeds_when_is_directory() {
        val dir = createTempDirectory()
        assertThat(dir).isDirectory()
    }

    @Test
    fun isDirectory_fails_when_is_file() {
        data class FileContainer(val file: File)
        val file = createTempFile()
        val container = FileContainer(file)

        val error =
            assertFailsWith<AssertionError> {
                assertThat(container.file).isDirectory()
            }
        val message = error.message!!

        // File toString() is platform-dependent, so we verify key parts
        assertTrue(
            message.contains("assertThat(container.file).isDirectory()"),
            "Should show assertion expression"
        )
        assertTrue(
            message.contains("FileContainer"),
            "Should show FileContainer"
        )
        assertTrue(
            message.contains(file.name),
            "Should show the file name"
        )
    }

    @Test
    fun isDirectory_supports_custom_message() {
        val file = createTempFile()
        val error =
            assertFailsWith<AssertionError> {
                assertThat(file).isDirectory { "Custom: should be directory" }
            }
        assertTrue(error.message!!.contains("Custom: should be directory"))
    }

    // isFile tests
    @Test
    fun isFile_succeeds_when_is_file() {
        val file = createTempFile()
        assertThat(file).isFile()
    }

    @Test
    fun isFile_fails_when_is_directory() {
        data class FileContainer(val file: File)
        val dir = createTempDirectory()
        val container = FileContainer(dir)

        val error =
            assertFailsWith<AssertionError> {
                assertThat(container.file).isFile()
            }
        val message = error.message!!

        // File toString() is platform-dependent, so we verify key parts
        assertTrue(
            message.contains("assertThat(container.file).isFile()"),
            "Should show assertion expression"
        )
        assertTrue(
            message.contains("FileContainer"),
            "Should show FileContainer"
        )
        assertTrue(
            message.contains(dir.name),
            "Should show the directory name"
        )
    }

    @Test
    fun isFile_supports_custom_message() {
        val dir = createTempDirectory()
        val error =
            assertFailsWith<AssertionError> {
                assertThat(dir).isFile { "Custom: should be file" }
            }
        assertTrue(error.message!!.contains("Custom: should be file"))
    }

    // isHidden tests
    @Test
    fun isHidden_succeeds_when_is_hidden() {
        val dir = createTempDirectory()
        val hiddenFile = File(dir, ".hidden")
        hiddenFile.createNewFile()
        hiddenFile.deleteOnExit()
        if (hiddenFile.isHidden) {
            assertThat(hiddenFile).isHidden()
        }
        // On some platforms, files starting with "." may not be considered hidden
    }

    @Test
    fun isHidden_fails_when_is_not_hidden() {
        data class FileContainer(val file: File)
        val file = createTempFile()
        val container = FileContainer(file)

        if (!file.isHidden) {
            val error =
                assertFailsWith<AssertionError> {
                    assertThat(container.file).isHidden()
                }
            val message = error.message!!

            // File toString() is platform-dependent, so we verify key parts
            assertTrue(
                message.contains("assertThat(container.file).isHidden()"),
                "Should show assertion expression"
            )
            assertTrue(
                message.contains("FileContainer"),
                "Should show FileContainer"
            )
            assertTrue(
                message.contains(file.name),
                "Should show the file name"
            )
        }
    }

    // isNotHidden tests
    @Test
    fun isNotHidden_succeeds_when_is_not_hidden() {
        val file = createTempFile()
        if (!file.isHidden) {
            assertThat(file).isNotHidden()
        }
    }

    @Test
    fun isNotHidden_fails_when_is_hidden() {
        data class FileContainer(val file: File)
        val dir = createTempDirectory()
        val hiddenFile = File(dir, ".hidden")
        hiddenFile.createNewFile()
        hiddenFile.deleteOnExit()
        val container = FileContainer(hiddenFile)

        if (hiddenFile.isHidden) {
            val error =
                assertFailsWith<AssertionError> {
                    assertThat(container.file).isNotHidden()
                }
            val message = error.message!!

            // File toString() is platform-dependent, so we verify key parts
            assertTrue(
                message.contains("assertThat(container.file).isNotHidden()"),
                "Should show assertion expression"
            )
            assertTrue(
                message.contains("FileContainer"),
                "Should show FileContainer"
            )
            assertTrue(
                message.contains(".hidden"),
                "Should show the hidden file name"
            )
        }
    }

    // hasName tests
    @Test
    fun hasName_succeeds_when_name_matches() {
        val file = createTempFile(prefix = "mytest", suffix = ".txt")
        assertThat(file).hasName(file.name)
    }

    @Test
    fun hasName_fails_when_name_does_not_match() {
        data class FileContainer(val file: File)
        val file = createTempFile()
        val container = FileContainer(file)

        val error =
            assertFailsWith<AssertionError> {
                assertThat(container.file).hasName("different.txt")
            }
        val message = error.message!!

        // File toString() is platform-dependent, so we verify key parts
        assertTrue(
            message.contains("assertThat(container.file).hasName(\"different.txt\")"),
            "Should show assertion expression"
        )
        assertTrue(
            message.contains("FileContainer"),
            "Should show FileContainer"
        )
        assertTrue(
            message.contains(file.name),
            "Should show the actual file name"
        )
    }

    @Test
    fun hasName_supports_custom_message() {
        val file = createTempFile()
        val error =
            assertFailsWith<AssertionError> {
                assertThat(file).hasName("different.txt") { "Custom: wrong name" }
            }
        assertTrue(error.message!!.contains("Custom: wrong name"))
    }

    // name tests
    @Test
    fun name_returns_file_name() {
        val file = createTempFile(prefix = "mytest", suffix = ".txt")
        val nameAssert = assertThat(file).name()
        assertTrue(nameAssert.actual.endsWith(".txt"))
    }

    // hasPath tests
    @Test
    fun hasPath_succeeds_when_path_matches() {
        val file = createTempFile()
        assertThat(file).hasPath(file.path)
    }

    @Test
    fun hasPath_fails_when_path_does_not_match() {
        data class FileContainer(val file: File)
        val file = createTempFile()
        val container = FileContainer(file)

        val error =
            assertFailsWith<AssertionError> {
                assertThat(container.file).hasPath("/different/path.txt")
            }
        val message = error.message!!

        // File toString() is platform-dependent, so we verify key parts
        assertTrue(
            message.contains("assertThat(container.file).hasPath(\"/different/path.txt\")"),
            "Should show assertion expression"
        )
        assertTrue(
            message.contains("FileContainer"),
            "Should show FileContainer"
        )
        assertTrue(
            message.contains(file.name),
            "Should show the file name"
        )
    }

    @Test
    fun hasPath_supports_custom_message() {
        val file = createTempFile()
        val error =
            assertFailsWith<AssertionError> {
                assertThat(file).hasPath("/different/path.txt") { "Custom: wrong path" }
            }
        assertTrue(error.message!!.contains("Custom: wrong path"))
    }

    // path tests
    @Test
    fun path_returns_file_path() {
        val file = createTempFile()
        val pathAssert = assertThat(file).path()
        assertTrue(pathAssert.actual.isNotEmpty())
    }

    // hasParent tests
    @Test
    fun hasParent_succeeds_when_parent_matches() {
        val file = createTempFile()
        val parent = file.parent
        if (parent != null) {
            assertThat(file).hasParent(parent)
        }
    }

    @Test
    fun hasParent_fails_when_parent_does_not_match() {
        data class FileContainer(val file: File)
        val file = createTempFile()
        val container = FileContainer(file)

        val error =
            assertFailsWith<AssertionError> {
                assertThat(container.file).hasParent("/different/parent")
            }
        val message = error.message!!

        // File toString() is platform-dependent, so we verify key parts
        assertTrue(
            message.contains("assertThat(container.file).hasParent(\"/different/parent\")"),
            "Should show assertion expression"
        )
        assertTrue(
            message.contains("FileContainer"),
            "Should show FileContainer"
        )
        assertTrue(
            message.contains(file.name),
            "Should show the file name"
        )
    }

    @Test
    fun hasParent_supports_custom_message() {
        val file = createTempFile()
        val error =
            assertFailsWith<AssertionError> {
                assertThat(file).hasParent("/different/parent") { "Custom: wrong parent" }
            }
        assertTrue(error.message!!.contains("Custom: wrong parent"))
    }

    // parent tests
    @Test
    fun parent_returns_file_parent() {
        val file = createTempFile()
        val parentAssert = assertThat(file).parent()
        assertTrue(parentAssert.actual != null)
    }

    // hasExtension tests
    @Test
    fun hasExtension_succeeds_when_extension_matches() {
        val file = createTempFile(suffix = ".txt")
        assertThat(file).hasExtension("txt")
    }

    @Test
    fun hasExtension_fails_when_extension_does_not_match() {
        data class FileContainer(val file: File)
        val file = createTempFile(suffix = ".txt")
        val container = FileContainer(file)

        val error =
            assertFailsWith<AssertionError> {
                assertThat(container.file).hasExtension("md")
            }
        val message = error.message!!

        // File toString() is platform-dependent, so we verify key parts
        assertTrue(
            message.contains("assertThat(container.file).hasExtension(\"md\")"),
            "Should show assertion expression"
        )
        assertTrue(
            message.contains("FileContainer"),
            "Should show FileContainer"
        )
        assertTrue(
            message.contains(file.name),
            "Should show the file name"
        )
    }

    @Test
    fun hasExtension_supports_custom_message() {
        val file = createTempFile(suffix = ".txt")
        val error =
            assertFailsWith<AssertionError> {
                assertThat(file).hasExtension("md") { "Custom: wrong extension" }
            }
        assertTrue(error.message!!.contains("Custom: wrong extension"))
    }

    // extension tests
    @Test
    fun extension_returns_file_extension() {
        val file = createTempFile(suffix = ".txt")
        val extAssert = assertThat(file).extension()
        assertTrue(extAssert.actual == "txt")
    }

    // hasDirectChild tests
    @Test
    fun hasDirectChild_succeeds_when_child_exists() {
        val dir = createTempDirectory()
        val child = File(dir, "child.txt")
        child.createNewFile()
        child.deleteOnExit()
        assertThat(dir).hasDirectChild(child)
    }

    @Test
    fun hasDirectChild_fails_when_child_does_not_exist() {
        data class FileContainer(val file: File)
        val dir = createTempDirectory()
        val nonExistentChild = File(dir, "nonexistent.txt")
        val container = FileContainer(dir)

        val error =
            assertFailsWith<AssertionError> {
                assertThat(container.file).hasDirectChild(nonExistentChild)
            }
        val message = error.message!!

        // File toString() is platform-dependent, so we verify key parts
        assertTrue(
            message.contains("assertThat(container.file).hasDirectChild(nonExistentChild)"),
            "Should show assertion expression"
        )
        assertTrue(
            message.contains("FileContainer"),
            "Should show FileContainer"
        )
        assertTrue(
            message.contains(dir.name),
            "Should show the directory name"
        )
    }

    @Test
    fun hasDirectChild_fails_when_not_directory() {
        val file = createTempFile()
        val child = File(file, "child.txt")
        val error =
            assertFailsWith<AssertionError> {
                assertThat(file).hasDirectChild(child)
            }
        // This error is thrown internally, not via Power Assert
        assertTrue(
            error.message!!.contains("expected to be a directory") || error.message!!.contains("hasDirectChild"),
        )
    }

    @Test
    fun hasDirectChild_supports_custom_message() {
        val dir = createTempDirectory()
        val nonExistentChild = File(dir, "nonexistent.txt")
        val error =
            assertFailsWith<AssertionError> {
                assertThat(dir).hasDirectChild(nonExistentChild) { "Custom: missing child" }
            }
        assertTrue(error.message!!.contains("Custom: missing child"))
    }

    // text tests
    @Test
    fun text_returns_file_content() {
        val file = createTempFile(content = "Hello World")
        val textAssert = assertThat(file).text()
        assertTrue(textAssert.actual == "Hello World")
    }

    @Test
    fun text_fails_when_file_does_not_exist() {
        val file = File("/nonexistent/file.txt")
        val error =
            assertFailsWith<AssertionError> {
                assertThat(file).text()
            }
        assertTrue(error.message!!.contains("expected file to exist"))
    }

    // bytes tests
    @Test
    fun bytes_returns_file_bytes() {
        val content = "Hello World"
        val file = createTempFile(content = content)
        val bytesAssert = assertThat(file).bytes()
        assertTrue(bytesAssert.actual.contentEquals(content.toByteArray()))
    }

    @Test
    fun bytes_fails_when_file_does_not_exist() {
        val file = File("/nonexistent/file.txt")
        val error =
            assertFailsWith<AssertionError> {
                assertThat(file).bytes()
            }
        assertTrue(error.message!!.contains("expected file to exist"))
    }

    // hasText tests
    @Test
    fun hasText_succeeds_when_text_matches() {
        val content = "Hello World"
        val file = createTempFile(content = content)
        assertThat(file).hasText(content)
    }

    @Test
    fun hasText_fails_when_text_does_not_match() {
        data class FileContainer(val file: File)
        val file = createTempFile(content = "Hello World")
        val container = FileContainer(file)

        val error =
            assertFailsWith<AssertionError> {
                assertThat(container.file).hasText("Different")
            }
        val message = error.message!!

        // File toString() is platform-dependent, so we verify key parts
        assertTrue(
            message.contains("assertThat(container.file).hasText(\"Different\")"),
            "Should show assertion expression"
        )
        assertTrue(
            message.contains("FileContainer"),
            "Should show FileContainer"
        )
        assertTrue(
            message.contains(file.name),
            "Should show the file name"
        )
    }

    @Test
    fun hasText_fails_when_file_does_not_exist() {
        val file = File("/nonexistent/file.txt")
        val error =
            assertFailsWith<AssertionError> {
                assertThat(file).hasText("content")
            }
        // This error is thrown internally, not via Power Assert
        assertTrue(error.message!!.contains("expected file to exist") || error.message!!.contains("hasText"))
    }

    @Test
    fun hasText_supports_custom_message() {
        val file = createTempFile(content = "Hello World")
        val error =
            assertFailsWith<AssertionError> {
                assertThat(file).hasText("Different") { "Custom: wrong content" }
            }
        assertTrue(error.message!!.contains("Custom: wrong content"))
    }
}
