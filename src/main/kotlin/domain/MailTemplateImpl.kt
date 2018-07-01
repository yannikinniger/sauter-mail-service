package domain

import org.celtric.kotlin.html.*

/**
 * @author Yannik Inniger
 */

val customerTemplate: MailTemplate = { order ->
    div {
        h2("Vielen Dank für Ihre Bestellung bei Sauter") +
                p("Hier senden wir Ihnen eine Kopie Ihrer Bestellung:") +
                fillAddresses(order) +
                fillOrder(order)
    }.render()
}

val vendorTemplate: MailTemplate = { order ->
    div {
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
    return div {
        if (order.project != null) {
            p("Projekt: ${order.project}")
        } else { span {} } +
                if (order.reference != null) {
                    p("Referenz: ${order.reference}")
                } else { span {} } +
                table {
                    tr {
                        th("Rechnungsadresse") +
                                th(other = tableStyle) { "Lieferadresse" }
                    } +
                            tr {
                                td(order.deliveryAddress.company) +
                                        td(other = tableStyle) { order.invoiceAddress.company }
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
}