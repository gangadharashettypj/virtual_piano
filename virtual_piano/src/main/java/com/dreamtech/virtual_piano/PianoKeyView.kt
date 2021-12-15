package com.dreamtech.virtual_piano

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.MotionEvent
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.Nullable
import com.dreamtech.virtual_piano.model.PianoKey
import com.dreamtech.virtual_piano.model.PianoKeyStyle


@SuppressLint("ClickableViewAccessibility", "CutPasteId")
class PianoKeyView(context: Context, @Nullable attrs: AttributeSet?) :
    RelativeLayout(context, attrs) {

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
        if (attributes.getInt(R.styleable.StandardPianoKey_default_style, -1) == 0) {
            this.pianoKey.style = VirtualPianoConstants.WHITE_KEY_STYLE
        }

        else if (attributes.getInt(R.styleable.StandardPianoKey_default_style, -1) == 1) {
            this.pianoKey.style = VirtualPianoConstants.BLACK_KEY_STYLE
        } else{
            this.pianoKey.style.borderWidth = attributes.getInt(
                R.styleable.StandardPianoKey_outer_border_width,
                this.pianoKey.style.borderWidth
            )
            this.pianoKey.style.strokeColor = attributes.getColor(
                R.styleable.StandardPianoKey_stroke_color,
                Color.WHITE,
            )
            this.pianoKey.style.keyColor = attributes.getColor(
                R.styleable.StandardPianoKey_key_color,
                Color.WHITE,
            )
            this.pianoKey.style.shadowColor = attributes.getColor(
                R.styleable.StandardPianoKey_shadow_color,
                0xFFC8C8C8.toInt(),
            )
            this.pianoKey.style.labelColor = attributes.getColor(
                R.styleable.StandardPianoKey_label_color,
                Color.BLACK,
            )
            this.pianoKey.style.labelSize = attributes.getDimension(
                R.styleable.StandardPianoKey_label_size,
                64f,
            )
            this.pianoKey.style.pressedHeight = attributes.getDimension(
                R.styleable.StandardPianoKey_pressed_height,
                64f,
            )
            this.pianoKey.style.pressedColor = attributes.getColor(
                R.styleable.StandardPianoKey_pressed_color,
                0xFF8DD599.toInt(),
            )
            val array = floatArrayOf(
                attributes.getFloat(
                    R.styleable.StandardPianoKey_top_left_radius,
                    0f,
                ),

                attributes.getFloat(
                    R.styleable.StandardPianoKey_top_right_radius,
                    0f,
                ),

                attributes.getFloat(
                    R.styleable.StandardPianoKey_bottom_right_radius,
                    16f,
                ),

                attributes.getFloat(
                    R.styleable.StandardPianoKey_bottom_left_radius,
                    16f,
                ),
            )
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
        }
        this.pianoKey.label = attributes.getString(R.styleable.StandardPianoKey_label).toString()

        this.pianoKey.animationDuration = attributes.getInt(
            R.styleable.StandardPianoKey_animation_duration,
            this.pianoKey.animationDuration.toInt()
        ).toLong()


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
                    val pressingShadowBgShape =animatedView.background as GradientDrawable
                    pressingShadowBgShape.setColor(this.pianoKey.style.pressedColor)
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
                    val pressingShadowBgShape =animatedView.background as GradientDrawable
                    pressingShadowBgShape.setColor(this.pianoKey.style.keyColor)
                    val params = animatedView.layoutParams as LayoutParams
                    val animator = ValueAnimator.ofInt(
                        params.bottomMargin,
                        this.pianoKey.style.pressedHeight.toInt(),
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

    fun setCornerRadii(array: FloatArray) {
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
        syncKeyStyle()
    }

    fun getCornerRadii(): FloatArray {
        return floatArrayOf(
            this.pianoKey.style.cornerRadii[0],
            this.pianoKey.style.cornerRadii[2],
            this.pianoKey.style.cornerRadii[4],
            this.pianoKey.style.cornerRadii[6]
        )
    }

    fun setBorderWidth(width: Int) {
        this.pianoKey.style.borderWidth = width
        syncKeyStyle()
    }

    fun getBorderWidth(): Int {
        return this.pianoKey.style.borderWidth
    }

    fun setStrokeColor(color: Int) {
        this.pianoKey.style.strokeColor = color
        syncKeyStyle()
    }

    fun getStrokeColor(): Int {
        return this.pianoKey.style.strokeColor
    }

    fun setLabelColor(color: Int) {
        this.pianoKey.style.labelColor = color
        syncKeyStyle()
    }

    fun getLabelColor(): Int {
        return this.pianoKey.style.labelColor
    }

    fun setShadowColor(color: Int) {
        this.pianoKey.style.shadowColor = color
        syncKeyStyle()
    }

    fun getShadowColor(): Int {
        return this.pianoKey.style.shadowColor
    }

    fun setKeyColor(color: Int) {
        this.pianoKey.style.keyColor = color
        syncKeyStyle()
    }

    fun getKeyColor(): Int {
        return this.pianoKey.style.keyColor
    }

    fun setKeyStyle(keyStyle: PianoKeyStyle) {
        this.pianoKey.style = keyStyle
        syncKeyStyle()
    }

    fun getKeyStyle(): PianoKeyStyle {
        return this.pianoKey.style
    }

    fun setAnimationDuration(duration: Long) {
        this.pianoKey.animationDuration = duration
        syncKeyStyle()
    }

    fun getAnimationDuration(): Long {
        return this.pianoKey.animationDuration
    }

    fun setKeyLabel(keyLabel: CharSequence?) {
        this.pianoKey.label = keyLabel.toString()
        syncKeyStyle()
    }

    fun getKeyLabel(): String {
        return this.pianoKey.label
    }

    fun setKeyLabelSize(size: Float) {
        this.pianoKey.style.labelSize = size
        syncKeyStyle()
    }

    fun getKeyLabelSize(): Float {
        return this.pianoKey.style.labelSize
    }

    fun setPressedHeight(height: Float) {
        this.pianoKey.style.pressedHeight = height
        syncKeyStyle()
    }


    fun setPressedColor(color: Int) {
        this.pianoKey.style.pressedColor = color
        syncKeyStyle()
    }

    fun getPressedHeight(): Float {
        return this.pianoKey.style.pressedHeight
    }

    fun getPressedColor(): Int {
        return this.pianoKey.style.pressedColor
    }


    private fun syncKeyStyle() {
        val pressingShadowBgShape = GradientDrawable()
        pressingShadowBgShape.cornerRadii = this.pianoKey.style.cornerRadii
        pressingShadowBgShape.setColor(this.pianoKey.style.shadowColor)
        pressingShadow.background = pressingShadowBgShape

        val keyBgShape = GradientDrawable()
        keyBgShape.setColor(this.pianoKey.style.keyColor)
        keyBgShape.setStroke(this.pianoKey.style.borderWidth, this.pianoKey.style.strokeColor)
        keyBgShape.cornerRadii = this.pianoKey.style.cornerRadii
        key.background = keyBgShape
        val params =
            LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        params.setMargins(
            0, 0, 0,
            this.pianoKey.style.pressedHeight.toInt()
        )
        key.layoutParams = params

        keyLabel.setTextSize(TypedValue.COMPLEX_UNIT_PX, this.pianoKey.style.labelSize)
        keyLabel.text = this.pianoKey.label
        keyLabel.setTextColor(this.pianoKey.style.labelColor)
    }
}
