package com.dreamtech.virtual_piano

import android.graphics.Color
import com.dreamtech.virtual_piano.model.PianoKeyStyle

abstract class VirtualPianoConstants {
    companion object {
        val WHITE_KEY_STYLE = PianoKeyStyle(
            borderWidth = 0,
            strokeColor = Color.WHITE,
            keyColor = Color.WHITE,
            shadowColor = 0xFFC8C8C8.toInt(),
            labelColor = Color.BLACK,
            cornerRadii = floatArrayOf(0f, 0f, 0f, 0f, 32f, 32f, 32f, 32f),
            labelSize = 64f,
            pressedHeight = 64f,
            pressedColor = 0xFF8DD599.toInt(),
        )
        val BLACK_KEY_STYLE = PianoKeyStyle(
            borderWidth = 0,
            strokeColor = Color.BLACK,
            keyColor = Color.BLACK,
            shadowColor = 0xFF2F2F2F.toInt(),
            labelColor = Color.WHITE,
            cornerRadii = floatArrayOf(0f, 0f, 0f, 0f, 32f, 32f, 32f, 32f),
            labelSize = 64f,
            pressedHeight = 64f,
            pressedColor = 0xFF16591D.toInt(),
        )
    }

}