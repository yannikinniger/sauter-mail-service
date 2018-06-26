package domain

import org.celtric.kotlin.html.*

/**
 * @author Yannik Inniger
 */
fun fillCustomerTemplate(order: Order): String {
    return div {
        h2("Vielen Dank für Ihre Bestellung bei Sauter") +
                p("Hier senden wir Ihnen eine Kopie Ihrer Bestellung:") +
                fillAddresses(order) +
                fillOrder(order)
    }.render()
}

fun fillOrderTemplate(order: Order): String {
    return div {
        h2("Neue Bestellung bei HLK Components") +
                fillAddresses(order) +
                p("Email: ${order.mailAddress}") +
                fillOrder(order)
    }.render()
}

private fun fillOrder(order: Order): BlockElement {
    return div {
        h4("Bestellte Produkte") +
                p("${order.quantity}x Heizungsregler EQJW126F001") +
                p("${order.quantity}x Aussentemperatur-Fühler EGT301F102") +
                p("${order.quantity}x Vorlauftemperatur-Fühler EGT311F102") +
                p("${order.quantity}x Normschema") +
                div("${order.quantity}x ${order.articleNumber}") +
                div("DN: ${order.dn}") +
                div("KVS: ${order.kvs}") +
                div("Preis: ${order.price}")
    }
}

private fun fillAddresses(order: Order): BlockElement {
    val tableStyle = mapOf("style" to "padding-left: 20px")
    return table {
        tr {
            th("Rechnungsadresse") +
                    th(other = tableStyle) { "Lieferadresse" }
        } +
                tr {
                    td(order.deliveryAddress.company) +
                            td(other = tableStyle) { order.invoiceAddress.company }
                } +
                if (order.deliveryAddress.project != null || order.invoiceAddress.project != null) {
                    tr {
                        td(order.deliveryAddress.project ?: "") +
                                td(other = tableStyle) { order.invoiceAddress.project ?: "" }
                    }
                } else {
                    span {  }
                } +
                tr {
                    td(order.deliveryAddress.street) +
                            td(other = tableStyle) { order.invoiceAddress.street }
                } +
                tr {
                    td(order.deliveryAddress.zip + " " + order.deliveryAddress.city) +
                            td(other = tableStyle) { order.invoiceAddress.zip + " " + order.invoiceAddress.city }
                }
    }
}
