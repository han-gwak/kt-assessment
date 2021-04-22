package domain

import java.math.BigDecimal

class Apple : Item(price = APPLE_PRICE) {
    companion object {
        val APPLE_PRICE = BigDecimal.valueOf(0.60)
    }
}
