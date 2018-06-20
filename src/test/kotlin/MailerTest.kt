import domain.Address
import domain.Order
import org.junit.Assert.*
import org.junit.Test

/**
 * @author Yannik Inniger
 */
class MailerTest {

    private val address = Address("Test AG", null, "Teststrasse", "Test City", 1000)
    private val order = Order(address, address, 1, "DN10", 1, 1, "article")

    @Test
    fun shouldThrowExceptionOnInvalidEmail() {
        val mailer = Mailer()

        try {
            val mailAddress = "invalid@mail"
            mailer.sendEmail(mailAddress, "subject", order)
            fail("email address $mailAddress was accepted")
        } catch (e: IllegalArgumentException ) { /* expected exception */ }

        try {
            val mailAddress = "invalid.ch"
            mailer.sendEmail(mailAddress, "subject", order)
            fail("email address $mailAddress was accepted")
        } catch (e: IllegalArgumentException ) { /* expected exception */ }
    }

    @Test
    fun shouldSendEmailWithoutError() {
        val mailer = Mailer()
        val mailAddress = "yannik.inniger@gmail.com"
        val result = mailer.sendEmail(mailAddress, "subject", order)

        assertTrue(result)
    }

}
