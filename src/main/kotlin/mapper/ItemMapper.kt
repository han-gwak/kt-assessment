package mapper

import domain.Apple
import domain.Item
import domain.Orange

class ItemMapper {

    companion object {

        private const val APPLE_TEXT_LOWER = "apple"
        private const val ORANGE_TEXT_LOWER = "orange"

        fun mapStringToItem(text: String): Item? {
            return when (text.toLowerCase()) {
                APPLE_TEXT_LOWER -> Apple()
                ORANGE_TEXT_LOWER -> Orange()
                else -> null
            }
        }

        fun mapItemToString(item: Item): String {
            return when (item.javaClass) {
                Apple::class.java -> APPLE_TEXT_LOWER
                Orange::class.java -> ORANGE_TEXT_LOWER
                else -> ""
            }
        }

    }
}