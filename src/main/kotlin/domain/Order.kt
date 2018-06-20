package domain

/**
 * @author Yannik Inniger
 */
data class Order(
        val deliveryAddress: Address,
        val invoiceAddress: Address,
        val kvs: Number,
        val dn: String,
        val quantity: Int
) {
    init {
        if (!dn.toLowerCase().contains("dn")) {
            throw IllegalArgumentException("invalid dn")
        }
        if (quantity < 0) {
            throw IllegalArgumentException("invalid dn")
        }
    }
}
