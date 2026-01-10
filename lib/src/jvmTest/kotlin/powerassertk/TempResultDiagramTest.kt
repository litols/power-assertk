package powerassertk

import kotlin.test.Test

class TempResultDiagramTest {
    @Test
    fun test_variable_expansion() {
        val result: Result<Int> = Result.failure(RuntimeException("error"))
        assertThat(result).isSuccess()
    }

    @Test
    fun test_property_chain() {
        data class Response(val result: Result<Int>)
        val response = Response(Result.failure(RuntimeException("error")))
        assertThat(response.result).isSuccess()
    }

    @Test
    fun test_isFailure_with_success() {
        val successResult: Result<Int> = Result.success(42)
        assertThat(successResult).isFailure()
    }
}
