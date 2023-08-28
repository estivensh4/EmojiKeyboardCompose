package com.estiven.emoji_ios

import com.estiven.emoji_common.EmojiCategory
import com.estiven.emoji_common.EmojiProvider

class IosEmojiProvider : EmojiProvider {
    override val categories: List<EmojiCategory>
        get() = listOf(
            PeopleCategory(),
            NatureCategory(),
            FoodCategory(),
            ActivityCategory(),
            TravelCategory(),
            ObjectsCategory(),
            SymbolsCategory(),
            FlagsCategory()
        )
}