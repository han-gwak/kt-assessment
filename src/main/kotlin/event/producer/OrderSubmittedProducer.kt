package event.producer

import event.OrderSubmitted
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerRecord
import java.util.*

class OrderSubmittedProducer(private val config: Properties) {

    companion object {
        const val ORDER_SUBMITTED_TOPIC = "order-submitted"
    }

    val orderSubmittedProducer = KafkaProducer<String, OrderSubmitted>(config)

    fun sendOrderSubmitted(orderSubmitted: OrderSubmitted) {
        orderSubmittedProducer.send(
            ProducerRecord(ORDER_SUBMITTED_TOPIC, null, orderSubmitted))
    }
}