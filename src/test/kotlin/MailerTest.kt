import builder.Builder
import domain.customerTemplate
import org.junit.Assert.fail
import org.junit.Test

/**
 * @author Yannik Inniger
 */
class MailerTest {

    private val order = Builder.buildOrder()

    @Test
    fun shouldThrowExceptionOnInvalidEmail() {
        val mailer = Builder.buildMailer()

        try {
            val mailAddress = "invalid@mail"
            mailer.sendEmail(mailAddress, "subject", order, customerTemplate)
            fail("email address $mailAddress was accepted")
        } catch (e: IllegalArgumentException) { /* expected exception */
        }

        try {
            val mailAddress = "invalid.ch"
            mailer.sendEmail(mailAddress, "subject", order, customerTemplate)
            fail("email address $mailAddress was accepted")
        } catch (e: IllegalArgumentException) { /* expected exception */
        }
    }

    @Test
    fun shouldSendEmailWithoutError() {
        val mailer = Builder.buildMailer()
        val mailAddress = order.mailAddress
        mailer.sendEmail(mailAddress, "subject", order, customerTemplate)
    }

}
