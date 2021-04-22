package domain

enum class OrderStatus {
    RECEIVED,
    ITEMS_OUT_OF_STOCK,
    IN_PROGRESS,
    CHECKOUT,
    OUT_FOR_DELIVERY,
    DELIVERED
}