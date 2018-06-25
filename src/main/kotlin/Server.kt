import com.beust.klaxon.Klaxon
import com.beust.klaxon.KlaxonException
import domain.Order
import spark.kotlin.post
import java.util.logging.Logger

val klaxon = Klaxon()
val logger: Logger = Logger.getLogger("Server")

fun main(args: Array<String>) {

    val mailer: Mailer = initMailer()

    post("/order") {
        val orderJson = request.body()
        try {
            val order = klaxon.parse<Order>(orderJson)
            logger.info("recieved order $order")
            if (order != null) {
                mailer.sendEmail(order.mailAddress, "Kopie Ihrer Bestellung", order)
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
        }
        return@post response.body()
    }

}

private fun initMailer(): Mailer {
    val userName = System.getenv("ZOHO_USER_NAME")
    val password = System.getenv("ZOHO_PASSWORD")
    if (userName == null || password == null) {
        System.err.println("missing arguments")
        System.exit(1)
    }
    return Mailer(userName, password)
}
