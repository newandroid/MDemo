package css.com.applab

import org.junit.Test

class BaseTest {
    @Test
    fun hoh() {
        println("hello")
    }

    @Test
    fun jjj(x: Int): Int {
        return 2 * x + 1
    }
}