package service

import domain.Order
import kotlinx.coroutines.Deferred

interface CustomerNotificationService {

    fun notifyCustomerOrderSuccess(order: Order): String

    fun notifyCustomerOrderFailed(order: Order): String

}