import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import domain.Address
import domain.Order
import org.junit.Assert.*
import org.junit.Test

/**
 * @author Yannik Inniger
 */
class MailerTest {

    private val address = Address("Test AG", null, "Teststrasse", "Test City", 1000)
    private val order = Order(address, address, 1, "DN10", 1, 1, "article", "test@test.ch")

    @Test
    fun shouldThrowExceptionOnInvalidEmail() {
        val mailer = initMailer()

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
        val mailer = initMailer()
        val mailAddress = System.getenv("ZOHO_USER_NAME")
        val result = mailer.sendEmail(mailAddress, "subject", order)
        assertTrue(result)
    }

}
