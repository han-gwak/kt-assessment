package service

import domain.*
import event.OrderCompleted
import event.producer.OrderCompletedProducer
import mapper.ItemMapper
import org.apache.kafka.clients.CommonClientConfigs
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringSerializer
import java.math.BigDecimal
import java.net.InetAddress
import java.time.Duration
import java.time.Instant
import java.util.*

class OrderServiceImpl(private val config: Properties?) : OrderService {

    companion object {
        val DEFAULT_DURATION: Duration = Duration.ofMinutes(45)
    }

    private var orderCompletedProducer: OrderCompletedProducer? = null

    init {
        if (config != null) {
            orderCompletedProducer = OrderCompletedProducer(config)
        }
    }

    override fun createNewOrder(itemNames: List<String>): Order {
        val items = itemNames.mapNotNull { ItemMapper.mapStringToItem(it) }
        val orderTotal = calculateOrderTotal(items)
        val result = Order(orderTotal, items, OrderStatus.RECEIVED, Instant.now().plus(DEFAULT_DURATION))
        orderCompletedProducer?.sendOrderCompleted(OrderCompleted(result))
        return result
    }

    override fun calculateOrderTotal(items: List<Item>): BigDecimal {
        if (items.isEmpty()) {
            return BigDecimal.ZERO
        }

        var appleCount = 0
        var orangeCount = 0
        items.forEach { item ->
            if (item is Apple) {
                appleCount += 1
            } else if (item is Orange) {
                orangeCount += 1
            }
        }

        // buy one get one free
        val appleTotalPrice = Apple.APPLE_PRICE * ((appleCount / 2) + (appleCount % 2)).toBigDecimal()

        // 3 for the price of 2
        val orangeTotalPrice = Orange.ORANGE_PRICE * (((orangeCount / 3) * 2) + (orangeCount % 3)).toBigDecimal()

        return (appleTotalPrice + orangeTotalPrice).setScale(2)
    }

}