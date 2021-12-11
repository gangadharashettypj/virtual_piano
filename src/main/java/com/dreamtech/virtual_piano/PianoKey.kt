package com.dreamtech.virtual_piano

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.RelativeLayout
import android.widget.TextView
import com.dreamtech.virtual_piano.model.PianoKey
import com.dreamtech.virtual_piano.model.PianoKeyStyle

@SuppressLint("ClickableViewAccessibility", "CutPasteId")
class PianoKey(context: Context, attrs: AttributeSet) : RelativeLayout(context, attrs) {

    private var onTapDown: (() -> Unit)? = null
    private var onTapRelease: (() -> Unit)? = null
    private lateinit var keyLabel: TextView
    private lateinit var key: RelativeLayout
    private lateinit var pressingShadow: RelativeLayout
    private val pianoKey: PianoKey = PianoKey()

    init {
        inflate(context, R.layout.piano_individual_key, this)
        initComponents()

        val attributes = context.obtainStyledAttributes(attrs, R.styleable.StandardPianoKey)
        setKeyLabel(attributes.getString(R.styleable.StandardPianoKey_label))
        setBorderWidth(
            attributes.getInt(
                R.styleable.StandardPianoKey_outerBorderWidth,
                pianoKey.style.borderWidth
            ), false
        )
        setAnimationDuration(
            attributes.getInt(
                R.styleable.StandardPianoKey_animationDuration,
                pianoKey.animationDuration.toInt()
            ).toLong(), false
        )
        setStrokeColor(
            attributes.getColor(
                R.styleable.StandardPianoKey_strokeColor,
                Color.WHITE,
            ), false
        )
        setKeyColor(
            attributes.getColor(
                R.styleable.StandardPianoKey_keyColor,
                Color.WHITE,
            ), false
        )
        setPressedShadowColor(
            attributes.getColor(
                R.styleable.StandardPianoKey_pressedShadowColor,
                0xFFC8C8C8.toInt(),
            ), false
        )
        setLabelColor(
            attributes.getColor(
                R.styleable.StandardPianoKey_labelColor,
                Color.BLACK,
            ), false
        )

        when (attributes.getInt(R.styleable.StandardPianoKey_default_style, -1)) {
            0 -> {
                setKeyStyle(VirtualPianoConstants.WHITE_KEY_STYLE, false)
            }
            1 -> {
                setKeyStyle(VirtualPianoConstants.BLACK_KEY_STYLE, false)
            }
        }

        setCornerRadii(
            floatArrayOf(
                attributes.getFloat(
                    R.styleable.StandardPianoKey_topLeftRadius,
                    0f,
                ),

                attributes.getFloat(
                    R.styleable.StandardPianoKey_topRightRadius,
                    0f,
                ),

                attributes.getFloat(
                    R.styleable.StandardPianoKey_bottomRightRadius,
                    16f,
                ),

                attributes.getFloat(
                    R.styleable.StandardPianoKey_bottomLeftRadius,
                    16f,
                ),
            ), false
        )
        attributes.recycle()
        syncKeyStyle()
    }

    private fun initComponents() {
        keyLabel = findViewById(R.id.key_label)
        key = findViewById(R.id.white_key)
        pressingShadow = findViewById(R.id.pressing_shadow)
        key.setOnTouchListener { _, event ->
            when (event?.action) {
                MotionEvent.ACTION_DOWN -> {
                    val animatedView = findViewById<RelativeLayout>(R.id.white_key)
                    val params = animatedView.layoutParams as LayoutParams
                    val animator = ValueAnimator.ofInt(params.bottomMargin, 0)
                    animator.addUpdateListener { valueAnimator ->
                        params.bottomMargin = valueAnimator.animatedValue as Int
                        animatedView.requestLayout()
                    }
                    animator.duration = pianoKey.animationDuration
                    animator.start()
                    onTapDown?.invoke()
                }
                MotionEvent.ACTION_UP -> {
                    val animatedView = findViewById<RelativeLayout>(R.id.white_key)
                    val params = animatedView.layoutParams as LayoutParams
                    val animator = ValueAnimator.ofInt(
                        params.bottomMargin,
                        pianoKey.style.shadowHeight.toInt().px,
                    )
                    animator.addUpdateListener { valueAnimator ->
                        params.bottomMargin = valueAnimator.animatedValue as Int
                        animatedView.requestLayout()
                    }
                    animator.duration = pianoKey.animationDuration
                    animator.start()
                    onTapRelease?.invoke()
                }
            }

            true
        }
    }

    fun setOnTapDownListener(onTapDown: () -> Unit) {
        this.onTapDown = onTapDown
    }

    fun setOnTapReleaseListener(onTapRelease: () -> Unit) {
        this.onTapRelease = onTapRelease
    }

    fun setCornerRadii(array: FloatArray, sync: Boolean = true) {
        pianoKey.style.cornerRadii = floatArrayOf(
            array[0],
            array[0],
            array[1],
            array[1],
            array[2],
            array[2],
            array[3],
            array[3],
        )
        if (sync) syncKeyStyle()
    }

    fun getCornerRadii(): FloatArray {
        return floatArrayOf(
            pianoKey.style.cornerRadii[0],
            pianoKey.style.cornerRadii[2],
            pianoKey.style.cornerRadii[4],
            pianoKey.style.cornerRadii[6]
        )
    }

    fun setShadowHeight(height: Float, sync: Boolean = true) {
        pianoKey.style.shadowHeight = height
        if (sync) syncKeyStyle()
    }

    fun getShadowHeight(): Float {
        return pianoKey.style.shadowHeight
    }

    fun setBorderWidth(width: Int, sync: Boolean = true) {
        pianoKey.style.borderWidth = width
        if (sync) syncKeyStyle()
    }

    fun getBorderWidth(): Int {
        return pianoKey.style.borderWidth
    }

    fun setStrokeColor(color: Int, sync: Boolean = true) {
        pianoKey.style.strokeColor = color
        if (sync) syncKeyStyle()
    }

    fun getStrokeColor(): Int {
        return pianoKey.style.strokeColor
    }

    fun setLabelColor(color: Int, sync: Boolean = true) {
        pianoKey.style.labelColor = color
        if (sync) syncKeyStyle()
    }

    fun getLabelColor(): Int {
        return pianoKey.style.labelColor
    }

    fun setPressedShadowColor(color: Int, sync: Boolean = true) {
        pianoKey.style.pressedShadowColor = color
        if (sync) syncKeyStyle()
    }

    fun getPressedShadowColor(): Int {
        return pianoKey.style.pressedShadowColor
    }

    fun setKeyColor(color: Int, sync: Boolean = true) {
        pianoKey.style.keyColor = color
        if (sync) syncKeyStyle()
    }

    fun getKeyColor(): Int {
        return pianoKey.style.keyColor
    }

    fun setKeyStyle(keyStyle: PianoKeyStyle, sync: Boolean = true) {
        pianoKey.style = keyStyle
        if (sync) syncKeyStyle()
    }

    fun getKeyStyle(): PianoKeyStyle {
        return pianoKey.style
    }

    fun setAnimationDuration(duration: Long, sync: Boolean = true) {
        pianoKey.animationDuration = duration
        if (sync) syncKeyStyle()
    }

    fun getAnimationDuration(): Long {
        return pianoKey.animationDuration
    }

    fun setKeyLabel(keyLabel: CharSequence?, sync: Boolean = true) {
        pianoKey.label = keyLabel.toString()
        if (sync) syncKeyStyle()
    }

    fun getKeyLabel(): String {
        return pianoKey.label
    }


    private fun syncKeyStyle() {
        pressingShadow.setBackgroundResource(R.drawable.bg_piano_white_key_shadow)
        key.setBackgroundResource(R.drawable.bg_piano_white_key)

        val pressingShadowBgShape = pressingShadow.background as GradientDrawable
        pressingShadowBgShape.cornerRadii = pianoKey.style.cornerRadii
        pressingShadowBgShape.setColor(pianoKey.style.pressedShadowColor)

        val keyBgShape = key.background as GradientDrawable
        keyBgShape.setColor(pianoKey.style.keyColor)
        keyBgShape.setStroke(pianoKey.style.borderWidth, pianoKey.style.strokeColor)
        keyBgShape.cornerRadii = pianoKey.style.cornerRadii

        keyLabel.text = pianoKey.label
        keyLabel.setTextColor(pianoKey.style.labelColor)
    }
}
