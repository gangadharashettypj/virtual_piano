package com.example.pianokeys

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.recyclerview.widget.RecyclerView
import com.dreamtech.virtual_piano.PianoKeyView
import com.dreamtech.virtual_piano.VirtualPianoConstants
import com.dreamtech.virtual_piano.model.PianoKeyStyle

class PianoViewAdapter(
    private val items: MutableList<PianoKeyModel>,
    private val context: Context,
    private val blackKeyWidth: Int = 100,
    private val blackKeyHeight: Int = 350,
    private val whiteKeyWidth: Int = 150,
    private val whiteKeyHeight: Int = 500,
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
        val itemData = items[position]
        v.setKeyLabel(itemData.label)
        if(itemData.type == "white") {
            v.setKeyStyle(VirtualPianoConstants.WHITE_KEY_STYLE)
            v.setKeyLabelSize(10f)
            v.setPressedHeight(whiteKeyHeight*0.2f)
            v.layoutParams = RelativeLayout.LayoutParams(whiteKeyWidth, whiteKeyHeight)
        }else{
            v.setKeyStyle(VirtualPianoConstants.BLACK_KEY_STYLE)
            v.setKeyLabelSize(10f)
            v.setPressedHeight(blackKeyHeight*0.2f)
            v.layoutParams = RelativeLayout.LayoutParams(blackKeyWidth, blackKeyHeight)
        }

    }
}

class ViewHolder<PianoKeyView>(view: View) : RecyclerView.ViewHolder(view) {
}