package com.dreamtech.myapplication

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.util.TypedValue
import android.view.MotionEvent
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.StyleableRes

class PianoKey(context: Context, attrs: AttributeSet) :
    LinearLayout(context, attrs) {
    @StyleableRes
    var index0 = 0

    @SuppressLint("ResourceType")
    @StyleableRes
    var index1 = 1

    @SuppressLint("ResourceType")
    @StyleableRes
    var index2 = 2
    private lateinit var keyLabel: TextView
    private lateinit var whiteKey: RelativeLayout
    private lateinit var blackKey: RelativeLayout
    private fun init(context: Context, attrs: AttributeSet) {
        inflate(context, R.layout.piano_individual_key, this)
        val sets = intArrayOf(R.attr.label, R.attr.white_key_color, R.attr.black_key_color)
        val typedArray = context.obtainStyledAttributes(attrs, sets)
        val keyLabel = typedArray.getText(index0)
        val whiteKeyColor = typedArray.getColor(index1, 0xFFFFFF)
        val blackKeyColor = typedArray.getColor(index2, 0x000000)
        typedArray.recycle()
        initComponents()
        setKeyLabel(keyLabel)
        setWhiteKeyColor(whiteKeyColor)
        setBlackKeyColor(blackKeyColor)
    }

    private fun setKeyLabel(keyLabel: CharSequence?) {
        this.keyLabel.text = keyLabel
    }

    fun getKeyLabel(): String {
        return keyLabel.text.toString()
    }

    private fun setWhiteKeyColor(color: Int) {
        whiteKey.setBackgroundColor(color)
    }

    private fun setBlackKeyColor(color: Int) {
        blackKey.setBackgroundColor(color)
    }

    @SuppressLint("ClickableViewAccessibility", "CutPasteId")
    private fun initComponents() {
        keyLabel = findViewById(R.id.key_label)
        whiteKey = findViewById(R.id.white_key)
        blackKey = findViewById(R.id.black_key)

        whiteKey.setOnTouchListener { _, event ->
            when (event?.action) {
                MotionEvent.ACTION_DOWN -> {
                    val animatedView = findViewById<RelativeLayout>(R.id.white_key)
                    val params = animatedView.layoutParams as RelativeLayout.LayoutParams
                    val animator = ValueAnimator.ofInt(params.bottomMargin, 0)
                    animator.addUpdateListener { valueAnimator ->
                        params.bottomMargin = valueAnimator.animatedValue as Int
                        animatedView.requestLayout()
                    }
                    animator.duration = 60;
                    animator.start();
                }
                MotionEvent.ACTION_UP -> {
                    val px = TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP,
                        16f,
                        resources.displayMetrics
                    ).toInt()
                    val animatedView = findViewById<RelativeLayout>(R.id.white_key)
                    val params = animatedView.layoutParams as RelativeLayout.LayoutParams
                    val animator = ValueAnimator.ofInt(params.bottomMargin, px)
                    animator.addUpdateListener { valueAnimator ->
                        params.bottomMargin = valueAnimator.animatedValue as Int
                        animatedView.requestLayout()
                    }
                    animator.duration = 60;
                    animator.start();
                }
            }

            true
        }
    }

    init {
        init(context, attrs)
    }
}
