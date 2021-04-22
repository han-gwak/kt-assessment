import event.OrderSubmitted
import event.consumer.OrderCompletedConsumer
import event.consumer.OrderSubmittedConsumer
import event.producer.OrderCompletedProducer
import event.producer.OrderSubmittedProducer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.apache.kafka.clients.CommonClientConfigs.GROUP_ID_CONFIG
import org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG
import org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG
import org.apache.kafka.common.serialization.StringSerializer
import java.net.InetAddress
import java.util.*


fun main(args: Array<String>) {

    val config = Properties()
    config["client.id"] = InetAddress.getLocalHost().hostName
    config["bootstrap.servers"] = "host1:9092,host2:9092"
    config["acks"] = "all"
    config[KEY_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
    config[VALUE_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
    config[GROUP_ID_CONFIG] = "Kafka_Group"

    // create consumers and start polling
    val orderSubmittedConsumer = OrderSubmittedConsumer(config)
    val orderCompletedConsumer = OrderCompletedConsumer(config)
    GlobalScope.launch(Dispatchers.IO) { orderSubmittedConsumer.handleEvents() }
    GlobalScope.launch(Dispatchers.IO) { orderCompletedConsumer.handleEvents() }

    println("Customer input: $args")
    val orderSubmittedProducer = OrderSubmittedProducer(config)
    orderSubmittedProducer.sendOrderSubmitted(OrderSubmitted(args.asList()))
}