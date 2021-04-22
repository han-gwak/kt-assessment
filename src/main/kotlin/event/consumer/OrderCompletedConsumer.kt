package event.consumer

import domain.OrderStatus
import event.OrderCompleted
import event.OrderSubmitted
import org.apache.kafka.clients.CommonClientConfigs
import org.apache.kafka.clients.consumer.KafkaConsumer
import service.CustomerNotificationService
import service.CustomerNotificationServiceImpl
import service.OrderService
import service.OrderServiceImpl
import java.net.InetAddress
import java.time.Duration.ofMillis
import java.util.*

class OrderCompletedConsumer(private val config: Properties) {

    companion object {
        const val ORDER_COMPLETED_TOPIC = "order-completed"
    }

    val customerNotificationService: CustomerNotificationService
    val consumer: KafkaConsumer<String, OrderCompleted>

    init {
        customerNotificationService = CustomerNotificationServiceImpl()
        consumer = KafkaConsumer<String, OrderCompleted>(config).apply {
            subscribe(listOf(ORDER_COMPLETED_TOPIC))
        }
    }

    fun handleEvents() {
        consumer.use {
            while (true) {
                consumer.poll(ofMillis(1000))
                    .forEach {
                        val order = it.value().order
                        if (order.status == OrderStatus.ITEMS_OUT_OF_STOCK) {
                            customerNotificationService.notifyCustomerOrderFailed(order)
                        } else {
                            customerNotificationService.notifyCustomerOrderSuccess(order)
                        }
                    }
            }
        }
    }
}