package service

import domain.Order
import domain.OrderStatus
import mapper.ItemMapper

class CustomerNotificationServiceImpl : CustomerNotificationService {

    override fun notifyCustomerOrderSuccess(order: Order): String {
        val result = "Order ${order.id} has status: ${order.status}\nEstimated delivery time: ${order.deliveryTime}"
        println(result)
        return result
    }

    override fun notifyCustomerOrderFailed(order: Order): String {
        val message = if (order.status == OrderStatus.ITEMS_OUT_OF_STOCK) {
            val outOfStockItems = order.items.filter {
                it.outOfStock
            }
                .map { ItemMapper.mapItemToString(it) }
                .distinct()
            "Order ${order.id} could not be completed as the following items are out of stock: $outOfStockItems"
        } else {
            "Order ${order.id} has failed with status: ${order.status}"
        }
        println(message)
        return message
    }

}