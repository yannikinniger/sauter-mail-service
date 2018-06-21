import domain.Order
import domain.fillTemplate
import org.apache.commons.mail.DefaultAuthenticator
import org.apache.commons.mail.EmailException
import org.apache.commons.mail.HtmlEmail
import java.util.regex.Pattern

/**
 * @author Yannik Inniger
 */
class Mailer {

    fun sendEmail(recipient: String, subject: String, order: Order): Boolean {
        if (recipient.isEmailValid()) {
            try {
                createEmail(recipient, subject)
                        .setHtmlMsg(fillTemplate(order))
                        .send()
            } catch (e: EmailException) {
                return false
            }
            return true
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
            setFrom(sendingAddress)
            setSubject(subject)
            addTo(recipient)
        }
    }


    companion object {
        private const val host = "smtp.zoho.com"
        private const val port = 465
        private const val sendingAddress = "order@hlk-components.ch"
        private val userName = System.getenv("ZOHO_USER_NAME") ?: ""
        private val password = System.getenv("ZOHO_PASSWORD") ?: ""
    }

}
