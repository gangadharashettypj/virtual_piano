package com.dreamtech.virtual_piano

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.TypedValue
import android.view.MotionEvent
import android.widget.RelativeLayout
import android.widget.TextView

enum class KeyType {
    WHITE,
    BLACK,
}


@SuppressLint("ClickableViewAccessibility", "CutPasteId")
class StandardPianoKey(context: Context, attrs: AttributeSet) : RelativeLayout(context, attrs) {

    private lateinit var keyLabel: TextView
    private lateinit var whiteKey: RelativeLayout
    private lateinit var pressingKey: RelativeLayout
    private var keyType: KeyType = KeyType.WHITE

    init {
        inflate(context, R.layout.piano_individual_key, this)
        initComponents()

        val attributes = context.obtainStyledAttributes(attrs, R.styleable.StandardPianoKey)
        setKeyLabel(attributes.getString(R.styleable.StandardPianoKey_label))
        setKeyType(KeyType.values()[attributes.getInt(R.styleable.StandardPianoKey_key_type,0)])
        attributes.recycle()
    }

    private fun initComponents() {
        keyLabel = findViewById(R.id.key_label)
        whiteKey = findViewById(R.id.white_key)
        pressingKey = findViewById(R.id.pressing_layout)
        whiteKey.setOnTouchListener { _, event ->
            when (event?.action) {
                MotionEvent.ACTION_DOWN -> {
                    val animatedView = findViewById<RelativeLayout>(R.id.white_key)
                    val params = animatedView.layoutParams as LayoutParams
                    val animator = ValueAnimator.ofInt(params.bottomMargin, 0)
                    animator.addUpdateListener { valueAnimator ->
                        params.bottomMargin = valueAnimator.animatedValue as Int
                        animatedView.requestLayout()
                    }
                    animator.duration = 60
                    animator.start()
                }
                MotionEvent.ACTION_UP -> {
                    val px = TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP,
                        16f,
                        resources.displayMetrics
                    ).toInt()
                    val animatedView = findViewById<RelativeLayout>(R.id.white_key)
                    val params = animatedView.layoutParams as LayoutParams
                    val animator = ValueAnimator.ofInt(params.bottomMargin, px)
                    animator.addUpdateListener { valueAnimator ->
                        params.bottomMargin = valueAnimator.animatedValue as Int
                        animatedView.requestLayout()
                    }
                    animator.duration = 60
                    animator.start()
                }
            }

            true
        }
    }

    fun setKeyLabel(keyLabel: CharSequence?) {
        this.keyLabel.text = keyLabel
    }

    fun getKeyLabel(): String {
        return keyLabel.text.toString()
    }

    fun getKeyType(): KeyType {
        return keyType
    }

    fun setKeyType(keyType: KeyType) {
        this.keyType = keyType
        when (keyType) {
            KeyType.BLACK -> {
                pressingKey.setBackgroundResource(R.drawable.bg_piano_black_key_shadow)
                whiteKey.setBackgroundResource(R.drawable.bg_piano_black_key)
                keyLabel.setTextColor(Color.WHITE)
            }
            KeyType.WHITE -> {
                pressingKey.setBackgroundResource(R.drawable.bg_piano_white_key_shadow)
                whiteKey.setBackgroundResource(R.drawable.bg_piano_white_key)
                keyLabel.setTextColor(Color.BLACK)
            }
        }
    }
}
