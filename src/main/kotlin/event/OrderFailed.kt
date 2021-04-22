package event

import domain.Order

data class OrderFailed(val order: Order)