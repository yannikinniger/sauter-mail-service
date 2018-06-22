package domain

/**
 * @author Yannik Inniger
 */
data class Address(
        val company: String,
        val project: String?,
        val street: String,
        val city: String,
        val zip: Int
) {

    constructor(
            company: String,
            street: String,
            city: String,
            zip: Int
    ) : this(company, null, street, city, zip)

}

