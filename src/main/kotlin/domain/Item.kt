package domain

import java.math.BigDecimal

abstract class Item(open val price: BigDecimal, open var outOfStock: Boolean = false)
