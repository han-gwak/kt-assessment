package service

import domain.Apple
import domain.Orange
import domain.Order
import domain.OrderStatus
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import org.assertj.core.api.KotlinAssertions.assertThat
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.time.Instant

class CustomerNotificationServiceTest {

    private val customerNotificationService = CustomerNotificationServiceImpl()

    @Test
    fun `test successful order notification`() {
        val itemsList = listOf(Orange(), Orange(), Apple(), Apple())
        val order = Order(BigDecimal.valueOf(1.10), itemsList, OrderStatus.OUT_FOR_DELIVERY, Instant.now().plusSeconds(900))
        val result = customerNotificationService.notifyCustomerOrderSuccess(order)
        assertThat(result).contains("has status: OUT_FOR_DELIVERY\nEstimated delivery time:")
    }

    @Test
    fun `test out of stock order notification`() {
        var itemsList = listOf(Orange(), Orange(), Apple().apply { outOfStock = true})
        var order = Order(BigDecimal.ZERO, itemsList, OrderStatus.ITEMS_OUT_OF_STOCK)
        var result = customerNotificationService.notifyCustomerOrderFailed(order)
        assertThat(result).endsWith("could not be completed as the following items are out of stock: [apple]")

        // multiple items out of stock
        itemsList = listOf(Orange().apply { outOfStock = true }, Apple().apply { outOfStock = true})
        order = Order(BigDecimal.ZERO, itemsList, OrderStatus.ITEMS_OUT_OF_STOCK)
        result = customerNotificationService.notifyCustomerOrderFailed(order)
        assertThat(result).endsWith("could not be completed as the following items are out of stock: [orange, apple]")
    }

}