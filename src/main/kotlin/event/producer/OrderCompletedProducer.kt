package event.producer

import event.OrderCompleted
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerRecord
import java.util.*

class OrderCompletedProducer(private val config: Properties) {

    companion object {
        const val ORDER_COMPLETED_TOPIC = "order-completed"
    }

    val orderCompletedProducer = KafkaProducer<String, OrderCompleted>(config)

    fun sendOrderCompleted(orderCompleted: OrderCompleted) {
        orderCompletedProducer.send(
            ProducerRecord(ORDER_COMPLETED_TOPIC, null, orderCompleted))
    }
}