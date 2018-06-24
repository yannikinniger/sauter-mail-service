package domain

/**
 * @author Yannik Inniger
 */
data class Address(
        val company: String,
        val project: String? = null,
        val street: String,
        val city: String,
        val zip: String
)

