package powerassertk

class Assert<T> @PublishedApi internal constructor(val actual: T)

fun <T> assertThat(actual: T): Assert<T> = Assert(actual)
