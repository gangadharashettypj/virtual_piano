package com.dreamtech.virtual_piano

import android.graphics.Color
import com.dreamtech.virtual_piano.model.PianoKeyStyle

abstract class VirtualPianoConstants {
    companion object {
        val WHITE_KEY_STYLE = PianoKeyStyle(
            borderWidth = 0,
            strokeColor = Color.WHITE,
            keyColor = Color.WHITE,
            pressedShadowColor = 0xFFC8C8C8.toInt(),
            labelColor = Color.BLACK,
            cornerRadii = floatArrayOf(0f, 0f, 0f, 0f, 32f, 32f, 32f, 32f),
            shadowHeight = 16f,
            labelSize = 64f,
        )
        val BLACK_KEY_STYLE = PianoKeyStyle(
            borderWidth = 0,
            strokeColor = Color.BLACK,
            keyColor = Color.BLACK,
            pressedShadowColor = 0xFF2F2F2F.toInt(),
            labelColor = Color.WHITE,
            cornerRadii = floatArrayOf(0f, 0f, 0f, 0f, 32f, 32f, 32f, 32f),
            shadowHeight = 16f,
            labelSize = 64f,
        )
    }

}