package com.estiven.emoji_keyboard.keyboard

import android.app.Activity
import android.view.WindowManager
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MicNone
import androidx.compose.material.icons.rounded.EmojiEmotions
import androidx.compose.material.icons.rounded.Keyboard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.estiven.emoji_common.EmojiProvider
import com.estiven.emoji_keyboard.lazygrid.GridCells
import com.estiven.emoji_keyboard.lazygrid.items

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun EmojiKeyboard(
    emojiProvider: EmojiProvider,
    textFieldValue: TextFieldValue,
    textOnValueChange: (TextFieldValue) -> Unit
) {
    val context = LocalContext.current
    val activity = context as Activity
    val insets = ViewCompat.getRootWindowInsets(activity.window.decorView)
    val imeHeight =
        insets?.getInsets(WindowInsetsCompat.Type.ime())?.bottom ?: 0
    val navigationBarsHeight =
        insets?.getInsets(WindowInsetsCompat.Type.navigationBars())?.bottom ?: 0
    val keyboardHeight = (imeHeight - navigationBarsHeight).pxToDp()
    var showEmojiKeyboard by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
    val keyboard = LocalSoftwareKeyboardController.current
    val keyboardState by keyboardAsState()

    context.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)

    LaunchedEffect(key1 = keyboardState) {
        if (keyboardState == Keyboard.Closed) {
            showEmojiKeyboard = false
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(contentAlignment = Alignment.BottomCenter, modifier = Modifier.weight(1f)) {

            EmojiRichTextWithCategories(
                text = textFieldValue.text,
                emojiCategories = emojiProvider.categories
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            IconToggleButton(
                checked = showEmojiKeyboard,
                onCheckedChange = {
                    showEmojiKeyboard = it
                    focusRequester.requestFocus()
                    keyboard?.show()
                }
            ) {
                val icon =
                    if (showEmojiKeyboard) Icons.Rounded.Keyboard else Icons.Rounded.EmojiEmotions
                Icon(imageVector = icon, contentDescription = null)
            }

            Spacer(modifier = Modifier.size(8.dp))

            Box(
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically)
            ) {
                BasicTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusRequester),
                    value = textFieldValue,
                    onValueChange = textOnValueChange,
                )
                if (textFieldValue.text.isEmpty()) {
                    Text(
                        text = "Mensaje",
                        modifier = Modifier.align(Alignment.BottomStart),
                        color = Color.Black
                    )
                }
            }

            InputIcon(
                onClick = { },
                icon = Icons.Default.MicNone,
                description = " stringResource(id = R.string.icon_audio_record_description)",
                tint = MaterialTheme.colorScheme.primary,
            )
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .offset(y = keyboardHeight)
    ) {
        if (showEmojiKeyboard) {
            Popup(
                alignment = Alignment.BottomCenter,
                properties = PopupProperties()
            ) {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(keyboardHeight)
                        .background(Color.Gray)
                ) {
                    com.estiven.emoji_keyboard.lazygrid.LazyVerticalGrid(
                        columns = GridCells.Fixed(8),
                        contentPadding = PaddingValues(8.dp)
                    ) {
                        emojiProvider.categories.forEach { group ->
                            stickyHeader {
                                Text(
                                    text = group.categoryName,
                                    color = Color.DarkGray,
                                    modifier = Modifier.fillMaxWidth(),
                                )
                            }
                            items(items = group.emojis) {
                                EmojiImage(
                                    emoji = it,
                                    onEmojiSelected = { emojiCode ->
                                        val value = textFieldValue.text.plus("$emojiCode")

                                        textOnValueChange(
                                            TextFieldValue(
                                                text = value,
                                                selection = TextRange(value.length)
                                            )
                                        )
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun InputIcon(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: ImageVector,
    description: String,
    tint: Color = MaterialTheme.colorScheme.secondary
) {
    IconButton(onClick = onClick) {
        Icon(icon, tint = tint, contentDescription = description, modifier = modifier)
    }
}


