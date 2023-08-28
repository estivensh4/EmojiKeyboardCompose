package com.estiven.emoji_common

data class Emoji(
    val unicode: Any,
    val iconResId: Int,
    val variants: Array<Emoji> = arrayOf()
) {

    fun getUnicodeString(): String {
        return when (unicode) {
            is Int -> String(Character.toChars(unicode))
            is IntArray -> unicode.joinToString("") { codePoint ->
                String(Character.toChars(codePoint))
            }

            else -> ""
        }
    }

    fun matches(segment: String): Boolean {
        return getUnicodeString() == segment || variants.any { it.getUnicodeString() == segment }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Emoji

        if (unicode != other.unicode) return false
        if (iconResId != other.iconResId) return false
        if (!variants.contentEquals(other.variants)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = unicode.hashCode()
        result = 31 * result + iconResId
        result = 31 * result + variants.contentHashCode()
        return result
    }
}

