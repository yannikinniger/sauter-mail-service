package domain

import builder.Builder
import org.junit.Test
import java.lang.IllegalArgumentException

/**
 * @author Yannik Inniger
 */
internal class OrderTest {

    @Test(expected = IllegalArgumentException::class)
    fun shouldThrowExceptionOnNegativeQuantity() {
        val quantity = -1
        Builder.buildOrder(quantity = quantity)
    }

    @Test(expected = IllegalArgumentException::class)
    fun shouldThrowExceptionOnInvalidDn() {
        val dn = "test"
        Builder.buildOrder(dn = dn)
    }

}
