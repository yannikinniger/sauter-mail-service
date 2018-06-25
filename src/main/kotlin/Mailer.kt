import domain.Order
import domain.fillTemplate
import kotlinx.coroutines.experimental.launch
import org.apache.commons.mail.DefaultAuthenticator
import org.apache.commons.mail.HtmlEmail
import java.util.logging.Logger
import java.util.regex.Pattern

/**
 * @author Yannik Inniger
 */
class Mailer(
        private val userName: String,
        private val password: String
) {

    val logger: Logger = Logger.getLogger(this.javaClass.toString())

    fun sendEmail(recipient: String, subject: String, order: Order) {
        if (recipient.isEmailValid()) {
            launch {
                createEmail(recipient, subject)
                        .setHtmlMsg(fillTemplate(order))
                        .send()
                logger.info("successfully sent to $recipient")
            }
        } else {
            throw IllegalArgumentException("invalid email address")
        }
    }

    private fun String.isEmailValid(): Boolean {
        return Pattern.compile(
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]|[\\w-]{2,}))@"
                        + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                        + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9]))|"
                        + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$"
        ).matcher(this).matches()
    }

    private fun createEmail(recipient: String, subject: String): HtmlEmail {
        return HtmlEmail().apply {
            hostName = host
            setSmtpPort(port)
            setAuthenticator(DefaultAuthenticator(userName, password))
            isSSLOnConnect = true
            setFrom(userName)
            setSubject(subject)
            addTo(recipient)
        }
    }

    companion object {
        private const val host = "smtp.zoho.com"
        private const val port = 465
    }

}
