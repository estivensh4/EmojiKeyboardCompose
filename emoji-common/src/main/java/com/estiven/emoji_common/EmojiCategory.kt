package com.estiven.emoji_common

interface EmojiCategory {
    val categoryName: String
    val emojis: List<Emoji>
}