import com.beust.klaxon.Klaxon
import com.beust.klaxon.KlaxonException
import domain.*
import spark.kotlin.before
import spark.kotlin.post
import java.util.logging.Logger

val klaxon = Klaxon()
val logger: Logger = Logger.getLogger("Server")

fun main(args: Array<String>) {

    val mailer = Mailer()
    val orderEmail: String = System.getenv("ORDER_EMAIL") ?: Mailer.userName

    // To work with CORS
    before { response.header("Access-Control-Allow-Origin", "*") }

    post("/submit") {
        val orderJson = request.body()
        try {
            val order = klaxon.parse<Order>(orderJson)
            logger.info("recieved order $order")
            if (order != null) {
                mailer.sendEmail(orderEmail, "Neue Bestellung bei HLK Components", order, vendorTemplate)
                if (order.sendCopy) {
                    mailer.sendEmail(order.mailAddress, "Kopie Ihrer Bestellung", order, customerTemplate)
                }
            }
            response.status(200)
            response.body(klaxon.toJsonString(order as Any))
            response.type("application/json")
        } catch (e: KlaxonException) {
            logger.warning("unable to parse order $e")
            response.status(400)
            response.body("could not parse order")
        } catch (e: IllegalArgumentException) {
            logger.info(e.message)
            response.status(400)
            response.body(e.message)
        } catch (e: Exception) {
            logger.severe(e.toString())
        }
        return@post response.body()
    }

}
