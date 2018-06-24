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
        val mailAddress: String
) {
    init {
        if (!dn.toLowerCase().contains("dn")) {
            throw IllegalArgumentException("invalid dn")
        } else if (quantity < 0) {
            throw IllegalArgumentException("quantity must be positive")
        } else if (!mailAddress.isEmailValid()) {
            throw IllegalArgumentException("email is invalid")
        }
    }

    private fun String.isEmailValid(): Boolean {
        return Pattern.compile(
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]|[\\w-]{2,}))@"
                        + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                        + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9]))|"
                        + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$"
        ).matcher(this).matches()
    }

}
