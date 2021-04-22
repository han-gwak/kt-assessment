package domain

import java.math.BigDecimal
import java.time.Instant
import java.util.*

data class Order(
    val total: BigDecimal,
    val items: List<Item>,
    val status: OrderStatus = OrderStatus.RECEIVED,
    val deliveryTime: Instant? = null
) {
    val id: UUID = UUID.randomUUID()
}