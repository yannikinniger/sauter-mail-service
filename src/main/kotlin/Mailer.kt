import kotlinx.coroutines.experimental.launch
import org.apache.commons.mail.DefaultAuthenticator
import org.apache.commons.mail.HtmlEmail
import java.util.logging.Logger
import java.util.regex.Pattern

/**
 * @author Yannik Inniger
 */
class Mailer {

    val logger: Logger = Logger.getLogger(this.javaClass.toString())

    fun sendEmail(recipient: String, subject: String, message: String) {
        if (recipient.isEmailValid()) {
            launch {
                createEmail(recipient, subject)
                        .setHtmlMsg(message)
                        .send()
                logger.info("successfully sent to $recipient")
            }
        } else {
            throw IllegalArgumentException("invalid email address")
        }
    }

    private fun createEmail(recipient: String, subject: String): HtmlEmail {
        return HtmlEmail().apply {
            hostName = host
            setSmtpPort(port)
            setAuthenticator(DefaultAuthenticator(userName, password))
            isSSLOnConnect = true
            if (replyTo != null) { addReplyTo(replyTo) }
            setFrom(userName)
            setSubject(subject)
            addTo(recipient)
        }
    }

    companion object {
        private val host: String = System.getenv("SMTP_HOST")
                ?: throw IllegalStateException("SMTP_HOST is not specified")
        private val port: Int = System.getenv("SMTP_PORT")?.toInt() ?: 465
        val userName = System.getenv("SMTP_USER_NAME")
                ?: throw IllegalStateException("SMTP_USER_NAME is not specified")
        private val password = System.getenv("SMTP_PASSWORD")
                ?: throw IllegalStateException("SMTP_PASSWORD is not specified")
        private val replyTo: String? = System.getenv("REPLY_TO_ADDRESS")

        init {
            if (replyTo != null && !replyTo.isEmailValid()) {
                throw IllegalArgumentException("invalid reply to address")
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
    }

}
