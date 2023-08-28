package com.estiven.emoji_keyboard.keyboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.estiven.emoji_common.Emoji

@Composable
fun EmojiDialog(
    emoji: Emoji,
    onVariantSelected: (String) -> Unit,
    popupPositionProvider: DropdownMenuPositionProvider
) {

    Popup(
        properties = PopupProperties(),
        popupPositionProvider = popupPositionProvider
    ) {
        Card(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 5.dp
            ),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
            ) {
                items(emoji.variants) { variant ->
                    Image(
                        painter = painterResource(id = variant.iconResId),
                        contentDescription = null,
                        modifier = Modifier
                            .size(34.dp)
                            .clickable(
                                onClick = {
                                    onVariantSelected(variant.getUnicodeString())
                                },
                                interactionSource = MutableInteractionSource(),
                                indication = null
                            )
                    )
                }
            }
        }
    }
}