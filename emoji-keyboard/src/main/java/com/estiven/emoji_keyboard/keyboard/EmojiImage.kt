package com.estiven.emoji_keyboard.keyboard

import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.estiven.emoji_common.Emoji

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun EmojiImage(
    offset: DpOffset = DpOffset(0.dp, 0.dp),
    emoji: Emoji,
    onEmojiSelected: (String) -> Unit
) {
    var isDialogVisible by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .padding(4.dp)
            .combinedClickable(
                onClick = {
                    onEmojiSelected(emoji.getUnicodeString())
                },
                onLongClick = {
                    if (emoji.variants.isNotEmpty()) {
                        isDialogVisible = true
                    } else {
                        onEmojiSelected(emoji.getUnicodeString())
                    }

                },
                indication = null,
                interactionSource = MutableInteractionSource()
            ),
        contentAlignment = Alignment.Center,
    ) {
        Box {
            Image(
                painter = painterResource(id = emoji.iconResId),
                contentDescription = null,
                modifier = Modifier.size(45.dp)
            )
            if (emoji.variants.isNotEmpty()) {
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .size(10.dp)
                        .background(Color.Gray, CircleShape)
                )
            }
        }

        val expandedStates = remember { MutableTransitionState(false) }
        expandedStates.targetState = isDialogVisible

        if (expandedStates.currentState || expandedStates.targetState) {
            val transformOriginState = remember { mutableStateOf(TransformOrigin.Center) }
            val density = LocalDensity.current
            val popupPositionProvider = DropdownMenuPositionProvider(
                offset,
                density
            ) { parentBounds, menuBounds ->
                transformOriginState.value = calculateTransformOrigin(parentBounds, menuBounds)
            }
            EmojiDialog(
                emoji = emoji,
                onVariantSelected = { variant ->
                    onEmojiSelected(variant)
                    isDialogVisible = false
                },
                popupPositionProvider = popupPositionProvider
            )
        }
    }
}