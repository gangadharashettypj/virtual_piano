package com.dreamtech.virtual_piano

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.TypedValue
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
                this.pianoKey.style.borderWidth
            ), false
        )
        setAnimationDuration(
            attributes.getInt(
                R.styleable.StandardPianoKey_animationDuration,
                this.pianoKey.animationDuration.toInt()
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
        setKeyLabelSize(
            attributes.getDimension(
                R.styleable.StandardPianoKey_labelSize,
                64f,
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
                    animator.duration = this.pianoKey.animationDuration
                    animator.start()
                    onTapDown?.invoke()
                }
                MotionEvent.ACTION_UP -> {
                    val animatedView = findViewById<RelativeLayout>(R.id.white_key)
                    val params = animatedView.layoutParams as LayoutParams
                    val animator = ValueAnimator.ofInt(
                        params.bottomMargin,
                        this.pianoKey.style.shadowHeight.toInt().px,
                    )
                    animator.addUpdateListener { valueAnimator ->
                        params.bottomMargin = valueAnimator.animatedValue as Int
                        animatedView.requestLayout()
                    }
                    animator.duration = this.pianoKey.animationDuration
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
        this.pianoKey.style.cornerRadii = floatArrayOf(
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
            this.pianoKey.style.cornerRadii[0],
            this.pianoKey.style.cornerRadii[2],
            this.pianoKey.style.cornerRadii[4],
            this.pianoKey.style.cornerRadii[6]
        )
    }

    fun setShadowHeight(height: Float, sync: Boolean = true) {
        this.pianoKey.style.shadowHeight = height
        if (sync) syncKeyStyle()
    }

    fun getShadowHeight(): Float {
        return this.pianoKey.style.shadowHeight
    }

    fun setBorderWidth(width: Int, sync: Boolean = true) {
        this.pianoKey.style.borderWidth = width
        if (sync) syncKeyStyle()
    }

    fun getBorderWidth(): Int {
        return this.pianoKey.style.borderWidth
    }

    fun setStrokeColor(color: Int, sync: Boolean = true) {
        this.pianoKey.style.strokeColor = color
        if (sync) syncKeyStyle()
    }

    fun getStrokeColor(): Int {
        return this.pianoKey.style.strokeColor
    }

    fun setLabelColor(color: Int, sync: Boolean = true) {
        this.pianoKey.style.labelColor = color
        if (sync) syncKeyStyle()
    }

    fun getLabelColor(): Int {
        return this.pianoKey.style.labelColor
    }

    fun setPressedShadowColor(color: Int, sync: Boolean = true) {
        this.pianoKey.style.pressedShadowColor = color
        if (sync) syncKeyStyle()
    }

    fun getPressedShadowColor(): Int {
        return this.pianoKey.style.pressedShadowColor
    }

    fun setKeyColor(color: Int, sync: Boolean = true) {
        this.pianoKey.style.keyColor = color
        if (sync) syncKeyStyle()
    }

    fun getKeyColor(): Int {
        return this.pianoKey.style.keyColor
    }

    fun setKeyStyle(keyStyle: PianoKeyStyle, sync: Boolean = true) {
        this.pianoKey.style = keyStyle
        if (sync) syncKeyStyle()
    }

    fun getKeyStyle(): PianoKeyStyle {
        return this.pianoKey.style
    }

    fun setAnimationDuration(duration: Long, sync: Boolean = true) {
        this.pianoKey.animationDuration = duration
        if (sync) syncKeyStyle()
    }

    fun getAnimationDuration(): Long {
        return this.pianoKey.animationDuration
    }

    fun setKeyLabel(keyLabel: CharSequence?, sync: Boolean = true) {
        this.pianoKey.label = keyLabel.toString()
        if (sync) syncKeyStyle()
    }

    fun getKeyLabel(): String {
        return this.pianoKey.label
    }

    fun setKeyLabelSize(size: Float, sync: Boolean = true) {
        this.pianoKey.style.labelSize = size
        if (sync) syncKeyStyle()
    }

    fun getKeyLabelSize(): Float {
        return this.pianoKey.style.labelSize
    }


    private fun syncKeyStyle() {
        val pressingShadowBgShape = GradientDrawable()
        pressingShadowBgShape.cornerRadii = this.pianoKey.style.cornerRadii
        pressingShadowBgShape.setColor(this.pianoKey.style.pressedShadowColor)
        pressingShadow.background = pressingShadowBgShape

        val keyBgShape = GradientDrawable()
        keyBgShape.setColor(this.pianoKey.style.keyColor)
        keyBgShape.setStroke(this.pianoKey.style.borderWidth, this.pianoKey.style.strokeColor)
        keyBgShape.cornerRadii = this.pianoKey.style.cornerRadii
        key.background = keyBgShape


        keyLabel.setTextSize(TypedValue.COMPLEX_UNIT_PX, this.pianoKey.style.labelSize)
        keyLabel.text = this.pianoKey.label
        keyLabel.setTextColor(this.pianoKey.style.labelColor)
    }
}
