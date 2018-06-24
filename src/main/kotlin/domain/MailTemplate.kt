package domain

import org.celtric.kotlin.html.*

/**
 * @author Yannik Inniger
 */
fun fillTemplate(order: Order): String {
    val tableStyle = mapOf("style" to "padding-right: 20px")
    return div {
        h2("Vielen Dank für Ihre Bestellung bei Sauter") +
        p("Hier senden wir Ihnen eine Kopie Ihrer Bestellung:") +
            table {
            tr {
                th(other = tableStyle){ "Rechnungsadresse" } +
                th("Lieferadresse")
            } +
            tr {
                td(other = tableStyle){ order.deliveryAddress.company } +
                td(order.invoiceAddress.company)
            } +
            tr {
                td(other = tableStyle){ order.deliveryAddress.street } +
                td(order.invoiceAddress.street)
            } +
            tr {
                td(other = tableStyle) { order.deliveryAddress.zip + " " + order.deliveryAddress.city } +
                td(order.invoiceAddress.zip + " " + order.invoiceAddress.city)
            }
        } +
        br() +
        h4("Bestellte Produkte") +
        p("${order.quantity}x Heizungsregler EQJW126F001") +
        p("${order.quantity}x Aussentemperatur-Fühler EGT301F102") +
        p("${order.quantity}x Vorlauftemperatur-Fühler EGT311F102") +
        p("${order.quantity}x Normschema") +
        div("${order.quantity}x ${order.articleNumber}") +
        div("DN: ${order.dn}") +
        div("KVS: ${order.kvs}") +
        div("Preis: ${order.price}")
    }.render()
}