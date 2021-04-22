package event.consumer

import event.OrderSubmitted
import org.apache.kafka.clients.consumer.KafkaConsumer
import service.OrderService
import service.OrderServiceImpl
import java.time.Duration.ofMillis
import java.util.*

class OrderSubmittedConsumer(config: Properties) {

    companion object {
        const val ORDER_SUBMITTED_TOPIC = "order-submitted"
    }

    val orderService: OrderService
    val consumer: KafkaConsumer<String, OrderSubmitted>

    init {
        orderService = OrderServiceImpl(config)

        consumer = KafkaConsumer<String, OrderSubmitted>(config).apply {
            subscribe(listOf(ORDER_SUBMITTED_TOPIC))
        }
    }

    fun handleEvents() {
        consumer.use {
            while (true) {
                consumer.poll(ofMillis(1000))
                    .forEach { orderService.createNewOrder(it.value().items) }
            }
        }
    }
}