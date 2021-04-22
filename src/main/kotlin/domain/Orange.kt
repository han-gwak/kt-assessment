package domain

import java.math.BigDecimal

class Orange : Item(price = ORANGE_PRICE) {
    companion object {
        val ORANGE_PRICE = BigDecimal.valueOf(0.25)
    }
}
