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


@SuppressLint("ClickableViewAccessibility", "CutPasteId")
class PianoKeyView(context: Context, @Nullable attrs: AttributeSet?) :
    RelativeLayout(context, attrs) {

    private var onTapDown: (() -> Unit)? = null
    private var onTapRelease: (() -> Unit)? = null
    private lateinit var keyLabel: TextView
    private lateinit var key: RelativeLayout
    private lateinit var pressingShadow: RelativeLayout
    private var borderWidth: Int = 0
    private var strokeColor: Int = Color.WHITE
    private var keyColor: Int = Color.WHITE
    private var shadowColor: Int = 0xFFC8C8C8.toInt()
    private var labelColor: Int = Color.BLACK
    private var labelSize: Float = 64f
    private var pressedHeight: Float = 64f
    private var pressedColor: Int = 0xFF8DD599.toInt()
    private var cornerRadii: FloatArray = floatArrayOf(0f, 0f, 0f, 0f, 32f, 32f, 32f, 32f)
    private var label: String = ""
    private var viewOnly: Boolean = false
    private var animationDuration: Long = 60

    init {
        inflate(context, R.layout.piano_individual_key, this)
        initComponents()

        val attributes = context.obtainStyledAttributes(attrs, R.styleable.StandardPianoKey)
        if (attributes.getInt(
                R.styleable.StandardPianoKey_default_style,
                -1
            ) == 0 || attributes.getInt(R.styleable.StandardPianoKey_default_style, -1) == -1
        ) {
            borderWidth = 0
            strokeColor = Color.WHITE
            keyColor = Color.WHITE
            shadowColor = 0xFFC8C8C8.toInt()
            labelColor = Color.BLACK
            cornerRadii = floatArrayOf(0f, 0f, 0f, 0f, 32f, 32f, 32f, 32f)
            labelSize = 64f
            pressedHeight = 64f
            pressedColor = 0xFF8DD599.toInt()
        } else if (attributes.getInt(R.styleable.StandardPianoKey_default_style, -1) == 1) {

            borderWidth = 0
            strokeColor = Color.BLACK
            keyColor = Color.BLACK
            shadowColor = 0xFF2F2F2F.toInt()
            labelColor = Color.WHITE
            cornerRadii = floatArrayOf(0f, 0f, 0f, 0f, 32f, 32f, 32f, 32f)
            labelSize = 64f
            pressedHeight = 64f
            pressedColor = 0xFF16591D.toInt()
        }
        borderWidth = attributes.getInt(
            R.styleable.StandardPianoKey_outer_border_width,
            borderWidth
        )
        strokeColor = attributes.getColor(
            R.styleable.StandardPianoKey_stroke_color,
            strokeColor,
        )
        viewOnly = attributes.getBoolean(
            R.styleable.StandardPianoKey_view_only,
            viewOnly,
        )
        keyColor = attributes.getColor(
            R.styleable.StandardPianoKey_key_color,
            keyColor,
        )
        shadowColor = attributes.getColor(
            R.styleable.StandardPianoKey_shadow_color,
            shadowColor,
        )
        labelColor = attributes.getColor(
            R.styleable.StandardPianoKey_label_color,
            labelColor,
        )
        labelSize = attributes.getDimension(
            R.styleable.StandardPianoKey_label_size,
            labelSize,
        )
        if (attributes.getFloat(
                R.styleable.StandardPianoKey_pressed_height,
                pressedHeight,
            ) < 0.5
        ) {
            pressedHeight = attributes.getFloat(
                R.styleable.StandardPianoKey_pressed_height,
                pressedHeight,
            )
        }
        pressedColor = attributes.getColor(
            R.styleable.StandardPianoKey_pressed_color,
            pressedColor,
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
        cornerRadii = floatArrayOf(
            array[0],
            array[0],
            array[1],
            array[1],
            array[2],
            array[2],
            array[3],
            array[3],
        )
        label =
            attributes.getString(R.styleable.StandardPianoKey_label)?.toString() ?: ""

        animationDuration = attributes.getInt(
            R.styleable.StandardPianoKey_animation_duration,
            animationDuration.toInt()
        ).toLong()


        attributes.recycle()
        syncKeyStyle()
    }

    private fun initComponents() {
        keyLabel = findViewById(R.id.key_label)
        key = findViewById(R.id.white_key)
        pressingShadow = findViewById(R.id.pressing_shadow)
    }

    fun setOnTapDownListener(onTapDown: () -> Unit) {
        this.onTapDown = onTapDown
    }

    fun setOnTapReleaseListener(onTapRelease: () -> Unit) {
        this.onTapRelease = onTapRelease
    }

    fun setCornerRadii(array: FloatArray) {
        cornerRadii = floatArrayOf(
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
            cornerRadii[0],
            cornerRadii[2],
            cornerRadii[4],
            cornerRadii[6]
        )
    }

    fun setBorderWidth(width: Int) {
        borderWidth = width
        syncKeyStyle()
    }

    fun getBorderWidth(): Int {
        return borderWidth
    }

    fun setStrokeColor(color: Int) {
        strokeColor = color
        syncKeyStyle()
    }

    fun getStrokeColor(): Int {
        return strokeColor
    }

    fun setLabelColor(color: Int) {
        labelColor = color
        syncKeyStyle()
    }

    fun getLabelColor(): Int {
        return labelColor
    }

    fun setShadowColor(color: Int) {
        shadowColor = color
        syncKeyStyle()
    }

    fun getShadowColor(): Int {
        return shadowColor
    }

    fun setKeyColor(color: Int) {
        keyColor = color
        syncKeyStyle()
    }

    fun getKeyColor(): Int {
        return keyColor
    }

    fun setKeyStyle(keyStyle: String) {
        if (keyStyle == "white") {
            borderWidth = 0
            strokeColor = Color.WHITE
            keyColor = Color.WHITE
            shadowColor = 0xFFC8C8C8.toInt()
            labelColor = Color.BLACK
            cornerRadii = floatArrayOf(0f, 0f, 0f, 0f, 32f, 32f, 32f, 32f)
            labelSize = 64f
            pressedHeight = 64f
            pressedColor = 0xFF8DD599.toInt()
        } else {

            borderWidth = 0
            strokeColor = Color.BLACK
            keyColor = Color.BLACK
            shadowColor = 0xFF2F2F2F.toInt()
            labelColor = Color.WHITE
            cornerRadii = floatArrayOf(0f, 0f, 0f, 0f, 32f, 32f, 32f, 32f)
            labelSize = 64f
            pressedHeight = 64f
            pressedColor = 0xFF16591D.toInt()
        }
        syncKeyStyle()
    }

    fun setAnimationDuration(duration: Long) {
        animationDuration = duration
        syncKeyStyle()
    }

    fun getAnimationDuration(): Long {
        return animationDuration
    }

    fun setKeyLabel(keyLabel: CharSequence?) {
        label = keyLabel?.toString() ?: ""
        syncKeyStyle()
    }

    fun getKeyLabel(): String {
        return label
    }

    fun setKeyLabelSize(size: Float) {
        labelSize = size
        syncKeyStyle()
    }

    fun getKeyLabelSize(): Float {
        return labelSize
    }

    fun setPressedHeight(height: Float) {
        pressedHeight = height
        syncKeyStyle()
    }

    fun getPressedHeight(): Float {
        return pressedHeight
    }


    fun setPressedColor(color: Int) {
        pressedColor = color
        syncKeyStyle()
    }

    fun getPressedColor(): Int {
        return pressedColor
    }


    fun setViewOnly(viewOnly: Boolean) {
        this.viewOnly = viewOnly
        syncKeyStyle()
    }

    fun getViewOnly(): Boolean {
        return viewOnly
    }


    private fun syncKeyStyle() {
        val pressingShadowBgShape = GradientDrawable()
        pressingShadowBgShape.cornerRadii = cornerRadii
        pressingShadowBgShape.setColor(shadowColor)
        pressingShadow.background = pressingShadowBgShape

        val keyBgShape = GradientDrawable()
        keyBgShape.setColor(keyColor)
        keyBgShape.setStroke(borderWidth, strokeColor)
        keyBgShape.cornerRadii = cornerRadii
        key.background = keyBgShape
        val params =
            LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        params.setMargins(
            0, 0, 0,
            pressedHeight.toInt()
        )
        key.layoutParams = params
        keyLabel.setTextSize(TypedValue.COMPLEX_UNIT_PX, labelSize)
        keyLabel.text = label
        val labelParams =
            keyLabel.layoutParams as LayoutParams
        labelParams.bottomMargin = pressedHeight.toInt()
        keyLabel.setTextColor(labelColor)
        key.setOnTouchListener { _, event ->
            if (viewOnly) {
                false
            } else {
                when (event?.action) {
                    MotionEvent.ACTION_DOWN -> {
                        pressKey()
                        onTapDown?.invoke()
                    }
                    MotionEvent.ACTION_UP -> {
                        releaseKey()
                        onTapRelease?.invoke()
                    }
                }
                true
            }
        }
    }

    fun releaseKey() {
        val animatedView = findViewById<RelativeLayout>(R.id.white_key)
        val pressingShadowBgShape = animatedView.background as GradientDrawable
        pressingShadowBgShape.setColor(keyColor)
        val params = animatedView.layoutParams as LayoutParams
        val animator = ValueAnimator.ofInt(
            params.bottomMargin,
            pressedHeight.toInt(),
        )
        animator.addUpdateListener { valueAnimator ->
            params.bottomMargin = valueAnimator.animatedValue as Int
            animatedView.requestLayout()
        }
        animator.duration = animationDuration
        animator.start()
    }

    fun pressKey() {
        val animatedView = findViewById<RelativeLayout>(R.id.white_key)
        val pressingShadowBgShape = animatedView.background as GradientDrawable
        pressingShadowBgShape.setColor(pressedColor)
        val params = animatedView.layoutParams as LayoutParams
        val animator = ValueAnimator.ofInt(params.bottomMargin, 0)
        animator.addUpdateListener { valueAnimator ->
            params.bottomMargin = valueAnimator.animatedValue as Int
            animatedView.requestLayout()
        }
        animator.duration = animationDuration
        animator.start()
    }
}
