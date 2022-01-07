package com.dreamtech.virtual_piano.model
//
//import com.dreamtech.virtual_piano.VirtualPianoConstants
//
//data class PianoKeyStyle(
//    var borderWidth: Int,
//    var strokeColor: Int,
//    var keyColor: Int,
//    var shadowColor: Int,
//    var labelColor: Int,
//    var labelSize: Float,
//    var pressedHeight: Float,
//    var pressedColor: Int,
//    var cornerRadii: FloatArray,
//) {
//    override fun equals(other: Any?): Boolean {
//        if (this === other) return true
//        if (javaClass != other?.javaClass) return false
//
//        other as PianoKeyStyle
//
//        if (borderWidth != other.borderWidth) return false
//        if (strokeColor != other.strokeColor) return false
//        if (keyColor != other.keyColor) return false
//        if (shadowColor != other.shadowColor) return false
//        if (labelColor != other.labelColor) return false
//        if (labelSize != other.labelSize) return false
//        if (pressedHeight != other.pressedHeight) return false
//        if (pressedColor != other.pressedColor) return false
//        if (!cornerRadii.contentEquals(other.cornerRadii)) return false
//
//        return true
//    }
//
//    override fun hashCode(): Int {
//        var result = borderWidth
//        result = 31 * result + strokeColor
//        result = 31 * result + keyColor
//        result = 31 * result + shadowColor
//        result = 31 * result + labelColor
//        result = 31 * result + labelSize.hashCode()
//        result = 31 * result + pressedHeight.hashCode()
//        result = 31 * result + pressedColor
//        result = 31 * result + cornerRadii.contentHashCode()
//        return result
//    }
//
//}
//
//data class PianoKey(
//    var label: String = "",
//    var viewOnly: Boolean = false,
//    var style: PianoKeyStyle,
//    var animationDuration: Long = 60,
//)
