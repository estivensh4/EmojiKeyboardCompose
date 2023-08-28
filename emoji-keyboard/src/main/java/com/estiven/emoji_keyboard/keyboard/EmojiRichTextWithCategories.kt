package com.estiven.emoji_keyboard.keyboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.estiven.emoji_common.EmojiCategory

@Composable
fun EmojiRichTextWithCategories(text: String, emojiCategories: List<EmojiCategory>) {
    val textSegments = text.split(" ")
    Row(verticalAlignment = Alignment.CenterVertically) {
        for (segment in textSegments) {
            val emoji = findEmojiInCategories(segment, emojiCategories)
            if (emoji != null) {
                val variantEmoji = findVariantEmoji(emoji, segment)
                val resolvedEmoji = variantEmoji ?: emoji
                Image(
                    painter = painterResource(id = resolvedEmoji.iconResId),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
            } else {
                Text(text = "$segment ")
            }
        }
    }
}