package service

import domain.Item
import domain.Order
import java.math.BigDecimal

interface OrderService {

    fun createNewOrder(itemNames: List<String>): Order

    fun calculateOrderTotal(items: List<Item>): BigDecimal

}