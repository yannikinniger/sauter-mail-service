import domain.Order
import org.apache.commons.mail.DefaultAuthenticator
import org.apache.commons.mail.EmailException
import org.apache.commons.mail.HtmlEmail
import org.celtric.kotlin.html.*
import java.util.regex.Pattern

/**
 * @author Yannik Inniger
 */
class Mailer {

    private val userName = System.getenv("USER_NAME") ?: ""
    private val password = System.getenv("PASSWORD") ?: ""

    fun sendEmail(recipient: String, subject: String, order: Order): Boolean {
        if (recipient.isEmailValid()) {
            try {
                createEmail(recipient, subject).send()
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

    private fun createMessage(order: Order): String {
        return div {
            h1("Vielen Dank für Ihre Bestellung")
                p("Hier sind ihre Daten zur Überprüfung")
            table {
                thead {
                    tr {
                        th("Rechnungsadresse")
                        th("Lieferadresse")
                    }
                    tr {
                        td(order.deliveryAddress.company)
                        td(order.invoiceAddress.company)
                    }
                    tr {
                        td(order.deliveryAddress.street)
                        td(order.invoiceAddress.street)
                    }
                    tr {
                        td(order.deliveryAddress.zip.toString() + " " + order.deliveryAddress.city)
                        td(order.invoiceAddress.zip.toString() + " " + order.invoiceAddress.city)
                    }
                }
            }
        }.render()
    }

    companion object {
        const val host = "smtp.zoho.com"
        const val port = 465
        const val sendingAddress = "order@hlk-components.ch"
    }

}

fun main(args: Array<String>) {
    Mailer()
}
