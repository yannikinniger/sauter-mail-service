package domain

import java.util.regex.Pattern

/**
 * @author Yannik Inniger
 */
data class Order(
        val deliveryAddress: Address,
        val invoiceAddress: Address,
        val kvs: String,
        val dn: String,
        val quantity: Int,
        val price: Int,
        val articleNumber: String,
        val mailAddress: String,
        val sendCopy: Boolean = true
) {

    init {
        if (!dn.toLowerCase().contains("dn")) {
            throw IllegalArgumentException("invalid dn")
        } else if (quantity < 0) {
            throw IllegalArgumentException("quantity must be positive")
        }
    }

}
