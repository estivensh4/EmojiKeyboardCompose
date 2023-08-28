package com.estiven.emojikeyboardcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import com.estiven.emoji_ios.IosEmojiProvider
import com.estiven.emoji_keyboard.keyboard.EmojiKeyboard
import com.estiven.emojikeyboardcompose.ui.theme.EmojiKeyboardComposeTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            EmojiKeyboardComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    var textFieldValueState by remember {
                        mutableStateOf(
                            TextFieldValue(
                                text = ""
                            )
                        )
                    }
                    EmojiKeyboard(
                        emojiProvider = IosEmojiProvider(),
                        textFieldValue = textFieldValueState
                    ) {
                        textFieldValueState = it
                    }
                }
            }
        }
    }
}


