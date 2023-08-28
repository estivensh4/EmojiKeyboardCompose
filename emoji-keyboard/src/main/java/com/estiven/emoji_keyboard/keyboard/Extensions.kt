package com.estiven.emoji_keyboard.keyboard

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import com.estiven.emoji_common.Emoji
import com.estiven.emoji_common.EmojiCategory

@Composable
internal fun Int.pxToDp() = with(LocalDensity.current) { this@pxToDp.toDp() }

fun findEmojiInCategories(segment: String, emojiCategories: List<EmojiCategory>): Emoji? {
    for (category in emojiCategories) {
        val emoji = category.emojis.find { it.matches(segment) }
        if (emoji != null) {
            return emoji
        }
    }
    return null
}

fun findVariantEmoji(baseEmoji: Emoji, variantCode: String): Emoji? {
    for (variant in baseEmoji.variants) {
        if (variant.matches(variantCode)) {
            return variant
        }
    }
    return null
}