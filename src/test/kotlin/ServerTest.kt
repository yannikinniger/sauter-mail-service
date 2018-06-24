import builder.Builder
import com.beust.klaxon.Klaxon
import com.despegar.http.client.PostMethod
import com.despegar.sparkjava.test.SparkServer
import domain.Order
import org.junit.Assert.assertEquals
import org.junit.Test
import spark.servlet.SparkApplication

class OrderTestServer : SparkApplication {
    override fun init() {
        main(arrayOf())
    }
}

val testServer: SparkServer<OrderTestServer> = SparkServer(OrderTestServer::class.java, 4567)

class ServerTest {

    private val order = Builder.buildOrder()
    private val klaxon = Klaxon()

    @Test
    fun shouldPostOrder() {
        val orderJson = klaxon.toJsonString(order as Any)

        val post: PostMethod = testServer.post("/order", orderJson, false)
        val httpResponse = testServer.execute(post)
        assertEquals(httpResponse.code(), 200)

        val returnedOrder = String(httpResponse.body())
        val result = klaxon.parse<Order>(returnedOrder)
        assertEquals(order, result)
    }

}