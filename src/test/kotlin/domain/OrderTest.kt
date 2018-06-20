package domain

import org.junit.Test
import java.lang.IllegalArgumentException

/**
 * @author Yannik Inniger
 */
internal class OrderTest {

    private val address = Address("Test AG", null, "Teststrasse", "Test City", 1000)

    @Test(expected = IllegalArgumentException::class)
    fun shouldThrowExceptionOnNegativeQuantity() {
        val quantity = -1
        Order(address, address, 1, "DN20", quantity, 1, "article")
    }

    @Test(expected = IllegalArgumentException::class)
    fun shouldThrowExceptionOnInvalidDn() {
        val dn = "test"
        Order(address, address, 1, dn, 1)
    }

}
