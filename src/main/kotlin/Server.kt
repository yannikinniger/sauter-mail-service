import com.beust.klaxon.Klaxon
import com.beust.klaxon.KlaxonException
import domain.Order
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import spark.kotlin.post

val klaxon = Klaxon()
val logger: Logger = LoggerFactory.getLogger("Server")
val mailer = Mailer()

fun main(args: Array<String>) {

    post("/order") {
        val orderJson = request.body()
        try {
            val order = klaxon.parse<Order>(orderJson)
            var emailSent = false
            if (order != null) {
                emailSent = mailer.sendEmail(order.mailAddress, "Kopie Ihrer Bestellung", order)
                response.status(200)
                response.body(klaxon.toJsonString(order as Any))
                response.type("application/json")
            }
            if (!emailSent) {
                response.status(400)
                response.body("could not parse order")
            }
        } catch (e: KlaxonException) {
            logger.warn("unable to parse order $orderJson")
            response.status(400)
            response.body("could not parse order")
        }
        return@post response.body()
    }

}
