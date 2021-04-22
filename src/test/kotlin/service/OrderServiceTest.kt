package service

import org.assertj.core.api.KotlinAssertions.assertThat
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class OrderServiceTest {

    private val orderService = OrderServiceImpl(null)

    @Test
    fun `test calculate order`() {
        var itemNames = listOf("appLE", "ApPlE", "ORANGE", "apple")
        var orderTotal = orderService.createNewOrder(itemNames).total
        assertThat(orderTotal).isEqualTo(BigDecimal.valueOf(1.45))

        itemNames = listOf("apple", "orange", "orange")
        orderTotal = orderService.createNewOrder(itemNames).total
        assertThat(orderTotal).isEqualTo(BigDecimal.valueOf(1.10).setScale(2))

        itemNames = listOf("apple", "apple", "apple", "orange", "orange", "orange", "orange")
        orderTotal = orderService.createNewOrder(itemNames).total
        assertThat(orderTotal).isEqualTo(BigDecimal.valueOf(1.95))
    }

    @Test
    fun `test calculate order invalid input`() {
        val invalidItemNames = listOf("", "pineapple", "mango", "    ")
        var orderTotal = orderService.createNewOrder(invalidItemNames).total
        assertThat(orderTotal).isEqualTo(BigDecimal.ZERO)

        val mixedItemNames = listOf("   ", "", "WRONG", "apple")
        orderTotal = orderService.createNewOrder(mixedItemNames).total
        assertThat(orderTotal).isEqualTo(BigDecimal.valueOf(0.60).setScale(2))
    }

}