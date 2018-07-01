package builder

import Mailer
import domain.Address
import domain.Order

class Builder {

    companion object {

        fun buildAddress(company: String = "test", project: String? = null,
                         street: String = "teststreet", city: String = "testcity",
                         zip: String = "1000")
                : Address {
            return Address(company, project, street, city, zip)
        }

        fun buildOrder(project: String? = "project", reference: String? = "reference",
                       deliveryAddress: Address = buildAddress(), invoiceAddress: Address = buildAddress(),
                       kvs: String = "1.6", dn: String = "DN15", quantity: Int = 1, price: Int = 1,
                       articleNumber: String = "test", mailAddress: String = Mailer.userName
        ): Order {
            return Order(project, reference, deliveryAddress, invoiceAddress, kvs, dn, quantity, price, articleNumber, mailAddress)
        }

        fun buildMailer(): Mailer {
            return Mailer()
        }

    }
}
