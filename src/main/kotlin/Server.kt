import com.beust.klaxon.Klaxon
import com.beust.klaxon.KlaxonException
import domain.Order
import spark.kotlin.post

val klaxon = Klaxon()

fun main(args: Array<String>) {
    post("/send-mail") {
        val orderJson = request.body()
        try {
            val order = klaxon.parse<Order>(orderJson)
            println(order)
        } catch (e: KlaxonException) {
            response.status(400)
            response.body("could not parse order")
        }
    }
}
