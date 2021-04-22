package event

import domain.Order

data class OrderCompleted(val order: Order)