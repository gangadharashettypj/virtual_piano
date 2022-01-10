package com.example.pianokeys

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.recyclerview.widget.RecyclerView
import com.dreamtech.virtual_piano.PianoKeyView

class PianoViewAdapter(
    private val items: MutableList<PianoKeyModel>,
    private val context: Context,
    private val blackKeyWidth: Int = 100,
    private val blackKeyHeight: Int = 350,
    private val whiteKeyWidth: Int = 150,
    private val whiteKeyHeight: Int = 500,
    private val disableClicks: Boolean = false
) :
    RecyclerView.Adapter<ViewHolder<PianoKeyView>>() {
    private lateinit var v: PianoKeyView

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<PianoKeyView> {
        v = PianoKeyView(context, null)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder<PianoKeyView>, position: Int) {
        with(holder.itemView as PianoKeyView) {
            val itemData = items[position]

            if (itemData.type == "white") {
                setKeyStyle(itemData.type)
                setKeyLabelSize(30f)
                setPressedHeight(whiteKeyHeight * 0.12f)
                val lp = RecyclerView.LayoutParams(whiteKeyWidth, whiteKeyHeight)
                lp.setMargins(2, 0, 2, 0)
                layoutParams = lp
                elevation = 0f
            } else {
                setKeyStyle(itemData.type)
                setKeyLabelSize(30f)
                setPressedHeight(blackKeyHeight * 0.12f)
                elevation = 2f
                val lp = RecyclerView.LayoutParams(blackKeyWidth, blackKeyHeight)
                lp.setMargins(-blackKeyWidth / 2, 0, -blackKeyWidth / 2, 0)
                layoutParams = lp
            }
            setKeyLabel(itemData.label)
            setViewOnly(disableClicks)
            setCornerRadii(
                floatArrayOf(2f, 2f, 26f, 26f),
            )
        }

    }
}

class ViewHolder<PianoKeyView>(view: View) : RecyclerView.ViewHolder(view) {
}