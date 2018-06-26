package builder

import Mailer
import domain.Address
import domain.Order

class Builder {

    companion object {

        private val userName = System.getenv("ZOHO_USER_NAME") ?: ""
        private val password = System.getenv("ZOHO_PASSWORD") ?: ""

        fun buildAddress(company: String = "test", project: String? = null,
                         street: String = "teststreet", city: String = "testcity",
                         zip: String = "1000")
                : Address {
            return Address(company, project, street, city, zip)
        }

        fun buildOrder(deliveryAddress: Address = buildAddress(), invoiceAddress: Address = buildAddress(),
                       kvs: String = "1.6", dn: String = "DN15", quantity: Int = 1, price: Int = 1,
                       articleNumber: String = "test", mailAddress: String = userName
                       ): Order {
            return Order(deliveryAddress, invoiceAddress, kvs, dn, quantity, price, articleNumber, mailAddress)
        }

        fun buildMailer(): Mailer {
            if (userName == "" || password == "") {
                System.err.println("missing arguments")
                System.exit(1)
            }
            return Mailer(userName, password)
        }

    }
}
